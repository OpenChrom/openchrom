/*******************************************************************************
 * Copyright (c) 2013, 2015 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.renderer;

import javax.vecmath.Point3d;

/**
 * Helps for visualization of IAtom instances ...
 * 
 * @author administrator_marwin
 * 
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
