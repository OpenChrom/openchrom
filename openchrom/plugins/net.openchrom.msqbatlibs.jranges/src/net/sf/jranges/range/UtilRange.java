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
package net.sf.jranges.range;

import java.util.Collection;

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.kerner.utils.math.UtilMath;

public class UtilRange {

	public interface DoubleRangeTask {

		void call(double d);
	}

	public interface IntegerRangeTask {

		void call(int i);
	}

	public static void doForAllInRange(final RangeDouble range, final DoubleRangeTask task) {

		for(double i = range.getStart(); UtilMath.round(i, 6) <= range.getStop(); i += range.getInterval()) {
			task.call(i);
		}
	}

	public static void doForAllInRange(final RangeInteger range, final IntegerRangeTask task) {

		if(range == null || task == null) {
			throw new NullPointerException();
		}
		for(int i = range.getStart(); i <= range.getStop(); i += range.getInterval()) {
			task.call(i);
		}
	}

	public static boolean includesAll(final RangeInteger range, final Collection<? extends Integer> positions) {

		for(final int i : positions) {
			if(range.includes(i)) {
				// ok
			} else {
				return false;
			}
		}
		return true;
	}

	private UtilRange() {
	}
}
