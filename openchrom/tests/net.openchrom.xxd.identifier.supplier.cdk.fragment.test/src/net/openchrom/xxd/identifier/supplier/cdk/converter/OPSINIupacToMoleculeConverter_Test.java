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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.openscience.cdk.interfaces.IAtomContainer;

public class OPSINIupacToMoleculeConverter_Test {

	private OPSINIupacToMoleculeConverter converter = new OPSINIupacToMoleculeConverter();

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
