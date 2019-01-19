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

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PageSettings {

	private PDRectangle pdRectangle;
	private Base base = Base.BOTTOM_LEFT;
	private Unit unit = Unit.PT;
	private boolean landscape = false;

	public PageSettings(PDRectangle pdRectangle, Base base, Unit unit, boolean landscape) {
		this.pdRectangle = pdRectangle;
		this.base = base;
		this.unit = unit;
		this.landscape = landscape;
	}

	public PDRectangle getPDRectangle() {

		return pdRectangle;
	}

	public Base getBase() {

		return base;
	}

	public Unit getUnit() {

		return unit;
	}

	public boolean isLandscape() {

		return landscape;
	}
}
