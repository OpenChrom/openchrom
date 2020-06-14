/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;

import junit.framework.TestCase;

public class TracesValidator_3_Test extends TestCase {

	private TracesValidator tracesValidator = new TracesValidator();

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		IStatus status = tracesValidator.validate("18");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(1, traces.size());
		assertTrue(traces.contains(18));
	}

	public void test2() {

		IStatus status = tracesValidator.validate("18, 28");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(2, traces.size());
		assertTrue(traces.contains(18));
		assertTrue(traces.contains(28));
	}

	public void test3() {

		IStatus status = tracesValidator.validate("18, 28, 32");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(3, traces.size());
		assertTrue(traces.contains(18));
		assertTrue(traces.contains(28));
		assertTrue(traces.contains(32));
	}

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
