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

import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.iupac.parser.NomParser;
import org.openscience.cdk.iupac.parser.ParseException;

public class CDKIupacToIMoleculeConverter implements IStructureGenerator {

	@Override
	public IMolecule generate(String input) {

		IMolecule result = new Molecule();
		try {
			result = NomParser.generate(input);
		} catch(ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch(CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
