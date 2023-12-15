/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.util.time;

import org.eclipse.chemclipse.support.util.ValueParserSupport;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.util.AbstractRatioListUtil;

public class TimeRatioValidator extends ValueParserSupport implements IValidator<Object> {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + TimeRatioListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = AbstractRatioListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = AbstractRatioListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private String name = "";
	private double expectedRetentionTimeMinutes = 0.0d;
	private double deviationWarn = 0.0d;
	private double deviationError = 0.0d;

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = ERROR_ENTRY;
		} else {
			if(value instanceof String text) {
				text = text.trim();
				if(text.contains(SEPARATOR_TOKEN)) {
					message = ERROR_TOKEN;
				} else if("".equals(text.trim())) {
					message = ERROR_ENTRY;
				} else {
					/*
					 * Extract the name
					 */
					String[] values = text.trim().split("\\" + SEPARATOR_ENTRY); // The pipe needs to be escaped.
					if(values.length >= 4) {
						/*
						 * Evaluation
						 */
						name = parseString(values, 0);
						if("".equals(name)) {
							message = "A substance name needs to be set.";
						}
						//
						expectedRetentionTimeMinutes = parseDouble(values, 1);
						if(expectedRetentionTimeMinutes <= 0.0d) {
							message = "The expected retention time must be > 0.";
						}
						//
						deviationWarn = parseDouble(values, 2);
						deviationError = parseDouble(values, 3);
						//
						if(deviationWarn <= 0) {
							message = "The deviation warn must be >= 0.";
						}
						//
						if(deviationError <= 0) {
							message = "The deviation error must be >= 0.";
						}
						//
						if(deviationError < deviationWarn) {
							message = "The deviation error must be > deviation warn.";
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

	public TimeRatio getSetting() {

		TimeRatio setting = new TimeRatio();
		setting.setName(name);
		setting.setExpectedRetentionTimeMinutes(expectedRetentionTimeMinutes);
		setting.setDeviationWarn(deviationWarn);
		setting.setDeviationError(deviationError);
		return setting;
	}
}
