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

import junit.framework.TestCase;

public class TracesValidator_1_Test extends TestCase {

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

		assertEquals(-1, tracesValidator.getTrace(""));
	}

	public void test2() {

		assertEquals(-1, tracesValidator.getTrace(null));
	}

	public void test3() {

		assertEquals(-1, tracesValidator.getTrace("-1"));
	}

	public void test4() {

		assertEquals(-1, tracesValidator.getTrace("0"));
	}

	public void test5() {

		assertEquals(1, tracesValidator.getTrace("1"));
	}
}
