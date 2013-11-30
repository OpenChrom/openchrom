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
package net.openchrom.supplier.cdk.core.descriptors;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;

public class CDKMolecularWeightDescriptor implements IStructureDescriptor {

	@Override
	public String describe(IMolecule molecule) {

		if(molecule == null)
			return null;
		double mass = 0.0;
		WeightDescriptor weightDescriptor = new WeightDescriptor();
		mass = Double.parseDouble(weightDescriptor.calculate(molecule).getValue().toString());
		return "" + mass;
	}
}
