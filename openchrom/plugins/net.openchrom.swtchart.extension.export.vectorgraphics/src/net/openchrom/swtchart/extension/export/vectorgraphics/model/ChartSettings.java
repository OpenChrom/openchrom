/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.model;

import java.util.Objects;

public class ChartSettings {

	/*
	 * The defaults are maximized to full size landscape.
	 */
	private float factorGraphics = 20.0f;
	private float factorFont = 20.0f;
	private int numberTics = 20;

	public float getFactorGraphics() {

		return factorGraphics;
	}

	public ChartSettings setFactorGraphics(float factorGraphics) {

		this.factorGraphics = factorGraphics;
		return this;
	}

	public float getFactorFont() {

		return factorFont;
	}

	public ChartSettings setFactorFont(float factorFont) {

		this.factorFont = factorFont;
		return this;
	}

	public int getNumberTics() {

		return numberTics;
	}

	public ChartSettings setNumberTics(int numberTics) {

		this.numberTics = numberTics;
		return this;
	}

	@Override
	public int hashCode() {

		return Objects.hash(factorFont, factorGraphics, numberTics);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		ChartSettings other = (ChartSettings)obj;
		return Float.floatToIntBits(factorFont) == Float.floatToIntBits(other.factorFont) && Float.floatToIntBits(factorGraphics) == Float.floatToIntBits(other.factorGraphics) && numberTics == other.numberTics;
	}

	@Override
	public String toString() {

		return "ChartSettings [factorGraphics=" + factorGraphics + ", factorFont=" + factorFont + ", numberTics=" + numberTics + "]";
	}
}