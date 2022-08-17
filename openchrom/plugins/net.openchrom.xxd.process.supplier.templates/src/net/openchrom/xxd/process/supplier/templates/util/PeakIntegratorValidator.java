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

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;

public class PeakIntegratorValidator extends AbstractTemplateValidator implements ITemplateValidator {

	private static final String ERROR_ENTRY = "Please enter an item, e.g.: '" + PeakIntegratorListUtil.EXAMPLE_SINGLE + "'";
	//
	private String identifier = "";
	private PositionDirective positionDirective = PositionDirective.RETENTION_TIME_MIN;
	private double positionStart = 0;
	private double positionStop = 0;
	private String integrator = "";

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = ERROR_ENTRY;
		} else {
			if(value instanceof String string) {
				String text = string.trim();
				if(text.contains(AbstractTemplateListUtil.SEPARATOR_TOKEN)) {
					message = AbstractTemplateListUtil.ERROR_TOKEN;
				} else if("".equals(text.trim())) {
					message = ERROR_ENTRY;
				} else {
					/*
					 * Extract retention time, ...
					 */
					String[] values = text.trim().split("\\" + AbstractTemplateListUtil.SEPARATOR_ENTRY); // The pipe needs to be escaped.
					if(values.length >= 4) {
						/*
						 * Evaluation
						 */
						positionStart = parseDouble(values, 0);
						positionStop = parseDouble(values, 1);
						identifier = parseString(values, 2);
						integrator = parseString(values, 3);
						positionDirective = parsePositionDirective(parseString(values, 4));
						/*
						 * Validations
						 */
						if("".equals(integrator)) {
							message = "An integrator needs to be set.";
						} else {
							if(!(integrator.equals(IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID) || integrator.equals(IntegratorSetting.INTEGRATOR_NAME_MAX))) {
								message = "The integrator must be either '" + IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID + "' or '" + IntegratorSetting.INTEGRATOR_NAME_MAX + "'";
							}
						}
						//
						if(positionStart == 0.0d && positionStop == 0.0d) {
							if("".equals(identifier)) {
								message = "A substance name needs to be set.";
							}
						} else {
							if(positionStop <= positionStart) {
								message = "The stop position must be greater than the start position.";
							}
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

	public IntegratorSetting getSetting() {

		IntegratorSetting setting = new IntegratorSetting();
		//
		setting.setPositionStart(positionStart);
		setting.setPositionStop(positionStop);
		setting.setIdentifier(identifier);
		setting.setIntegrator(integrator);
		setting.setPositionDirective(positionDirective);
		//
		return setting;
	}
}