/*******************************************************************************
 * Copyright (c) 2013, 2024 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - adjustments
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import junit.framework.TestCase;

public class OPSINIupacToMoleculeConverter_Test extends TestCase {

	private OPSINIupacToMoleculeConverter converter;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		converter = new OPSINIupacToMoleculeConverter();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	@Test
	public void testNotNull() {

		IAtomContainer molecule = converter.generate("tri -(1-chlorophenyl) ethane");
		assertNotNull(molecule);
	}

	@Test
	public void testNull() {

		IAtomContainer molecule = converter.generate(null);
		assertNull(molecule);
	}
}
