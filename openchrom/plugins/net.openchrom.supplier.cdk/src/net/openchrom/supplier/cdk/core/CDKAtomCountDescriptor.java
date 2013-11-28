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
package net.openchrom.supplier.cdk.core;

import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.molecular.AtomCountDescriptor;

/**
 * Another wrapper for a CDK Descriptor, because they are generally easier to use when wrapped.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKAtomCountDescriptor implements IStructureDescriptor {

	@Override
	public String describe(IMolecule molecule) {

		return "" + new AtomCountDescriptor().calculate(molecule).getValue();
	}
}
