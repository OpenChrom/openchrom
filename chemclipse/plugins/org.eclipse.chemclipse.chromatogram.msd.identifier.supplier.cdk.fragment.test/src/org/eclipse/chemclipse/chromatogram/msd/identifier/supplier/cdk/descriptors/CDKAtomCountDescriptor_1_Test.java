/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.descriptors;

import org.openscience.cdk.interfaces.IMolecule;

import junit.framework.TestCase;

import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.converter.OPSINIupacToMoleculeConverter;
import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.descriptors.CDKAtomCountDescriptor;

public class CDKAtomCountDescriptor_1_Test extends TestCase {

	private CDKAtomCountDescriptor descriptor;
	private CDKSmilesToMoleculeConverter smilesToIMoleculeConverter;
	private OPSINIupacToMoleculeConverter iupacToIMoleculeConverter;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		descriptor = new CDKAtomCountDescriptor();
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

		IMolecule benz = new CDKSmilesToMoleculeConverter().generate("c1=cc=cc=c1");
		assertEquals(12.0d, descriptor.describe(benz));
	}

	public void testDescribeMethod_2() {

		IMolecule ethane = smilesToIMoleculeConverter.generate("cccccc");
		assertEquals(14.0d, descriptor.describe(ethane));
	}

	public void testDescribeMethod_3() {

		IMolecule triFluoroEthane = smilesToIMoleculeConverter.generate("c[F]c");
		assertEquals(7.0d, descriptor.describe(triFluoroEthane));
	}

	public void testDescribeMethod_4() {

		IMolecule bromFluorEthane = iupacToIMoleculeConverter.generate("1,1,2-Tribromo-1,2,2-trifluoroethane");
		assertEquals(8.0d, descriptor.describe(bromFluorEthane));
	}

	public void testDescribeMethod_5() {

		IMolecule ethanol = iupacToIMoleculeConverter.generate("2-chloro-3-fluoro(biphenyl)");
		assertEquals(22.0d, descriptor.describe(ethanol));
	}

	public void testDescribeMethod_6() {

		assertEquals(0.0d, descriptor.describe(null));
	}
}
