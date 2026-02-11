/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.support;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.aromaticity.Aromaticity.Model;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

public class SmartsCalculator_1_Test {

	@Test
	public void test1() {

		try {
			/*
			 * SMILES
			 */
			String smiles = "C=CC1=CC=CC=C1"; // Styrene
			SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
			IAtomContainer atomContainer = smilesParser.parseSmiles(smiles);
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(atomContainer);
			Aromaticity aromaticity = new Aromaticity(Model.CDK_2x, Cycles.all());
			aromaticity.apply(atomContainer);
			/*
			 * SMARTS
			 */
			StringBuilder builder = new StringBuilder();
			for(IAtom atom : atomContainer.atoms()) {
				String symbol = atom.getSymbol();
				if(atom.isAromatic()) {
					symbol = symbol.toLowerCase();
				}
				/*
				 * Count H
				 */
				Integer count = atom.getImplicitHydrogenCount();
				if(count != null) {
					symbol = "[" + symbol + "H" + count + "]";
				} else {
					symbol = "[" + symbol + "]";
				}
				builder.append(symbol);
			}
			String smarts = builder.toString();
			assertEquals("[CH2][CH1][cH0][cH1][cH1][cH1][cH1][cH1]", smarts);
		} catch(Exception e) {
			assertTrue(false);
		}
	}
}