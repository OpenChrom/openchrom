/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class ReviewValidator extends AbstractTemplateValidator implements ITemplateValidator {

	public static final Set<PeakType> DETECTOR_TYPES = Collections.unmodifiableSet(EnumSet.of(PeakType.BB, PeakType.VV, PeakType.MM));
	//
	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + ReviewListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = ReviewListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = ReviewListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private double startRetentionTimeMinutes = 0;
	private double stopRetentionTimeMinutes = 0;
	private String name = "";
	private String casNumber = "";
	private String traces = "";
	private PeakType detectorType = PeakType.VV;
	private boolean optimizeRange = true;

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
					if(values.length >= 5) {
						/*
						 * Evaluation
						 */
						startRetentionTimeMinutes = parseDouble(values, 0);
						if(startRetentionTimeMinutes < 0.0d) {
							message = "The start retention time must be not lower than 0.";
						}
						//
						stopRetentionTimeMinutes = parseDouble(values, 1);
						if(stopRetentionTimeMinutes <= startRetentionTimeMinutes) {
							message = "The stop retention time must be greater then the start retention time.";
						}
						//
						name = parseString(values, 2);
						if("".equals(name)) {
							message = "A substance name needs to be set.";
						}
						//
						casNumber = parseString(values, 3);
						//
						String traceValues = parseString(values, 4);
						message = validateTraces(traceValues);
						traces = (message == null) ? traceValues : "";
						//
						detectorType = parseType(parseString(values, 5));
						if(detectorType == null) {
							detectorType = PeakType.VV;
						}
						//
						optimizeRange = parseBoolean(values, 6, true);
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

	public static PeakType parseType(String parseString) {

		if(parseString != null) {
			try {
				PeakType type = PeakType.valueOf(parseString.toUpperCase());
				if(DETECTOR_TYPES.contains(type)) {
					return type;
				}
			} catch(RuntimeException e) {
				// invalid!
			}
		}
		return null;
	}

	public ReviewSetting getSetting() {

		ReviewSetting setting = new ReviewSetting();
		setting.setStartRetentionTimeMinutes(startRetentionTimeMinutes);
		setting.setStopRetentionTimeMinutes(stopRetentionTimeMinutes);
		setting.setName(name);
		setting.setCasNumber(casNumber);
		setting.setTraces(traces);
		setting.setDetectorType(detectorType);
		setting.setOptimizeRange(optimizeRange);
		return setting;
	}
}
