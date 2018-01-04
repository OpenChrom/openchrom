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
package net.sf.kerner.utils.math;

/**
 * A Point in a two-dimensional coordinate system, defined by {@code double} Coordinates.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-03-01
 * @deprecated Use Point2D instead
 */
public class Point {

	private final double x;
	private final double y;

	/**
	 * Create a new {@code Point}, at given X- and Y-Coordinate.
	 * 
	 * @param x
	 *            X-Coordinate
	 * @param y
	 *            Y-Coordinate
	 */
	public Point(double x, double y) {
		this.y = y;
		this.x = x;
	}

	/**
	 * Create new {@code Point}, which is a copy of given {@code Point}
	 * 
	 * @param template
	 *            {@code Point} to copy
	 */
	public Point(Point template) {
		this(template.x, template.y);
	}

	/**
	 * Calculate the slope from one {@code Point} to another.
	 * 
	 * @param one
	 *            first {@code Point}
	 * @param tow
	 *            second {@code Point}
	 * @return the slope
	 */
	final public static double getSlope(Point one, Point tow) {

		final double b = getDeltaX(one, tow);
		if(b == 0)
			return 0;
		return getDeltaY(one, tow) / (b);
	}

	final static double getDeltaY(Point one, Point two) {

		return two.y - one.y;
	}

	final static double getDeltaX(Point one, Point two) {

		return two.x - one.x;
	}

	final public double getDistance(Point two) {

		return Math.sqrt(Math.pow(Point.getDeltaX(this, two), 2) + Math.pow(Point.getDeltaY(this, two), 2));
	}

	final public double getSlope(Point two) {

		return Point.getSlope(this, two);
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

	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Point)) {
			return false;
		}
		Point other = (Point)obj;
		if(Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if(Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		return true;
	}

	public String toString() {

		return "[x=" + x + ",y=" + y + "]";
	}
}
