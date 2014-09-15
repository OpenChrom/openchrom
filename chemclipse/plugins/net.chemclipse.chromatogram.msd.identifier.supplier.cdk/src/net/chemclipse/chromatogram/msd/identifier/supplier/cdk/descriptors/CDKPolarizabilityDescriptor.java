/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.descriptors;

import org.openscience.cdk.charges.Polarizability;
import org.openscience.cdk.interfaces.IMolecule;

public class CDKPolarizabilityDescriptor extends AbstractStructureDescriptor implements IStructureDescriptor {

	private Polarizability polarizability;

	public CDKPolarizabilityDescriptor() {

		polarizability = new Polarizability();
	}

	@Override
	public double describe(IMolecule molecule) {

		if(molecule == null) {
			return 0;
		} else {
			return Double.valueOf(polarizability.calculateKJMeanMolecularPolarizability(molecule));
		}
	}
}
