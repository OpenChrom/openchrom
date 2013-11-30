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
 * Dr. Philip Wenig - minor improvements
 *******************************************************************************/
package net.openchrom.supplier.cdk.descriptors;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.molecular.LongestAliphaticChainDescriptor;

/**
 * Another wrapper for a CDK Descriptor class.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKLongestAliphaticChainDescriptor extends AbstractStructureDescriptor implements IStructureDescriptor {

	@Override
	public double describe(IMolecule molecule) {

		if(molecule == null) {
			return 0;
		} else {
			LongestAliphaticChainDescriptor chainDescriptor = new LongestAliphaticChainDescriptor();
			return Double.valueOf(chainDescriptor.calculate(molecule).getValue().toString());
		}
	}
}
