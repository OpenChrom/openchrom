/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

public class AssignerReference_2_Test extends TestCase {

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

		setting.setInternalStandard("Hello");
		assertEquals("Hello", setting.getInternalStandard());
	}

	public void test2() {

		setting.setPositionStart(3.45d);
		assertEquals(3.45d, setting.getPositionStart());
	}

	public void test3() {

		setting.setPositionStop(10.98d);
		assertEquals(10.98d, setting.getPositionStop());
	}

	public void test4() {

		setting.setIdentifier("World");
		assertEquals("World", setting.getIdentifier());
	}

	public void test5() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}