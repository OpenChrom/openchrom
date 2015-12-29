/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.hash;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

import net.sf.kerner.utils.UtilArray;

public class UtilHash {

	public static final int SEED = 23;
	private static final int ODD_PRIME_NUMBER = 37;

	/**
	 * Calculates {@link Object#hashCode()} of all elements in given collection
	 * recursively, calling on every object {@link Object#hashCode()} and add
	 * this to the result.
	 *
	 * @param objects
	 *            Objects from which {@link Object#hashCode()} is calculated
	 * @return deep hashCode for given {@link Collection} of objects
	 */
	public static int getDeepHash(final Collection<?> objects) {

		int result = 0;
		for(final Object o : objects) {
			if(o instanceof Collection) {
				result += getDeepHash((Collection<?>)o);
			} else if(UtilArray.isArray(o)) {
				result += getDeepHash(Arrays.asList((Object[])o));
			} else {
				result += o.hashCode();
			}
		}
		return result;
	}

	private static int firstTerm(final int aSeed) {

		return ODD_PRIME_NUMBER * aSeed;
	}

	public static int getHash(final Object aObject) {

		return getHash(SEED, aObject);
	}

	public static int getHash(final int aSeed, final boolean aBoolean) {

		return firstTerm(aSeed) + (aBoolean ? 1 : 0);
	}

	public static int getHash(final int aSeed, final char aChar) {

		return firstTerm(aSeed) + aChar;
	}

	public static int getHash(final int aSeed, final double aDouble) {

		return getHash(aSeed, Double.doubleToLongBits(aDouble));
	}

	public static int getHash(final int aSeed, final float aFloat) {

		return getHash(aSeed, Float.floatToIntBits(aFloat));
	}

	public static int getHash(final int aSeed, final int aInt) {

		return firstTerm(aSeed) + aInt;
	}

	public static int getHash(final int aSeed, final long aLong) {

		return firstTerm(aSeed) + (int)(aLong ^ (aLong >>> 32));
	}

	public static int getHash(final int aSeed, final Object aObject) {

		int result = aSeed;
		if(aObject == null) {
			result = getHash(result, 0);
		} else if(!UtilArray.isArray(aObject)) {
			result = getHash(result, aObject.hashCode());
		} else {
			final int length = Array.getLength(aObject);
			for(int idx = 0; idx < length; ++idx) {
				final Object item = Array.get(aObject, idx);
				// if an item in the array references the array itself, prevent
				// infinite looping
				if(!(item == aObject))
					// recursive call!
					result = getHash(result, item);
			}
		}
		return result;
	}

	public static int getHash(final Object... objects) {

		int result = SEED;
		for(final Object o : objects) {
			result = ODD_PRIME_NUMBER * result + ((o == null) ? 0 : o.hashCode());
		}
		return result;
	}

	private UtilHash() {
	}
}
