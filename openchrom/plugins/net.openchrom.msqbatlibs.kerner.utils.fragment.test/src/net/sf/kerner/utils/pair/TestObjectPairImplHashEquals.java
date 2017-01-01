/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.pair;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TestObjectPairImplHashEquals {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private PairImpl o1;
	private PairImpl o2;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void test01() {

		o1 = new PairImpl(1, 1);
		o2 = new PairImpl(1, 1);
		assertEquals(o1, o2);
		assertEquals(o1.hashCode(), o2.hashCode());
	}

	@Test
	public final void test02() {

		o1 = new PairImpl(1, 2);
		o2 = new PairImpl(2, 1);
		assertEquals(o1, o2);
		assertEquals(o1.hashCode(), o2.hashCode());
	}
}
