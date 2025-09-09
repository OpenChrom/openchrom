/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Visibility_1_Test {

	@Test
	public void test1() {

		assertEquals("TIC", Visibility.TIC.name());
	}

	@Test
	public void test2() {

		assertEquals("TRACE", Visibility.TRACE.name());
	}

	@Test
	public void test3() {

		assertEquals("BOTH", Visibility.BOTH.name());
	}

	@Test
	public void test4() {

		assertTrue(Visibility.isTIC(Visibility.TIC));
	}

	@Test
	public void test5() {

		assertFalse(Visibility.isTIC(Visibility.TRACE));
	}

	@Test
	public void test6() {

		assertTrue(Visibility.isTIC(Visibility.BOTH));
	}

	@Test
	public void test7() {

		assertFalse(Visibility.isTIC(null));
	}

	@Test
	public void test8() {

		assertFalse(Visibility.isTRACE(Visibility.TIC));
	}

	@Test
	public void test9() {

		assertTrue(Visibility.isTRACE(Visibility.TRACE));
	}

	@Test
	public void test10() {

		assertTrue(Visibility.isTRACE(Visibility.BOTH));
	}

	@Test
	public void test11() {

		assertFalse(Visibility.isTRACE(null));
	}
}