/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.ui.swt.StandardsReferencerListUI;

public class StandardsReferencerEditingSupport extends AbstractTemplateEditingSupport {

	public StandardsReferencerEditingSupport(StandardsReferencerListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof AssignerReference) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				AssignerReference setting = (AssignerReference)element;
				switch(getColumn()) {
					/*
					 * Do not edit the name
					 */
					case AbstractTemplateLabelProvider.IDENTIFIER:
						return setting.getIdentifier();
				}
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof AssignerReference) {
			AssignerReference setting = (AssignerReference)element;
			super.setValue(element, value);
			switch(getColumn()) {
				/*
				 * Do not edit the name
				 */
				case AbstractTemplateLabelProvider.IDENTIFIER:
					setting.setIdentifier(value.toString());
					break;
			}
			//
			updateTableViewer();
		}
	}
}