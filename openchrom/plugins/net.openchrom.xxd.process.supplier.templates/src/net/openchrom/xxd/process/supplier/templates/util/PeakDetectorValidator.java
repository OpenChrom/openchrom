/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorValidator extends AbstractTemplateValidator implements ITemplateValidator {

	public static final Set<PeakType> USEFULL_TYPES = Collections.unmodifiableSet(EnumSet.of(PeakType.BB, PeakType.VV, PeakType.DD));
	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + PeakDetectorListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = PeakDetectorListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = PeakDetectorListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private double startRetentionTimeMinutes = 0;
	private double stopRetentionTimeMinutes = 0;
	private PeakType detectorType = PeakType.VV;
	private String traces = "";
	private boolean optimizeRange = true;
	private String referenceIdentifier = "";

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
						detectorType = parseType(parseString(values, 2));
						if(detectorType == null) {
							StringBuilder sb = new StringBuilder();
							for(PeakType peakType : USEFULL_TYPES) {
								if(sb.length() > 0) {
									sb.append(", ");
								}
								sb.append(peakType.name());
							}
							message = "The detector type must be one of " + sb;
						}
						//
						String traceValues = parseString(values, 3);
						message = validateTraces(traceValues);
						traces = (message == null) ? traceValues : "";
						//
						optimizeRange = parseBoolean(values, 4, false);
						referenceIdentifier = parseString(values, 5, "");
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
				if(USEFULL_TYPES.contains(type)) {
					return type;
				}
			} catch(RuntimeException e) {
				// invalid!
			}
		}
		return null;
	}

	public DetectorSetting getSetting() {

		DetectorSetting setting = new DetectorSetting();
		setting.setStartRetentionTimeMinutes(startRetentionTimeMinutes);
		setting.setStopRetentionTimeMinutes(stopRetentionTimeMinutes);
		setting.setDetectorType(detectorType);
		setting.setTraces(traces);
		setting.setOptimizeRange(optimizeRange);
		setting.setReferenceIdentifier(referenceIdentifier);
		return setting;
	}
}
