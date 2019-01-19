/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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

public interface IBaseConverter {

	/**
	 * Position x (pt) - default (0,0) left bottom
	 * pageWidth (pt)
	 * x (pt)
	 * 
	 * @param pageWidth
	 * @param x
	 * @return float
	 */
	float getPositionX(float pageWidth, float x);

	/**
	 * 
	 * Position y (pt) - default 0,0 left bottom
	 * pageHeight (pt)
	 * y (pt)
	 *
	 * @param pageHeight
	 * @param y
	 * @return float
	 */
	float getPositionY(float pageHeight, float y);
}
