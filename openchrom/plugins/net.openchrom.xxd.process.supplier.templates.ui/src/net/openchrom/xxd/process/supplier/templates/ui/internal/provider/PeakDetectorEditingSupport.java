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

		if(element instanceof DetectorSetting) {
			Object object = super.getValue(element);
			if(object != null) {
				return object;
			} else {
				DetectorSetting setting = (DetectorSetting)element;
				switch(getColumn()) {
					case AbstractTemplateLabelProvider.PEAK_TYPE:
						return setting.getPeakType();
					case AbstractTemplateLabelProvider.TRACES:
						return setting.getTraces();
					case AbstractTemplateLabelProvider.OPTIMIZE_RANGE:
						return setting.isOptimizeRange();
					case AbstractTemplateLabelProvider.REFERENCE_IDENTIFIER:
						return setting.getReferenceIdentifier();
					case AbstractTemplateLabelProvider.NAME:
						return setting.getName();
				}
			}
		}
		//
		return false;
	}

	@Override
	protected void setValue(Object element, Object value) {

		if(element instanceof DetectorSetting) {
			DetectorSetting setting = (DetectorSetting)element;
			super.setValue(element, value);
			switch(getColumn()) {
				case AbstractTemplateLabelProvider.PEAK_TYPE:
					if(value instanceof PeakType) {
						setting.setPeakType((PeakType)value);
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
				case AbstractTemplateLabelProvider.REFERENCE_IDENTIFIER:
					String referenceIdentifier = ((String)value).trim();
					setting.setReferenceIdentifier(referenceIdentifier);
					break;
				case AbstractTemplateLabelProvider.NAME:
					setting.setName(((String)value).trim());
					break;
			}
			//
			updateTableViewer();
		}
	}
}