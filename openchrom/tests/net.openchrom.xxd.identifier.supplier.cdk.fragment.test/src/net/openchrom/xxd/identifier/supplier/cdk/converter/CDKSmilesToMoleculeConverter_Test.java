/*******************************************************************************
 * Copyright (c) 2013, 2025 Marwin Wollschläger.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - adjustments
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

import junit.framework.TestCase;

public class CDKSmilesToMoleculeConverter_Test extends TestCase {

	private CDKSmilesToMoleculeConverter converter;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		converter = new CDKSmilesToMoleculeConverter();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	@Test
	public void testNotNull() {

		IAtomContainer molecule = converter.generate("c1=cc=cc=c1");
		assertNotNull(molecule);
	}

	@Test
	public void testNull() {

		IAtomContainer molecule = converter.generate(null);
		assertNull(molecule);
	}
}
