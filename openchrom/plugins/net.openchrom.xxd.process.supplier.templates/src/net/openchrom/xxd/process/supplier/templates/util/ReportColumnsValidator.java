/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.ReportColumns;

public class ReportColumnsValidator implements IValidator<Object> {

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = "The report colum selection doesn't exist.";
		} else {
			if(value instanceof ReportColumns) {
				/*
				 * No checks required.
				 */
			} else {
				message = "The settings class is not of type: " + ReportColumns.class.getName();
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