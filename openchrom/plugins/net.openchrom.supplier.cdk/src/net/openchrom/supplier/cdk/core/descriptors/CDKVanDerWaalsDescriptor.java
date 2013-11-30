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

import java.io.IOException;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.qsar.descriptors.atomic.VdWRadiusDescriptor;

import net.openchrom.logging.core.Logger;

/**
 * A simple descriptor class that calculates the Van-Der-Waals-Radius of an atom in a molecule and returns
 * this value as a String. Returns 1.0 if anything went wrong.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKVanDerWaalsDescriptor {

	private static final Logger logger = Logger.getLogger(CDKVanDerWaalsDescriptor.class);

	public double describe(IAtom atom, IMolecule molecule) {

		double val = 1.0;
		try {
			VdWRadiusDescriptor desc = new VdWRadiusDescriptor();
			val = Double.parseDouble(desc.calculate(atom, molecule).getValue().toString());
		} catch(IOException e) {
			logger.warn("IOException while trying to describe Van Der Waals radius of molecule instance " + molecule + ". This Error is probably not harmful though.\n But check whether your input Atoms make sense!" + "   Here is more information about the IOException:\n" + e);
		} catch(ClassNotFoundException e) {
			logger.warn(e);
		}
		return val;
	}
}
