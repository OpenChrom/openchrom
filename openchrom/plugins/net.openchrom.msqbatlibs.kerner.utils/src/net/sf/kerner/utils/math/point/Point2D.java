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
package net.sf.kerner.utils.math.point;

import net.sf.kerner.utils.pair.Pair;

public class Point2D implements Pair<Double, Double> {

	final public static double getDeltaX(final Point2D one, final Point2D two) {

		return two.x - one.x;
	}

	final public static double getDeltaY(final Point2D one, final Point2D two) {

		return two.y - one.y;
	}

	/**
	 * Calculate the slope from one {@code Point2D} to another.
	 *
	 * @param one
	 *            first {@code Point}
	 * @param tow
	 *            second {@code Point}
	 * @return the slope
	 */
	final public static double getSlope(final Point2D one, final Point2D tow) {

		final double b = getDeltaX(one, tow);
		if(b == 0)
			return 0;
		return getDeltaY(one, tow) / (b);
	}

	protected final double x;
	protected final double y;

	public Point2D(final double x, final double y) {

		super();
		this.x = x;
		this.y = y;
	}

	public Point2D(final Point2D template) {

		this(template.getX(), template.getY());
	}

	
	public Point2D clone() {

		return new Point2D(getX(), getY());
	}

	
	public boolean equals(final Object obj) {

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Point2D)) {
			return false;
		}
		final Point2D other = (Point2D)obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}

	final public double getDistance(final Point2D two) {

		return Math.sqrt(Math.pow(getDeltaX(this, two), 2) + Math.pow(getDeltaY(this, two), 2));
	}

	public Double getFirst() {

		return getX();
	}

	public Double getSecond() {

		return getY();
	}

	final public double getSlope(final Point2D two) {

		return getSlope(this, two);
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	public Point2D invert() {

		return new Point2D(getY(), getX());
	}

	
	public String toString() {

		return "x=" + getX() + ",y=" + getY();
	}
}
