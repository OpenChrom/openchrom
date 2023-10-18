/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.util.ReviewValidator;

public class PeakReviewEditingSupport extends AbstractTemplateEditingSupport {

	public PeakReviewEditingSupport(PeakReviewListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof ReviewSetting setting) {
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
					case AbstractTemplateLabelProvider.TRACES:
						return setting.getTraces();
					case AbstractTemplateLabelProvider.PEAK_TYPE:
						return setting.getPeakType();
					case AbstractTemplateLabelProvider.OPTIMIZE_RANGE:
						return setting.isOptimizeRange();
				}
			}
		}
		//
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof ReviewSetting setting) {
			super.setValue(element, value);
			switch(getColumn()) {
				/*
				 * Do not edit the name
				 */
				case AbstractTemplateLabelProvider.CAS_NUMBER:
					String casNumber = ((String)value).trim();
					if(!"".equals(casNumber)) {
						setting.setCasNumber(casNumber);
					}
					break;
				case AbstractTemplateLabelProvider.TRACES:
					String traces = ((String)value).trim();
					ReviewValidator validator = new ReviewValidator();
					IStatus status = validator.validateTraces(traces);
					if(status.isOK()) {
						setting.setTraces(traces);
					}
					break;
				case AbstractTemplateLabelProvider.PEAK_TYPE:
					if(value instanceof PeakType peakType) {
						setting.setPeakType(peakType);
					}
					break;
				case AbstractTemplateLabelProvider.OPTIMIZE_RANGE:
					setting.setOptimizeRange((boolean)value);
					break;
			}
			//
			updateTableViewer();
		}
	}
}
