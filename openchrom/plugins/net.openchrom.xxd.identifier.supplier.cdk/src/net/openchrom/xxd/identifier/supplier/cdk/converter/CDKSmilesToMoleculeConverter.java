/*******************************************************************************
 * Copyright (c) 2013, 2022 Marwin Wollschläger, Lablicate GmbH.
 * Copyright (c) 2023 Egon Willighagen <egonw@users.sf.net>
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
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.eclipse.chemclipse.logging.core.Logger;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

/**
 * Very useful wrapper class, that converts Smiles Strings to IMolecule instances and also does (a little) exception handling.
 * 
 * @author administrator_marwin
 * 
 */
public class CDKSmilesToMoleculeConverter implements IStructureConverter {

	private static final Logger logger = Logger.getLogger(CDKSmilesToMoleculeConverter.class);
	private SmilesParser smilesParser = new SmilesParser(SilentChemObjectBuilder.getInstance());

	@Override
	public IAtomContainer generate(String input) {

		IAtomContainer molecule = null;
		if(input != null) {
			try {
				smilesParser.setStrict(PreferenceSupplier.isSmilesStrict());
				molecule = smilesParser.parseSmiles(input);
			} catch(InvalidSmilesException e) {
				logger.warn("Cannot parse input as Smiles String, \n" + "because the following error occured:\n" + e);
			}
		}
		return molecule;
	}
}
