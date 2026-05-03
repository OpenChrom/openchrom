/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments, use combobox editor
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakDetectorListUI;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorEditingSupport extends AbstractTemplateEditingSupport {

	public PeakDetectorEditingSupport(PeakDetectorListUI tableViewer, String column) {

		super(tableViewer, column);
	}

	@Override
	protected Object getValue(Object element) {

		if(element instanceof DetectorSetting setting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				switch(getColumn()) {
					case AbstractTemplateLabelProvider.PEAK_TYPE:
						return setting.getPeakType();
					case AbstractTemplateLabelProvider.TRACES:
						return setting.getTraces();
					case AbstractTemplateLabelProvider.OPTIMIZE_RANGE:
						return setting.isOptimizeRange();
					case AbstractTemplateLabelProvider.POSITION_RELATIVE_PEAK_NAME:
						return setting.getReferenceIdentifier();
					case AbstractTemplateLabelProvider.NAME:
						return setting.getName();
					case AbstractTemplateLabelProvider.CLASSIFIER:
						return setting.getClassifier();
					case AbstractTemplateLabelProvider.AUTO_ADJUST_SCAN_RANGE:
						return setting.isAutoAdjustScanRange();
					case AbstractTemplateLabelProvider.AUTO_ADJUST_DETECTOR_RANGE:
						return setting.isAutoAdjustDetectorRange();
				}
			}
		}

		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof DetectorSetting setting) {
			super.setValue(element, value);
			switch(getColumn()) {
				case AbstractTemplateLabelProvider.PEAK_TYPE:
					if(value instanceof PeakType peakType) {
						setting.setPeakType(peakType);
					}
					break;
				case AbstractTemplateLabelProvider.TRACES:
					String traces = ((String)value).trim();
					PeakDetectorValidator validator = new PeakDetectorValidator();
					IStatus status = validator.validateTraces(traces);
					if(status.isOK()) {
						setting.setTraces(traces);
					}
					break;
				case AbstractTemplateLabelProvider.OPTIMIZE_RANGE:
					setting.setOptimizeRange((boolean)value);
					break;
				case AbstractTemplateLabelProvider.POSITION_RELATIVE_PEAK_NAME:
					String referenceIdentifier = ((String)value).trim();
					setting.setReferenceIdentifier(referenceIdentifier);
					break;
				case AbstractTemplateLabelProvider.NAME:
					setting.setName(((String)value).trim());
					break;
				case AbstractTemplateLabelProvider.CLASSIFIER:
					setting.setClassifier(((String)value).trim());
					break;
				case AbstractTemplateLabelProvider.AUTO_ADJUST_SCAN_RANGE:
					setting.setAutoAdjustScanRange((boolean)value);
					break;
				case AbstractTemplateLabelProvider.AUTO_ADJUST_DETECTOR_RANGE:
					setting.setAutoAdjustDetectorRange((boolean)value);
					break;
			}

			updateTableViewer();
		}
	}
}