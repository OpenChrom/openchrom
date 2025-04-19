/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.classifier.supplier.ratios.util.quant;

import org.eclipse.chemclipse.support.util.ValueParserSupport;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;

public class QuantRatioValidator extends ValueParserSupport implements IValidator<Object> {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + QuantRatioListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = QuantRatioListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = QuantRatioListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private String name = "";
	private String quantitationName = "";
	private double expectedConcentration = 0.0d;
	private String concentrationUnit = "";
	private double deviationWarn = 0.0d;
	private double deviationError = 0.0d;

	@Override
	public IStatus validate(Object value) {

		reset();
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
					if(values.length >= 6) {
						/*
						 * Evaluation
						 */
						name = parseString(values, 0);
						if(name.isEmpty()) {
							message = "A substance name needs to be set.";
						}
						//
						quantitationName = parseString(values, 1);
						if(quantitationName.isEmpty()) {
							message = "A quantitation name needs to be set.";
						}
						//
						expectedConcentration = parseDouble(values, 2);
						if(expectedConcentration <= 0) {
							message = "The expected concentration must be > 0.";
						}
						//
						concentrationUnit = parseString(values, 3);
						if(concentrationUnit.isEmpty()) {
							message = "A concentration unit needs to be set.";
						}
						//
						deviationWarn = parseDouble(values, 4);
						deviationError = parseDouble(values, 5);
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

	private void reset() {

		name = "";
		quantitationName = "";
		expectedConcentration = 0.0d;
		concentrationUnit = "";
		deviationWarn = 0.0d;
		deviationError = 0.0d;
	}

	public QuantRatio getSetting() {

		QuantRatio setting = new QuantRatio();
		setting.setName(name);
		setting.setQuantitationName(quantitationName);
		setting.setExpectedConcentration(expectedConcentration);
		setting.setConcentrationUnit(concentrationUnit);
		setting.setDeviationWarn(deviationWarn);
		setting.setDeviationError(deviationError);
		//
		return setting;
	}
}