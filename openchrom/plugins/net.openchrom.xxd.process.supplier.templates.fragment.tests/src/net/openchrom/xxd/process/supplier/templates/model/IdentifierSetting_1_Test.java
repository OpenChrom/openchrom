/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import junit.framework.TestCase;

public class IdentifierSetting_1_Test extends TestCase {

	private IdentifierSetting setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new IdentifierSetting();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertEquals(0.0d, setting.getStartRetentionTimeMinutes());
	}

	public void test2() {

		assertEquals(0.0d, setting.getStopRetentionTimeMinutes());
	}

	public void test3() {

		assertEquals("", setting.getName());
	}

	public void test4() {

		assertEquals("", setting.getCasNumber());
	}

	public void test5() {

		assertEquals("", setting.getComments());
	}

	public void test6() {

		assertEquals("", setting.getContributor());
	}

	public void test7() {

		assertEquals("", setting.getReferenceId());
	}

	public void test8() {

		assertEquals("", setting.getTraces());
	}
}
