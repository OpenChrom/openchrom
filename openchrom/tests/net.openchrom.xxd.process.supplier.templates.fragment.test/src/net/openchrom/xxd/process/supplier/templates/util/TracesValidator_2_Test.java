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

import java.util.List;

import org.eclipse.chemclipse.support.validators.TraceValidator;
import org.eclipse.core.runtime.IStatus;
import org.junit.Test;

public class TracesValidator_2_Test {

	private TraceValidator traceValidator = new TraceValidator();

	@Test
	public void test1() {

		IStatus status = traceValidator.validate(null);
		assertFalse(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test2() {

		IStatus status = traceValidator.validate("");
		assertTrue(status.isOK());
		List<Integer> traces = traceValidator.getTracesAsInteger();
		assertEquals(0, traces.size()); // TIC
	}

	@Test
	public void test3() {

		IStatus status = traceValidator.validate("A");
		assertFalse(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test4() {

		IStatus status = traceValidator.validate(" ");
		assertTrue(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test5() {

		IStatus status = traceValidator.validate("A - B");
		assertFalse(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test6() {

		IStatus status = traceValidator.validate("18 - B");
		assertFalse(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test7() {

		IStatus status = traceValidator.validate("A - 18");
		assertFalse(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test8() {

		IStatus status = traceValidator.validate("18 - 45 - 200");
		assertTrue(status.isOK());
		assertEquals(29, traceValidator.getTracesAsInteger().size());
	}

	@Test
	public void test9() {

		IStatus status = traceValidator.validate("32 - 18");
		assertTrue(status.isOK());
		assertEquals(0, traceValidator.getTracesAsInteger().size());
	}
}