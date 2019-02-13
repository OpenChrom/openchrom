/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

public class PeakIdentifierValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + PeakIdentifierListUtil.EXAMPLE_SINGLE + "'";
	private static final String SEPARATOR_TOKEN = PeakDetectorListUtil.SEPARATOR_TOKEN;
	private static final String SEPARATOR_ENTRY = PeakDetectorListUtil.SEPARATOR_ENTRY;
	private static final String ERROR_TOKEN = "The item must not contain: " + SEPARATOR_TOKEN;
	//
	private double startRetentionTime = 0;
	private double stopRetentionTime = 0;
	private String name = "";
	private String casNumber = "";
	private String comments = "";
	private String contributor = "";
	private String referenceId = "";
	private String traces = "";

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
						startRetentionTime = parseDouble(values, 0);
						if(startRetentionTime < 0.0d) {
							message = "The start retention time must be not lower than 0.";
						}
						//
						stopRetentionTime = parseDouble(values, 1);
						if(stopRetentionTime <= startRetentionTime) {
							message = "The stop retention time must be greater then the start retention time.";
						}
						//
						name = parseString(values, 2);
						if("".equals(name)) {
							message = "A substance name needs to be set.";
						}
						//
						casNumber = parseString(values, 3);
						comments = parseString(values, 4);
						contributor = parseString(values, 5);
						referenceId = parseString(values, 6);
						//
						String traceValues = parseString(values, 7);
						message = validateTraces(traceValues);
						traces = (message == null) ? traceValues : "";
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
		setting.setStartRetentionTime(startRetentionTime);
		setting.setStopRetentionTime(stopRetentionTime);
		setting.setName(name);
		setting.setCasNumber(casNumber);
		setting.setComments(comments);
		setting.setContributor(contributor);
		setting.setReferenceId(referenceId);
		setting.setTraces(traces);
		return setting;
	}
}
