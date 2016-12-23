/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.equal;

public class UtilEqual {

	static public boolean areEqual(final boolean aThis, final boolean aThat) {

		return aThis == aThat;
	}

	static public boolean areEqual(final char aThis, final char aThat) {

		return aThis == aThat;
	}

	/**
	 * Checks equality of two given {@code doubles} by using {@link Double#doubleToLongBits(double)}
	 *
	 * @param d1
	 *            first {@code double}
	 * @param d2
	 *            second {@code double}
	 * @return {@code true} if {@code d1} euqals {@code d2}; {@code false} otherwise
	 */
	static public boolean areEqual(final double d1, final double d2) {

		final boolean result = Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2);
		return result;
	}

	static public boolean areEqual(final float aThis, final float aThat) {

		return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
	}

	static public boolean areEqual(final long aThis, final long aThat) {

		return aThis == aThat;
	}

	static public boolean areEqual(final Object aThis, final Object aThat) {

		return aThis == null ? aThat == null : aThis.equals(aThat);
	}

	private UtilEqual() {
	}
}
