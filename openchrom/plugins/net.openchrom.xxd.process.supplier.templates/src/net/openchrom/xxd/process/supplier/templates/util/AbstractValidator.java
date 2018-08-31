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

import org.eclipse.chemclipse.logging.core.Logger;

public abstract class AbstractValidator {

	private static final Logger logger = Logger.getLogger(AbstractValidator.class);

	public double parseDouble(String input) {

		double result = 0.0d;
		try {
			result = Double.parseDouble(input);
		} catch(NumberFormatException e) {
			logger.warn(e);
		}
		return result;
	}
}
