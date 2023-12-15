/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
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

		if(element instanceof AssignerReference setting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
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

		if(element instanceof AssignerReference setting) {
			super.setValue(element, value);
			if(getColumn().equals(AbstractTemplateLabelProvider.IDENTIFIER)) {
				/*
				 * Do not edit the name
				 */
				setting.setIdentifier(value.toString());
			}
			//
			updateTableViewer();
		}
	}
}
