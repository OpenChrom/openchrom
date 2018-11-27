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
package net.openchrom.msd.converter.supplier.pdf.ui.internal.io;

public class Position {

	private float x = 0.0f;
	private float y = 0.0f;

	/**
	 * X and Y position in mm.
	 * 0,0 is upper left!
	 * 
	 * @param x
	 * @param y
	 */
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {

		return x;
	}

	public float getY() {

		return y;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Position other = (Position)obj;
		if(Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if(Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "Position [x=" + x + ", y=" + y + "]";
	}
}
