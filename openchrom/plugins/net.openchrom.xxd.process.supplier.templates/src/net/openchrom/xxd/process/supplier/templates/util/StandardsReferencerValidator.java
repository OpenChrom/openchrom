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

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public class StandardsReferencerValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + StandardsReferencerListUtil.EXAMPLE_SINGLE + "'";
	//
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;
	private double positionStart = 0;
	private double positionStop = 0;
	private String internalStandard = "";
	private String identifier = "";

	//
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
						internalStandard = parseString(values, 2);
						identifier = parseString(values, 3);
						positionDirective = parsePositionDirective(parseString(values, 4));
						/*
						 * Validations
						 */
						if(positionStart == 0.0d && positionStop == 0.0d) {
							if("".equals(identifier)) {
								message = "Please set a source identifier instead of the start/stop position.";
							}
						} else {
							if(positionStop <= positionStart) {
								message = "The stop position must be greater than the start position.";
							}
						}
						//
						if("".equals(internalStandard)) {
							message = "The name of the internal standards needs to be set.";
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

	public AssignerReference getSetting() {

		AssignerReference setting = new AssignerReference();
		//
		setting.setPositionStart(positionStart);
		setting.setPositionStop(positionStop);
		setting.setInternalStandard(internalStandard);
		setting.setIdentifier(identifier);
		setting.setPositionDirective(positionDirective);
		//
		return setting;
	}
}