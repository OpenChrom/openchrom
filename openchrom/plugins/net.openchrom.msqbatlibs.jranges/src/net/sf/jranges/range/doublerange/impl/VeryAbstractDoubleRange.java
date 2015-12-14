/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jranges.range.doublerange.impl;

import net.sf.jranges.range.doublerange.RangeDouble;

/**
 * 
 * Most abstract prototype implementation for {@link RangeDouble}.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * <b>Threading:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * Not thread save.
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2014-02-11
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 */
public abstract class VeryAbstractDoubleRange implements RangeDouble {

	private volatile int hashCode;
	/**
	 * start position.
	 */
	protected double start;
	/**
	 * stop position.
	 */
	protected double stop;

	/**
	 * Compares this {@code DoubleRange} to given {@code DoubleRange} by {@link #getStart()} .
	 */
	public int compareTo(final RangeDouble o) {

		return Double.valueOf(getStart()).compareTo(Double.valueOf(o.getStart()));
	}

	@Override
	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof RangeDouble))
			return false;
		final RangeDouble other = (RangeDouble)obj;
		if(getStart() != other.getStart())
			return false;
		if(getStop() != other.getStop())
			return false;
		return true;
	}

	/**
	 * @return 1
	 */
	public double getInterval() {

		return 1;
	}

	public int getLength() {

		return (int)((getStop() - getStart()) / getInterval());
	}

	/**
	 * 
	 */
	public double getStart() {

		return start;
	}

	/**
	 * 
	 */
	public double getStop() {

		return stop;
	}

	@Override
	public synchronized int hashCode() {

		double result = hashCode;
		if(result == 0) {
			final int prime = 31;
			result = 1;
			result = prime * result + getStart();
			result = prime * result + getStop();
			hashCode = (int)result;
		}
		return (int)result;
	}

	/**
	 * 
	 */
	public boolean includes(final double position) {

		if(getStart() <= position && getStop() >= position)
			return true;
		return false;
	}

	public boolean sharesPositionsWith(final RangeDouble anotherRange) {

		return (includes(anotherRange.getStart()) || includes(anotherRange.getStop()));
	}

	@Override
	public String toString() {

		return getStart() + "->" + getStop();
	}
}
