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

import net.openchrom.logging.core.Logger;

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;
import junit.framework.TestCase;

public class OPSINIupacToIMoleculeConverter_1_Test extends TestCase {

	private static final Logger logger = Logger.getLogger(OPSINIupacToIMoleculeConverter_1_Test.class);

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testMethod_1() {

		NameToStructure nameStructure = NameToStructure.getInstance();
		NameToStructureConfig nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true);// !!!
		OpsinResult result = nameStructure.parseChemicalName("tri -(1-chlorophenyl) ethane", nameStructureConfig);
		logger.info(result.getSmiles());
	}
}
