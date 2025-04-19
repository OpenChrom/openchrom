/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;
import net.openchrom.xxd.process.supplier.templates.ui.swt.StandardsAssignerListUI;

public class StandardsAssignerEditingSupport extends AbstractTemplateEditingSupport {

	public StandardsAssignerEditingSupport(StandardsAssignerListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof AssignerStandard setting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				switch(getColumn()) {
					/*
					 * Do not edit the name
					 */
					case AbstractTemplateLabelProvider.CONCENTRATION:
						return Double.toString(setting.getConcentration());
					case AbstractTemplateLabelProvider.CONCENTRATION_UNIT:
						return setting.getConcentrationUnit();
					case AbstractTemplateLabelProvider.COMPENSATION_FACTOR:
						return Double.toString(setting.getCompensationFactor());
					case AbstractTemplateLabelProvider.TRACES:
						return setting.getTracesIdentification();
				}
			}
		}
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof AssignerStandard setting) {
			super.setValue(element, value);
			switch(getColumn()) {
				/*
				 * Do not edit the name
				 */
				case AbstractTemplateLabelProvider.CONCENTRATION:
					double conectration = convertValue(value);
					if(!Double.isNaN(conectration)) {
						if(conectration > 0.0d) {
							setting.setConcentration(conectration);
						}
					}
					break;
				case AbstractTemplateLabelProvider.CONCENTRATION_UNIT:
					String text = ((String)value).trim();
					if(!"".equals(text)) {
						setting.setConcentrationUnit(text);
					}
					break;
				case AbstractTemplateLabelProvider.COMPENSATION_FACTOR:
					double factor = convertValue(value);
					if(!Double.isNaN(factor)) {
						if(factor > 0.0d) {
							setting.setCompensationFactor(factor);
						}
					}
					break;
				case AbstractTemplateLabelProvider.TRACES:
					setting.setTracesIdentification(((String)value).trim());
					break;
			}
			//
			updateTableViewer();
		}
	}
}