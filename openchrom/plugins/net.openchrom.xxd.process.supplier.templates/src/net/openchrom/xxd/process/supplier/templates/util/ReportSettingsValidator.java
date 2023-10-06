/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
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

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;

public class ReportSettingsValidator implements IValidator<Object> {

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = "The report setting doesn't exist.";
		} else {
			if(value instanceof ReportSettings settings) {
				ReportSettings reportSettings = settings;
				if(reportSettings.isEmpty()) {
					message = "Please add a template entry to report a peak.";
				}
			} else {
				message = "The settings class is not of type: " + ReportSettings.class.getName();
			}
		}
		//
		if(message != null) {
			return ValidationStatus.error(message);
		} else {
			return ValidationStatus.ok();
		}
	}
}