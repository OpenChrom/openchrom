/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - refactoring bundle name
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.renderer;

import javax.vecmath.Point3d;

/**
 * Helps for visualization of IAtom instances ...
 */
public class AtomRadialRepresentation {

	private double radiusOfAtom;
	private Point3d centerOfAtom;

	// Only allow the creation of AtomRadialRepresentation objects
	// with defined properties, so hide default constructor
	public AtomRadialRepresentation(Point3d centerOfAtom, double radiusOfAtom) {

		this.radiusOfAtom = radiusOfAtom;
		this.centerOfAtom = centerOfAtom;
	}

	// make centerOfAtom, radiusOfAtom accessible from outside
	public void setRadiusOfAtom(double radiusOfAtom) {

		this.radiusOfAtom = radiusOfAtom;
	}

	public void setCenterOfAtom(Point3d centerOfAtom) {

		this.centerOfAtom = centerOfAtom;
	}

	public double getRadiusOfAtom() {

		return radiusOfAtom;
	}

	public Point3d getCenterOfAtom() {

		return centerOfAtom;
	}
}
