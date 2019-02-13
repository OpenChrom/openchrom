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

public class AssignerReference_1_Test extends TestCase {

	private AssignerReference setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new AssignerReference();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertEquals("", setting.getName());
	}

	public void test2() {

		assertEquals(0.0d, setting.getStartRetentionTime());
	}

	public void test3() {

		assertEquals(0.0d, setting.getStopRetentionTime());
	}

	public void test4() {

		assertEquals("", setting.getIdentifier());
	}
}
