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
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

import java.util.Random;

import org.openscience.cdk.interfaces.IMolecule;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;

public class ExampleMolecule {

	public static IMolecule getMolecule() {

		String output = "ccccc";
		int randNumber = new Random().nextInt(5);
		if(randNumber == 5) {
			output = "c";
		}
		if(randNumber == 4) {
			output = "ccc[br]";
		}
		if(randNumber == 3) {
			output = "ccc[cl]";
		}
		if(randNumber == 2) {
			output = "c1ccc1c[cl]";
		}
		return new CDKSmilesToMoleculeConverter().generate(output);
	}
}
