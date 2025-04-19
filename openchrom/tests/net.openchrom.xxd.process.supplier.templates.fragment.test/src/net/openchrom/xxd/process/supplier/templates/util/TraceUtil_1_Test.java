/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.implementation.Peak;

import junit.framework.TestCase;

public class TraceUtil_1_Test extends TestCase {

	private TracesUtil tracesUtil = new TracesUtil();
	private IPeak peak = new Peak();

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertFalse(tracesUtil.isTraceMatch(null, null));
	}

	public void test2() {

		assertFalse(tracesUtil.isTraceMatch(null, ""));
	}

	public void test3() {

		assertTrue(tracesUtil.isTraceMatch(peak, null));
	}

	public void test4() {

		assertTrue(tracesUtil.isTraceMatch(peak, ""));
	}
}