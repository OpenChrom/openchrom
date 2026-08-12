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
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import net.openchrom.xxd.identifier.supplier.cdk.support.MoleculeMassCalculator;
import net.openchrom.xxd.identifier.supplier.cdk.support.SmilesSupport;

public class SmilesWithHydrogen_Test {

	@Test
	public void testStyrene() throws CDKException {

		IAtomContainer atomContainer = SmilesSupport.generate("C=CC1=CC=CC=C1", false);
		assertEquals(104.062600256d, MoleculeMassCalculator.calculateExactMass(MolecularFormulaManipulator.getMolecularFormula(atomContainer)), 0.000001d);
		assertEquals("C([H])([H])=C([H])C1=C([H])C([H])=C([H])C([H])=C1[H]", getSmilesWithHydrogen(atomContainer));
	}

	private String getSmilesWithHydrogen(IAtomContainer atomContainer) throws CDKException {

		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(atomContainer);
		CDKHydrogenAdder hydrogenAdder = CDKHydrogenAdder.getInstance(atomContainer.getBuilder());
		hydrogenAdder.addImplicitHydrogens(atomContainer);
		AtomContainerManipulator.convertImplicitToExplicitHydrogens(atomContainer);
		SmilesGenerator smilesGenerator = new SmilesGenerator(SmiFlavor.Absolute | SmiFlavor.UseAromaticSymbols);
		return smilesGenerator.create(atomContainer);
	}
}
