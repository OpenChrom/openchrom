/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.math;

/**
 * A Point in a two-dimensional coordinate system, defined by {@code double}
 * Coordinates.
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
        if (b == 0)
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
        return Math.sqrt(Math.pow(Point.getDeltaX(this, two), 2)
                + Math.pow(Point.getDeltaY(this, two), 2));
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point other = (Point) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[x=" + x + ",y=" + y + "]";
    }

}
