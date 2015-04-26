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
 * Dr. Philip Wenig - minor improvements
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.descriptors;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;

public class CDKMolecularWeightDescriptor extends AbstractStructureDescriptor implements IStructureDescriptor {

	@Override
	public double describe(IMolecule molecule) {

		if(molecule == null) {
			return 0;
		} else {
			WeightDescriptor weightDescriptor = new WeightDescriptor();
			return Double.valueOf(weightDescriptor.calculate(molecule).getValue().toString());
		}
	}
}
