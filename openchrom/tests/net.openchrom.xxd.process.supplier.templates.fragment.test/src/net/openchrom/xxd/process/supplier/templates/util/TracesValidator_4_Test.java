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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.junit.Test;

public class TracesValidator_4_Test {

	private TracesValidator tracesValidator = new TracesValidator();

	@Test
	public void test1() {

		IStatus status = tracesValidator.validate("18");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(1, traces.size());
		assertTrue(traces.contains(18));
	}

	@Test
	public void test2() {

		IStatus status = tracesValidator.validate("18 28");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(2, traces.size());
		assertTrue(traces.contains(18));
		assertTrue(traces.contains(28));
	}

	@Test
	public void test3() {

		IStatus status = tracesValidator.validate("18 28 32");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(3, traces.size());
		assertTrue(traces.contains(18));
		assertTrue(traces.contains(28));
		assertTrue(traces.contains(32));
	}

	@Test
	public void test4() {

		IStatus status = tracesValidator.validate("18 - 32");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(15, traces.size());
		assertFalse(traces.contains(17));
		assertTrue(traces.contains(18));
		assertTrue(traces.contains(32));
		assertFalse(traces.contains(33));
	}
}
