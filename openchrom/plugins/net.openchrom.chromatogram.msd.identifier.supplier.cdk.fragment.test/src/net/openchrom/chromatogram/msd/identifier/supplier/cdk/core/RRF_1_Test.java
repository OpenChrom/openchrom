/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import junit.framework.TestCase;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class RRF_1_Test extends TestCase {

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() throws Exception {

		String name = "1,4-Epoxy-p-menthan"; // "1,4-Cineole";
		//
		String smiles = calculateSmiles(name);
		IAtomContainer molecule = calculateMolecule(smiles);
		DepictionGenerator depictionGenerator = new DepictionGenerator();
		depictionGenerator.depict(molecule).writeTo("/home/pwenig/Dokumente/ISEO/Molecule.png");
		//
		int c = countAtoms(molecule, "C");
		int h = countAtoms(molecule, "H");
		int o = countAtoms(molecule, "O");
		int n = countAtoms(molecule, "N");
		int s = countAtoms(molecule, "S");
		int f = countAtoms(molecule, "F");
		int cl = countAtoms(molecule, "Cl");
		int br = countAtoms(molecule, "Br");
		int i = countAtoms(molecule, "I");
		int si = countAtoms(molecule, "Si");
		//
		IAtomContainer benzene = calculateMolecule("C1=CC=CC=C1");
		Pattern pattern = SmartsPattern.findSubstructure(benzene);
		int[] matches = pattern.match(molecule);
		int benz = matches.length;
		//
		IAtomContainer methylOctanoate = calculateMolecule("CCCCCCCC(=O)OC");
		//
		double Mwi = AtomContainerManipulator.getMass(molecule);
		double MwISTD = AtomContainerManipulator.getMass(methylOctanoate);
		//
		double RRFi = (1000 * (Mwi / MwISTD)) / (-61.3 + 88.8 * c + 18.7 * h - 41.3 * o + 6.4 * n + 64.0 * s - 20.2 * f - 23.5 * cl + 51.6 * br - 1.75 * i + 39.9 * si + 127 * benz);
		System.out.println("RRFi (" + name + "): " + RRFi);
	}

	private String calculateSmiles(String name) throws Exception {

		NameToStructure nameStructure = NameToStructure.getInstance();
		NameToStructureConfig nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true);
		OpsinResult opsinResult = nameStructure.parseChemicalName(name, nameStructureConfig);
		if(!"".equals(opsinResult.getMessage())) {
			throw new Exception("Could not parse the name.");
		}
		return opsinResult.getSmiles();
	}

	private IAtomContainer calculateMolecule(String smiles) throws CDKException {

		SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
		IAtomContainer molecule = smilesParser.parseSmiles(smiles);
		//
		// CDKAtomTypeMatcher matcher = CDKAtomTypeMatcher.getInstance(molecule.getBuilder());
		// for(IAtom atom : molecule.atoms()) {
		// IAtomType type = matcher.findMatchingAtomType(molecule, atom);
		// AtomTypeManipulator.configure(atom, type);
		// }
		//
		// CDKHydrogenAdder hydrogenAdder = CDKHydrogenAdder.getInstance(molecule.getBuilder());
		// hydrogenAdder.addImplicitHydrogens(molecule);
		//
		return molecule;
	}

	private int countAtoms(IAtomContainer molecule, String name) {

		int count = 0;
		for(IAtom atom : molecule.atoms()) {
			if(name.equals("H")) {
				count += atom.getImplicitHydrogenCount();
			} else if(name.equals(atom.getSymbol())) {
				count++;
			}
		}
		return count;
	}
}
