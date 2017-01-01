/*******************************************************************************
 * Copyright (c) 2013, 2017 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.descriptors;

import java.io.IOException;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.atomic.VdWRadiusDescriptor;

import org.eclipse.chemclipse.logging.core.Logger;

/**
 * A simple descriptor class that calculates the Van-Der-Waals-Radius of an atom in a molecule and returns
 * this value as a String. Returns 1.0 if anything went wrong.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKVanDerWaalsDescriptor extends AbstractStructureDescriptor implements IStructureDescriptor {

	private static final Logger logger = Logger.getLogger(CDKVanDerWaalsDescriptor.class);

	@Override
	public double describe(IAtom atom, IMolecule molecule) {

		double value = 1.0;
		if(atom != null && molecule != null) {
			try {
				VdWRadiusDescriptor radiusDescriptor = new VdWRadiusDescriptor();
				value = Double.parseDouble(radiusDescriptor.calculate(atom, molecule).getValue().toString());
			} catch(IOException e) {
				logger.warn("IOException while trying to describe Van Der Waals radius of molecule instance " + molecule + ". This Error is probably not harmful though.\n But check whether your input Atoms make sense!" + "   Here is more information about the IOException:\n" + e);
			} catch(ClassNotFoundException e) {
				logger.warn(e);
			}
		}
		return value;
	}
}
