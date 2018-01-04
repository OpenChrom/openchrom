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
package net.sf.kerner.utils.math.point;

public class Point3D extends Point2D {

	protected final double z;

	public Point3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public Point3D(Point3D template) {
		this(template.getX(), template.getY(), template.getZ());
	}

	// Override //
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(!super.equals(obj)) {
			return false;
		}
		if(!(obj instanceof Point3D)) {
			return false;
		}
		Point3D other = (Point3D)obj;
		if(Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		return true;
	}

	public String toString() {

		return super.toString() + ",z=" + getZ();
	}

	// Getter / Setter //
	public double getZ() {

		return z;
	}
}
