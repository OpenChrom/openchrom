/*******************************************************************************
 * Copyright (c) 2013, 2016 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.smiles.SmilesParser;

import org.eclipse.chemclipse.logging.core.Logger;

/**
 * Very useful wrapper class, that converts Smiles Strings to IMolecule instances and also does (a little) exception handling.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKSmilesToMoleculeConverter implements IStructureConverter {

	private static final Logger logger = Logger.getLogger(CDKSmilesToMoleculeConverter.class);
	private SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());

	@Override
	public IMolecule generate(String input) {

		IMolecule molecule = null;
		if(input != null) {
			try {
				molecule = smilesParser.parseSmiles(input);
			} catch(InvalidSmilesException e) {
				logger.warn("Cannot parse input as Smiles String, \n" + "because the following error occured:\n" + e);
			}
		}
		return molecule;
	}
}
