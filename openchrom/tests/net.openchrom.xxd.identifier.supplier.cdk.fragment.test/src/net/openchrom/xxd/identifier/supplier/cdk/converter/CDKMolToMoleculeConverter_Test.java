/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import junit.framework.TestCase;

public class CDKMolToMoleculeConverter_Test extends TestCase {

	private CDKMolToMoleculeConverter converter;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		converter = new CDKMolToMoleculeConverter();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	@Test
	public void testNotNull() {

		IAtomContainer molecule = converter.generate("CT1001987571\n" //
				+ "\n" //
				+ "\n" //
				+ " 24 25  0  3  0               999 V2000\n" //
				+ "   -0.0171    1.4073    0.0098 C   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    0.0021   -0.0041    0.0020 C   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    1.1868    2.1007    0.0020 C   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -1.0133    2.3630    0.0190 N   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    2.3717    1.3829   -0.0136 N   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    0.8932    3.4034    0.0118 N   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    1.1884   -0.6467   -0.0128 N   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -1.0401   -0.6344    0.0090 O   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    2.3458    0.0368   -0.0214 C   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    3.6549    2.0897   -0.0220 C   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    1.2155   -2.1115   -0.0209 C   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    3.3959   -0.5761   -0.0355 O   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -0.4053    3.5654    0.0231 C   0 00  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -2.4574    2.1166    0.0226 C   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    3.9831    2.2592    1.0035 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    4.3975    1.4884   -0.5465 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    3.5388    3.0475   -0.5293 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    1.2124   -2.4692   -1.0505 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    2.1169   -2.4610    0.4825 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "    0.3373   -2.4940    0.4993 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -0.9129    4.5186    0.0303 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -2.8119    2.0494    1.0512 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -2.9671    2.9358   -0.4846 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "   -2.6677    1.1812   -0.4960 H   0  0  0  0  0  0  0  0  0  0  0  0\n" //
				+ "  1  2 01  0  1  0  0\n" //
				+ "  1  3 02  0  2  0  0\n" //
				+ "  1  4 01  0  1  0  0\n" //
				+ "  2  7 01  0  1  0  0\n" //
				+ "  2  8  2  0  0  0  0\n" //
				+ "  3  5 01  0  1  0  0\n" //
				+ "  3  6 01  0  1  0  0\n" //
				+ "  4 13 01  0  1  0  0\n" //
				+ "  4 14  1  0  0  0  0\n" //
				+ "  5  9 01  0  1  0  0\n" //
				+ "  5 10  1  0  0  0  0\n" //
				+ "  6 13 02  0  1  0  0\n" //
				+ "  7  9 01  0  1  0  0\n" //
				+ "  7 11  1  0  0  0  0\n" //
				+ "  9 12  2  0  0  0  0\n" //
				+ " 10 15  1  0  0  0  0\n" //
				+ " 10 16  1  0  0  0  0\n" //
				+ " 10 17  1  0  0  0  0\n" //
				+ " 11 18  1  0  0  0  0\n" //
				+ " 11 19  1  0  0  0  0\n" //
				+ " 11 20  1  0  0  0  0\n" //
				+ " 13 21  1  0  0  0  0\n" //
				+ " 14 22  1  0  0  0  0\n" //
				+ " 14 23  1  0  0  0  0\n" //
				+ " 14 24  1  0  0  0  0\n" //
				+ "M  END\n" //
				+ "$$$$\n" + "");
		assertNotNull(molecule);
	}

	@Test
	public void testNull() {

		IAtomContainer molecule = converter.generate(null);
		assertNull(molecule);
	}
}
