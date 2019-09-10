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

import java.io.IOException;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.Mappings;
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

		calculateAndPrint("1,4-Epoxy-p-menthan"); // 0 "1,4-Cineole"
		//
		calculateAndPrint("2-nonanol"); // 0
		calculateAndPrint("tetralin"); // 1
		calculateAndPrint("1,4-dibromobenzene"); // 1
		calculateAndPrint("undecanol"); // 0
		calculateAndPrint("styrene"); // 1
		//
		calculateAndPrint("Pyridine-3-carbonitrile, 2-[2-(3,4-dihydroxyphenyl)-2-oxoethylthio]-4-methoxymethyl-6-methyl-"); // 1
		calculateAndPrint("Phenol, 4-(ethoxymethyl)-2-methoxy-"); // 1
		calculateAndPrint("Folic Acid"); // 1
		calculateAndPrint("3-Phenylpropylamine, N-acetyl-2-[3-[3,4,5-trimethoxyphenyl]propionyl]-"); // 2
		calculateAndPrint("1,3,2-Dioxaphosphorinan-2-amine, N,N-dimethyl-5,5-diphenyl-, 2-oxide"); // 2
	}

	private void calculateAndPrint(String iupacName) {

		double RRFi;
		try {
			RRFi = calculateRrfIupac(iupacName);
			System.out.println("RRFi (" + iupacName + "): " + RRFi);
		} catch(Exception e) {
			System.out.println("RRFi (" + iupacName + "): --");
		}
	}

	private double calculateRrfIupac(String iupacName) throws Exception {

		String smiles = calculateSmiles(iupacName);
		return calculateRrfSmiles(smiles);
	}

	private double calculateRrfSmiles(String smiles) throws Exception {

		IAtomContainer molecule = calculateMolecule(smiles);
		// exportFile(molecule, "Molecule.png");
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
		int benz = countBenzeneStructure(molecule);
		//
		IAtomContainer methylOctanoate = calculateMolecule("CCCCCCCC(=O)OC");
		//
		double Mwi = AtomContainerManipulator.getMass(molecule);
		double MwISTD = AtomContainerManipulator.getMass(methylOctanoate);
		//
		double RRFi = (1000 * (Mwi / MwISTD)) / (//
		-61.3 //
				+ 88.8 * c //
				+ 18.7 * h //
				- 41.3 * o //
				+ 6.4 * n //
				+ 64.0 * s //
				- 20.2 * f //
				- 23.5 * cl //
				+ 51.6 * br //
				- 1.75 * i //
				+ 39.9 * si //
				+ 127 * benz //
		);
		return RRFi;
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

	private int countBenzeneStructure(IAtomContainer molecule) throws CDKException {

		IAtomContainer benzene = calculateMolecule("C1=CC=CC=C1");
		Pattern pattern = SmartsPattern.findSubstructure(benzene);
		Mappings mappings = pattern.matchAll(molecule);
		return mappings.count() / 6;
	}

	private void exportFile(IAtomContainer molecule, String path) throws IOException, CDKException {

		DepictionGenerator depictionGenerator = new DepictionGenerator();
		depictionGenerator.depict(molecule).writeTo(path);
	}
}
