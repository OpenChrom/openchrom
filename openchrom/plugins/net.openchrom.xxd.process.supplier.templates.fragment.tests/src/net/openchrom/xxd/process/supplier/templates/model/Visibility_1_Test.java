/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import junit.framework.TestCase;

public class Visibility_1_Test extends TestCase {

	public void test1() {

		assertEquals("TIC", Visibility.TIC.name());
	}

	public void test2() {

		assertEquals("TRACE", Visibility.TRACE.name());
	}

	public void test3() {

		assertEquals("BOTH", Visibility.BOTH.name());
	}

	public void test4() {

		assertTrue(Visibility.isTIC(Visibility.TIC));
	}

	public void test5() {

		assertFalse(Visibility.isTIC(Visibility.TRACE));
	}

	public void test6() {

		assertTrue(Visibility.isTIC(Visibility.BOTH));
	}

	public void test7() {

		assertFalse(Visibility.isTIC(null));
	}

	public void test8() {

		assertFalse(Visibility.isTRACE(Visibility.TIC));
	}

	public void test9() {

		assertTrue(Visibility.isTRACE(Visibility.TRACE));
	}

	public void test10() {

		assertTrue(Visibility.isTRACE(Visibility.BOTH));
	}

	public void test11() {

		assertFalse(Visibility.isTRACE(null));
	}
}