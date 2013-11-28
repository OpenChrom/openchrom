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

import org.openscience.cdk.charges.MMFF94PartialCharges;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.modeling.builder3d.ModelBuilder3D;

import junit.framework.TestCase;

import net.openchrom.logging.core.Logger;



public class CDKPolarizabilityDescriptor_1_Test extends TestCase{
	CDKPolarizabilityDescriptor desc;
	
	private static final Logger logger = Logger.getLogger(CDKPolarizabilityDescriptor_1_Test.class);
	
	@Override
	protected void setUp() throws Exception {
			super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		desc = null;
		super.tearDown();
	}

	public void testMethod_1() {

		
		desc = new CDKPolarizabilityDescriptor();
		IMolecule benz = new CDKSmilesToIMoleculeConverter().generate("c1=cc=cc=c1");
		IMolecule ethane = new CDKSmilesToIMoleculeConverter().generate("cccccc");
		IMolecule triFluoroEthane = new CDKSmilesToIMoleculeConverter().generate("c[F]c");
		IMolecule bromFluorEthane = new OPSINIupacToIMoleculeConverter().generate("1,1,2-Tribromo-1,2,2-trifluoroethane");
		logger.info("benzene:\n " + desc.describe(benz));
		logger.info("\nethane:\n " + desc.describe(ethane));
		logger.info("\ntriFluoroEthane:\n " + desc.describe(triFluoroEthane));
		logger.info("\nbromFluorEthane:\n " + desc.describe(bromFluorEthane));
		//
		logger.info("Polarizabilities of carbocations.");
		IMolecule ethanol = // new CDKSmilesToIMoleculeConverter().generate("cc[o+]");
		new OPSINIupacToIMoleculeConverter().generate("2-chloro-3-fluoro(biphenyl)");
		logger.info("\ntriFluoroEthane:\n " + desc.describe(triFluoroEthane));
		MMFF94PartialCharges charges = new MMFF94PartialCharges();
		;
		try {
			ModelBuilder3D mb3d = ModelBuilder3D.getInstance();
			ethanol = mb3d.generate3DCoordinates(ethanol, false);
			charges.calculateCharges(ethanol);
			ethanol = (IMolecule)charges.assignMMFF94PartialCharges(ethanol);
		} catch(CDKException e1) {
			logger.warn("Something went wrong while assigning the polarizability of the molecule " + ethanol
					+ "\n This is becaus the following error occured " + e1);
		}
		catch(IOException e2){
			logger.warn("Something went wrong while assigning the polarizability of the molecule " + ethanol
					+ "\n This is becaus the following error occured " +e2);
		}
		catch(CloneNotSupportedException e3){
			logger.warn("Something went wrong while assigning the polarizability of the molecule " + ethanol
					+ "\n This is becaus the following error occured " +e3);
		}
		catch(Exception e4){
			logger.warn("Something went wrong while assigning the polarizability of the molecule " + ethanol
					+ "\n This is becaus the following error occured " +e4);
			
		}
		// double[]ens = charges.getPaulingElectronegativities(ethanol, true);
		
		for(IAtom atom : ethanol.atoms()) {
			logger.info("Atom has a charge of " + (Double)atom.getProperty("MMFF94charge"));
			logger.info("Atom " + atom.getSymbol() + " has en of "); // + ens[i] );
			// System.out.println("The softness of Atom is " + charges.getAtomicSoftnessCore(ethanol, i));
		}
	}
	
}
