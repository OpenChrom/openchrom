/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

public interface IUnitConverter {

	/**
	 * Returns the conversion factor to pt.
	 * 
	 * @return float
	 */
	float getFactor();

	/**
	 * Converts the given value to pt.
	 * 
	 * @param value
	 * @return float
	 */
	float convertToPt(float value);

	/**
	 * Converts the given value (pt) to the unit of this converter.
	 * 
	 * @param value
	 * @return
	 */
	float convertFromPt(float value);
}
