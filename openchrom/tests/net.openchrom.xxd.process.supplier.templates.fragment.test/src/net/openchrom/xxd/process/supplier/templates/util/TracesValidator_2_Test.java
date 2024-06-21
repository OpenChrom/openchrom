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

public class TracesValidator_2_Test extends TestCase {

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

		IStatus status = tracesValidator.validate(null);
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test2() {

		IStatus status = tracesValidator.validate("");
		assertTrue(status.isOK());
		Set<Integer> traces = tracesValidator.getTraces();
		assertEquals(0, traces.size()); // TIC
	}

	public void test3() {

		IStatus status = tracesValidator.validate("A");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test4() {

		IStatus status = tracesValidator.validate(" ");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test5() {

		IStatus status = tracesValidator.validate("A - B");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test6() {

		IStatus status = tracesValidator.validate("18 - B");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test7() {

		IStatus status = tracesValidator.validate("A - 18");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test8() {

		IStatus status = tracesValidator.validate("18 - 45 - 200");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}

	public void test9() {

		IStatus status = tracesValidator.validate("32 - 18");
		assertFalse(status.isOK());
		assertEquals(0, tracesValidator.getTraces().size());
	}
}
