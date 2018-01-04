/*******************************************************************************
 * Copyright (c) 2013, 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.descriptors;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.Atom;

import junit.framework.TestCase;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.OPSINIupacToMoleculeConverter;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.descriptors.CDKVanDerWaalsDescriptor;

public class CDKVanDerWaalsDescriptor_1_Test extends TestCase {

	private CDKVanDerWaalsDescriptor descriptor;
	private CDKSmilesToMoleculeConverter smilesToIMoleculeConverter;
	private OPSINIupacToMoleculeConverter iupacToIMoleculeConverter;
	private IAtom atom;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		descriptor = new CDKVanDerWaalsDescriptor();
		smilesToIMoleculeConverter = new CDKSmilesToMoleculeConverter();
		iupacToIMoleculeConverter = new OPSINIupacToMoleculeConverter();
		atom = new Atom("C");
	}

	@Override
	protected void tearDown() throws Exception {

		descriptor = null;
		smilesToIMoleculeConverter = null;
		iupacToIMoleculeConverter = null;
		atom = null;
		super.tearDown();
	}

	public void testDescribeMethod_1() {

		IMolecule benz = new CDKSmilesToMoleculeConverter().generate("c1=cc=cc=c1");
		assertEquals(1.7d, descriptor.describe(atom, benz));
	}

	public void testDescribeMethod_2() {

		IMolecule ethane = smilesToIMoleculeConverter.generate("cccccc");
		assertEquals(1.7d, descriptor.describe(atom, ethane));
	}

	public void testDescribeMethod_3() {

		IMolecule triFluoroEthane = smilesToIMoleculeConverter.generate("c[F]c");
		assertEquals(1.7d, descriptor.describe(atom, triFluoroEthane));
	}

	public void testDescribeMethod_4() {

		IMolecule bromFluorEthane = iupacToIMoleculeConverter.generate("1,1,2-Tribromo-1,2,2-trifluoroethane");
		assertEquals(1.7d, descriptor.describe(atom, bromFluorEthane));
	}

	public void testDescribeMethod_5() {

		IMolecule ethanol = iupacToIMoleculeConverter.generate("2-chloro-3-fluoro(biphenyl)");
		assertEquals(1.7d, descriptor.describe(atom, ethanol));
	}

	public void testDescribeMethod_6() {

		assertEquals(1.0d, descriptor.describe(null, null));
	}
}
