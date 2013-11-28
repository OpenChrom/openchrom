/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core.customizedRenderer;

import javax.vecmath.Point3d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;

/**
 * Class that converts IMolecule instances to 3-dimensional objects.
 * 
 * @author administrator_marwin
 * 
 */
@Deprecated
public class IMoleculeTo3DConverter {

	IMolecule molecule;

	private double getVanDerWaalsRadius(IAtom atom)// TODO: Work from here!
	{

		return 0.0;
	}

	public void generate() {

		// ... Assume that 3d coords have been calculated properly ...
		// import com.sun.j3d.utils.geometry.Sphere // (!)
		// Instantiate Sphere object for each atom in molecule:
		for(IAtom atom : molecule.atoms()) {
			AtomRadialRepresentation atomRep = new AtomRadialRepresentation(atom.getPoint3d(), getVanDerWaalsRadius(atom));
		}
	}
}
