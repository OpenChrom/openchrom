/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

public abstract class AbstractValidator {

	protected String parseString(String[] values, int index) {

		return (values.length > index) ? values[index].trim() : "";
	}

	protected double parseDouble(String[] values, int index) {

		double result = 0.0d;
		String value = parseString(values, index);
		try {
			result = Double.parseDouble(value);
		} catch(NumberFormatException e) {
			//
		}
		return result;
	}
}
