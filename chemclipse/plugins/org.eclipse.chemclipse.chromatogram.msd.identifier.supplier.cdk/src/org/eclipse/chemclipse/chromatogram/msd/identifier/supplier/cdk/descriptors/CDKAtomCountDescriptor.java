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
import org.openscience.cdk.qsar.descriptors.molecular.AtomCountDescriptor;

/**
 * Another wrapper for a CDK Descriptor, because they are generally easier to use when wrapped.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKAtomCountDescriptor extends AbstractStructureDescriptor implements IStructureDescriptor {

	@Override
	public double describe(IMolecule molecule) {

		if(molecule == null) {
			return 0;
		} else {
			return Double.valueOf(new AtomCountDescriptor().calculate(molecule).getValue().toString());
		}
	}
}
