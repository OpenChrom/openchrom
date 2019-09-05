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

import junit.framework.TestCase;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class SmilesParser_1_Test extends TestCase {

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		NameToStructure nameStructure = NameToStructure.getInstance();
		NameToStructureConfig nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true);
		OpsinResult result = nameStructure.parseChemicalName("Iminostilbene", nameStructureConfig);
		assertEquals("APPEARS_AMBIGUOUS: Connection of imino to stilben", result.getMessage());
		// assertEquals("N=C1C(C=CC=C1)C=CC1=CC=CC=C1", result.getSmiles());
	}
}
