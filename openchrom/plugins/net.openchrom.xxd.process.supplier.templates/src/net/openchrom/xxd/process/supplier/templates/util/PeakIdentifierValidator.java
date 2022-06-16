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
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public class PeakIdentifierValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + PeakIdentifierListUtil.EXAMPLE_SINGLE + "'";
	//
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;
	private double positionStart = 0;
	private double positionStop = 0;
	private String name = "";
	private String casNumber = "";
	private String comments = "";
	private String contributor = "";
	private String reference = "";
	private String traces = "";
	private String referenceIdentifier = "";

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
					if(values.length >= 3) {
						/*
						 * Evaluation
						 */
						positionStart = parseDouble(values, 0);
						positionStop = parseDouble(values, 1);
						name = parseString(values, 2);
						casNumber = parseString(values, 3);
						comments = parseString(values, 4);
						contributor = parseString(values, 5);
						reference = parseString(values, 6);
						String traceValues = parseString(values, 7);
						referenceIdentifier = parseString(values, 8, "");
						positionDirective = parsePositionDirective(parseString(values, 9));
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
						if("".equals(name)) {
							message = "A substance name needs to be set.";
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

	public IdentifierSetting getSetting() {

		IdentifierSetting setting = new IdentifierSetting();
		//
		setting.setPositionStart(positionStart);
		setting.setPositionStop(positionStop);
		setting.setName(name);
		setting.setCasNumber(casNumber);
		setting.setComments(comments);
		setting.setContributor(contributor);
		setting.setReference(reference);
		setting.setTraces(traces);
		setting.setReferenceIdentifier(referenceIdentifier);
		setting.setPositionDirective(positionDirective);
		//
		return setting;
	}
}
