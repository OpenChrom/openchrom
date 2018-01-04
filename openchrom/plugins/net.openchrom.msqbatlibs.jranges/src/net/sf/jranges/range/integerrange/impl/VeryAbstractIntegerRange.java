/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jranges.range.integerrange.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jranges.range.integerrange.RangeInteger;

/**
 * 
 * Most abstract prototype implementation for {@link RangeInteger}.
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-08
 * 
 */
public abstract class VeryAbstractIntegerRange implements RangeInteger {

	private volatile int hashCode;
	/**
	 * start position.
	 */
	protected int start;
	/**
	 * stop position.s
	 */
	protected int stop;

	public List<Integer> asList() {

		// TODO init size
		final List<Integer> result = new ArrayList<Integer>();
		for(int i = getStart(); i <= getStop(); i++) {
			result.add(i);
		}
		return result;
	}

	/**
	 * Compares this {@code IntegerRange} to given {@code IntegerRange} by {@link #getStart()} .
	 */
	public int compareTo(final RangeInteger o) {

		return Integer.valueOf(getStart()).compareTo(Integer.valueOf(o.getStart()));
	}

	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof RangeInteger))
			return false;
		final RangeInteger other = (RangeInteger)obj;
		if(getStart() != other.getStart())
			return false;
		if(getStop() != other.getStop())
			return false;
		if(getInterval() != other.getInterval())
			return false;
		return true;
	}

	/**
	 * 
	 */
	public int getInterval() {

		return 1;
	}

	/**
	 * 
	 */
	public int getLength() {

		return getStop() - getStart() + 1;
	}

	public int getStart() {

		return start;
	}

	public int getStop() {

		return stop;
	}

	public int hashCode() {

		int result = hashCode;
		if(result == 0) {
			final int prime = 31;
			result = 1;
			result = prime * result + getStart();
			result = prime * result + getStop();
			result = prime * result + getInterval();
			hashCode = result;
		}
		return result;
	}

	/**
	 * 
	 */
	public boolean includes(final int position) {

		if(getStart() <= position && getStop() >= position)
			return true;
		return false;
	}

	public String toString() {

		return getStart() + "->" + getStop();
	}
}
