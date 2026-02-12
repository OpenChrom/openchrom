/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - additional API and implementation
 * Egon Willighagen - additional API and implementation
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
