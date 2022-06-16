/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;

public class CompensationQuantValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + CompensationQuantListUtil.EXAMPLE_SINGLE + "'";
	//
	private String name = "";
	private String internalStandard = "";
	private double expectedConcentration = 0.0d;
	private String concentrationUnit = "";
	private boolean adjustQuantitationEntry = false;

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = ERROR_ENTRY;
		} else {
			if(value instanceof String) {
				String text = ((String)value).trim();
				if(text.contains(AbstractTemplateListUtil.SEPARATOR_TOKEN)) {
					message = AbstractTemplateListUtil.ERROR_TOKEN;
				} else if("".equals(text.trim())) {
					message = ERROR_ENTRY;
				} else {
					/*
					 * Extract retention time, ...
					 */
					String[] values = text.trim().split("\\" + AbstractTemplateListUtil.SEPARATOR_ENTRY); // The pipe needs to be escaped.
					if(values.length == 5) {
						/*
						 * Evaluation
						 */
						name = parseString(values, 0);
						if("".equals(name)) {
							message = "A substance name needs to be set.";
						}
						//
						internalStandard = parseString(values, 1);
						if("".equals(name)) {
							message = "A internal standard name needs to be set.";
						}
						//
						expectedConcentration = parseDouble(values, 2);
						if(expectedConcentration <= 0) {
							message = "The concentration must be > 0.";
						}
						//
						concentrationUnit = parseString(values, 3);
						if("".equals(concentrationUnit)) {
							message = "A concentration unit needs to be set.";
						}
						//
						adjustQuantitationEntry = parseBoolean(values, 4);
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

	public CompensationSetting getSetting() {

		CompensationSetting setting = new CompensationSetting();
		setting.setName(name);
		setting.setInternalStandard(internalStandard);
		setting.setExpectedConcentration(expectedConcentration);
		setting.setConcentrationUnit(concentrationUnit);
		setting.setAdjustQuantitationEntry(adjustQuantitationEntry);
		return setting;
	}
}
