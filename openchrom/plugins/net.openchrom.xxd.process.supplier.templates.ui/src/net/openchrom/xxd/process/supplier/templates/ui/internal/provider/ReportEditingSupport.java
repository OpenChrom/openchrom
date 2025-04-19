/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.cas.CasSupport;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReportStrategy;
import net.openchrom.xxd.process.supplier.templates.ui.swt.ReportListUI;

public class ReportEditingSupport extends AbstractTemplateEditingSupport {

	public ReportEditingSupport(ReportListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof ReportSetting setting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				switch(getColumn()) {
					/*
					 * Do not edit the name
					 */
					case AbstractTemplateLabelProvider.CAS_NUMBER:
						return setting.getCasNumber();
					case AbstractTemplateLabelProvider.REPORT_STRATEGY:
						return setting.getReportStrategy();
				}
			}
		}
		//
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof ReportSetting setting) {
			super.setValue(element, value);
			switch(getColumn()) {
				/*
				 * Do not edit the name
				 */
				case AbstractTemplateLabelProvider.CAS_NUMBER:
					setting.setCasNumber(CasSupport.format(((String)value).trim()));
					break;
				case AbstractTemplateLabelProvider.REPORT_STRATEGY:
					if(value instanceof ReportStrategy reportStrategy) {
						setting.setReportStrategy(reportStrategy);
					}
					break;
			}
			//
			updateTableViewer();
		}
	}
}
