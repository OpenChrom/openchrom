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

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class OpsinTestCase1 {

	public static void main(String[] args) {

		NameToStructure nameStructure = NameToStructure.getInstance();
		NameToStructureConfig nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true);// !!!
		OpsinResult result = nameStructure.parseChemicalName("tri -(1-chlorophenyl) ethane", nameStructureConfig);
		System.out.println(result.getSmiles());
	}
}
