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

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class RetentionTimeValidator implements IValidator<Object> {

	private static final String ERROR = "Please enter a correct retention time in minutes, e.g. 4.25";
	private static final String ERROR_VALUE_RANGE = "The retention time must be not <= 0.";

	@Override
	public IStatus validate(Object value) {

		String message = null;
		//
		if(value == null) {
			message = ERROR;
		} else {
			if(value instanceof String) {
				try {
					double retentionTime = Double.parseDouble(((String)value).trim());
					if(retentionTime <= 0.0d) {
						message = ERROR_VALUE_RANGE;
					}
				} catch(NumberFormatException e) {
					message = ERROR;
				}
			} else {
				message = ERROR;
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
