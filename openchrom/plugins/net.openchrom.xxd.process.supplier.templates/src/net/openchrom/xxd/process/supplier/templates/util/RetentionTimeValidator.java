/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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

		if(value == null) {
			message = ERROR;
		} else {
			if(value instanceof String text) {
				try {
					double retentionTime = Double.parseDouble(text.trim());
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

		if(message != null) {
			return ValidationStatus.error(message);
		} else {
			return ValidationStatus.ok();
		}
	}
}
