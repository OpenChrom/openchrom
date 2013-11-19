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

import java.io.IOException;

import org.openscience.cdk.charges.InductivePartialCharges;
import org.openscience.cdk.charges.MMFF94PartialCharges;
import org.openscience.cdk.charges.Polarizability;
import org.openscience.cdk.charges.StabilizationCharges;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.modeling.builder3d.ModelBuilder3D;

public class CDKPolarizabilityDescriptor implements IStructureDescriptor {

	Polarizability polarizability;

	public CDKPolarizabilityDescriptor() {

		polarizability = new Polarizability();
	}

	@Override
	public String describe(IMolecule molecule) {

		double result = polarizability.calculateKJMeanMolecularPolarizability(molecule);
		return "" + result;
	}

	//
	public static void main(String[] args) throws Exception {

		CDKPolarizabilityDescriptor desc = new CDKPolarizabilityDescriptor();
		IMolecule benz = new CDKSmilesToIMoleculeConverter().generate("c1=cc=cc=c1");
		IMolecule ethane = new CDKSmilesToIMoleculeConverter().generate("cccccc");
		IMolecule triFluoroEthane = new CDKSmilesToIMoleculeConverter().generate("c[F]c");
		IMolecule bromFluorEthane = new OPSINIupacToIMoleculeConverter().generate("1,1,2-Tribromo-1,2,2-trifluoroethane");
		System.out.println("benzene:\n " + desc.describe(benz));
		System.out.println("\nethane:\n " + desc.describe(ethane));
		System.out.println("\ntriFluoroEthane:\n " + desc.describe(triFluoroEthane));
		System.out.println("\nbromFluorEthane:\n " + desc.describe(bromFluorEthane));
		//
		System.out.println("Polarizabilities of carbocations.");
		IMolecule ethanol = // new CDKSmilesToIMoleculeConverter().generate("cc[o+]");
		new OPSINIupacToIMoleculeConverter().generate("2-chloro-3-fluoro(biphenyl)");
		System.out.println("\ntriFluoroEthane:\n " + desc.describe(triFluoroEthane));
		MMFF94PartialCharges charges = new MMFF94PartialCharges();
		;
		try {
			ModelBuilder3D mb3d = ModelBuilder3D.getInstance();
			ethanol = mb3d.generate3DCoordinates(ethanol, false);
			charges.calculateCharges(ethanol);
			ethanol = (IMolecule)charges.assignMMFF94PartialCharges(ethanol);
		} catch(CDKException e1) {
			// logger.warn(e1);
			System.out.println("error:");
			e1.printStackTrace();
		}
		// double[]ens = charges.getPaulingElectronegativities(ethanol, true);
		int i = 0;
		for(IAtom atom : ethanol.atoms()) {
			System.out.println("Atom has a charge of " + (Double)atom.getProperty("MMFF94charge"));
			System.out.println("Atom " + atom.getSymbol() + " has en of "); // + ens[i] );
			// System.out.println("The softness of Atom is " + charges.getAtomicSoftnessCore(ethanol, i));
			i++;
		}
	}
}
