/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.gaml.fragment.test;

import org.eclipse.chemclipse.rcp.app.test.TestAssembler;

import net.openchrom.wsd.converter.supplier.gaml.Activator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {

		TestAssembler testAssembler = new TestAssembler(Activator.getContext().getBundle().getBundleContext().getBundles());
		TestSuite suite = new TestSuite("Run all tests.");
		String bundleAndPackageName = "net.openchrom.wsd.converter.supplier.gaml";
		testAssembler.assembleTests(suite, bundleAndPackageName, bundleAndPackageName, "*_Test"); // Unit
		testAssembler.assembleTests(suite, bundleAndPackageName, bundleAndPackageName, "*_ITest"); // Integration
		return suite;
	}
}
