/*******************************************************************************
 * Copyright (c) 2013, 2019 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.descriptors;

import java.io.IOException;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.charges.MMFF94PartialCharges;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.modeling.builder3d.ModelBuilder3D;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.OPSINIupacToMoleculeConverter;

import junit.framework.TestCase;

public class CDKPolarizabilityDescriptor_1_Test extends TestCase {

	private CDKPolarizabilityDescriptor descriptor;
	private CDKSmilesToMoleculeConverter smilesToIMoleculeConverter;
	private OPSINIupacToMoleculeConverter iupacToIMoleculeConverter;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		descriptor = new CDKPolarizabilityDescriptor();
		smilesToIMoleculeConverter = new CDKSmilesToMoleculeConverter();
		iupacToIMoleculeConverter = new OPSINIupacToMoleculeConverter();
	}

	@Override
	protected void tearDown() throws Exception {

		descriptor = null;
		smilesToIMoleculeConverter = null;
		iupacToIMoleculeConverter = null;
		super.tearDown();
	}

	public void testDescribeMethod_1() {

		IAtomContainer benz = new CDKSmilesToMoleculeConverter().generate("c1=cc=cc=c1");
		assertEquals(10.542d, descriptor.describe(benz), 0.0001d);
	}

	public void testDescribeMethod_2() {

		IAtomContainer ethane = smilesToIMoleculeConverter.generate("cccccc");
		assertEquals(9.48d, descriptor.describe(ethane), 0.0001d);
	}

	public void testDescribeMethod_3() {

		IAtomContainer triFluoroEthane = smilesToIMoleculeConverter.generate("c[F]c");
		assertEquals(2.424d, descriptor.describe(triFluoroEthane), 0.0001d);
	}

	public void testDescribeMethod_4() {

		IAtomContainer bromFluorEthane = iupacToIMoleculeConverter.generate("1,1,2-Tribromo-1,2,2-trifluoroethane");
		assertEquals(13.516d, descriptor.describe(bromFluorEthane), 0.0001d);
	}

	public void testDescribeMethod_5() {

		IAtomContainer ethanol = iupacToIMoleculeConverter.generate("2-chloro-3-fluoro(biphenyl)");
		assertEquals(22.223d, descriptor.describe(ethanol), 0.0001d);
	}

	public void testDescribeMethod_6() {

		assertEquals(0.0d, descriptor.describe(null));
	}

	public void testDescribeMethod_7() {

		IAtomContainer ethanol = iupacToIMoleculeConverter.generate("2-chloro-3-fluoro(biphenyl)");
		MMFF94PartialCharges charges = new MMFF94PartialCharges();
		try {
			ModelBuilder3D mb3d = ModelBuilder3D.getInstance(DefaultChemObjectBuilder.getInstance());
			ethanol = mb3d.generate3DCoordinates(ethanol, false);
			charges.calculateCharges(ethanol);
			ethanol = (IAtomContainer)charges.assignMMFF94PartialCharges(ethanol);
			assertNotNull(ethanol);
		} catch(CDKException e1) {
			assertTrue(false);
		} catch(IOException e2) {
			assertTrue(false);
		} catch(CloneNotSupportedException e3) {
			assertTrue(false);
		} catch(Exception e4) {
			assertTrue(false);
		}
	}
}