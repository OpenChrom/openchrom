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
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.Mappings;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.smarts.SmartsPattern;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 * IOFI recommended practice for the use of
 * predicted relative-response factors for the
 * rapid quantification of volatile flavouring
 * compounds by GC-FID
 * 
 * T. Cachet,* H. Brevard, A. Chaintreau, J. Demyttenaere, L. French,
 * K. Gassenmeier, D. Joulain, T. Koenig, H. Leijs, P. Liddle, G. Loesing,
 * M. Marchant, Ph. Merle, K. Saito, C. Schippa, F. Sekiya and T. Smith
 * 
 * DOI 10.1002/ffj.3311
 * 
 * @author pwenig
 *
 */
public class RelativeResponseCalculator {

	private IAtomContainer methylOctanoate;
	private IAtomContainer benzene;

	public RelativeResponseCalculator() throws CDKException {
		methylOctanoate = calculateMolecule("CCCCCCCC(=O)OC");
		benzene = calculateMolecule("C1=CC=CC=C1");
	}

	public double calculateResponseIUPAC(String iupacName) throws Exception {

		String smiles = calculateSmiles(iupacName);
		return calculateResponseSMILES(smiles);
	}

	public double calculateResponseSMILES(String smiles) throws Exception {

		IAtomContainer molecule = calculateMolecule(smiles);
		return calculateResponse(molecule);
	}

	private double calculateResponse(IAtomContainer molecule) throws CDKException {

		double relativeResponseFactor = 0.0d;
		if(molecule != null && methylOctanoate != null) {
			/*
			 * Count atoms.
			 */
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
			double Mwi = AtomContainerManipulator.getMass(molecule);
			double MwISTD = AtomContainerManipulator.getMass(methylOctanoate);
			//
			if(MwISTD != 0) {
				relativeResponseFactor = (1000 * (Mwi / MwISTD)) / //
						(//
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
			}
		}
		return relativeResponseFactor;
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
		return smilesParser.parseSmiles(smiles);
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

		int countSubstructure = 0;
		if(benzene != null) {
			Pattern pattern = SmartsPattern.findSubstructure(benzene);
			Mappings mappings = pattern.matchAll(molecule);
			countSubstructure = mappings.count() / 6;
		}
		return countSubstructure;
	}
}
