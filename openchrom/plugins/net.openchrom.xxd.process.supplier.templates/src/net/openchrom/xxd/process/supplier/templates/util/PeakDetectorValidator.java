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
 * Christoph Läubrich - add support for comments, use PeakType instead of plain String
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public class PeakDetectorValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + PeakDetectorListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = PeakDetectorListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = PeakDetectorListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;
	private double positionStart = 0.0d;
	private double positionStop = 0.0d;
	private PeakType peakType = PeakType.VV;
	private String traces = "";
	private boolean optimizeRange = true;
	private String referenceIdentifier = "";
	private String name = "";

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = ERROR_ENTRY;
		} else {
			if(value instanceof String) {
				String text = ((String)value).trim();
				if(text.contains(SEPARATOR_TOKEN)) {
					message = ERROR_TOKEN;
				} else if("".equals(text.trim())) {
					message = ERROR_ENTRY;
				} else {
					/*
					 * Extract retention time, ...
					 */
					String[] values = text.trim().split("\\" + SEPARATOR_ENTRY); // The pipe needs to be escaped.
					if(values.length >= 3) {
						/*
						 * Evaluation
						 */
						positionStart = parseDouble(values, 0);
						positionStop = parseDouble(values, 1);
						peakType = parsePeakType(parseString(values, 2));
						String traceValues = parseString(values, 3);
						optimizeRange = parseBoolean(values, 4, false);
						referenceIdentifier = parseString(values, 5, "");
						name = parseString(values, 6, "");
						positionDirective = parsePositionDirective(parseString(values, 7));
						/*
						 * Validations
						 */
						if(positionStart < 0.0d) {
							message = "The start position must be not lower than 0.";
						}
						//
						if(positionStop <= positionStart) {
							message = "The stop position must be greater than the start position.";
						}
						//
						if(peakType == null) {
							message = "Please select a peak type: " + PeakType.VV + " or " + PeakType.BB + " or " + PeakType.MM;
						}
						//
						IStatus status = validateTraces(traceValues);
						if(status.isOK()) {
							traces = traceValues;
						} else {
							message = status.getMessage();
						}
					} else {
						message = ERROR_ENTRY;
					}
				}
			} else {
				message = ERROR_ENTRY;
			}
		}
		//
		if(message != null) {
			return ValidationStatus.error(message);
		} else {
			return ValidationStatus.ok();
		}
	}

	public DetectorSetting getSetting() {

		DetectorSetting setting = new DetectorSetting();
		//
		setting.setPositionStart(positionStart);
		setting.setPositionStop(positionStop);
		setting.setPeakType(peakType);
		setting.setTraces(traces);
		setting.setOptimizeRange(optimizeRange);
		setting.setReferenceIdentifier(referenceIdentifier);
		setting.setName(name);
		setting.setPositionDirective(positionDirective);
		//
		return setting;
	}
}