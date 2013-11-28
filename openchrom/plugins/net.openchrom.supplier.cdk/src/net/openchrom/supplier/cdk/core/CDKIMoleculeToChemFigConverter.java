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

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;

/**
 * A Class that should later be able to convert IMolecule instances to
 * the Chemfig format. It is currently not working.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKIMoleculeToChemFigConverter implements IStructureGenerator {

	@Override
	public IMolecule generate(String input) {

		IMolecule molecule = new CDKSmilesToIMoleculeConverter().generate(input);
		for(IAtom atom : molecule.atoms()) {
			System.out.println(atom.getSymbol());
		}
		return null;
	}
	// An example usage:
	// public static void main(String[] args) {
	// CDKIMoleculeToChemFigConverter converter = new CDKIMoleculeToChemFigConverter();
	// converter.generate("cccc");
	// }
}
