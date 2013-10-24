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

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * A simple yet useful class for parsing smiles to IMolecules
 * that can then be rendered.
 * 
 * @author marwin
 * 
 */
public class ChromSmilesParser {

	// The actual SmilesParser:
	private SmilesParser smilesParser;

	/**
	 * This class is used as a Singleton only.
	 */
	private ChromSmilesParser() {

	};

	public static ChromSmilesParser getInstance() {

		ChromSmilesParser chromSmilesParser = new ChromSmilesParser();
		chromSmilesParser.smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		return chromSmilesParser;
	}

	/**
	 * Class to generate IMolecule out of a smiles String
	 * 
	 * @param smilesString
	 * @return {@link IMolecule}
	 */
	public IMolecule generate(String smilesString) {

		IMolecule molecule = null;
		try {
			molecule = smilesParser.parseSmiles(smilesString);
		} catch(InvalidSmilesException e) {
			System.err.println("Cannot parse input as Smiles String, \n" + "because the following error occured:\n" + e);
		}
		return molecule;
	}
}
