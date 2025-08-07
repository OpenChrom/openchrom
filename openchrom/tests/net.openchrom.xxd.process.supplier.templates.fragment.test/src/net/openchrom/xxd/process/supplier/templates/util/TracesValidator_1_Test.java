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
package net.openchrom.xxd.process.supplier.templates.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TracesValidator_1_Test {

	private TracesValidator tracesValidator = new TracesValidator();

	@Test
	public void test1() {

		assertEquals(-1, tracesValidator.getTrace(""));
	}

	@Test
	public void test2() {

		assertEquals(-1, tracesValidator.getTrace(null));
	}

	@Test
	public void test3() {

		assertEquals(-1, tracesValidator.getTrace("-1"));
	}

	@Test
	public void test4() {

		assertEquals(-1, tracesValidator.getTrace("0"));
	}

	@Test
	public void test5() {

		assertEquals(1, tracesValidator.getTrace("1"));
	}
}
