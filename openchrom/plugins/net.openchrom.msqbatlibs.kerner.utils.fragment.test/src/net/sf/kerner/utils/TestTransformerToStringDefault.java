/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils;

import static org.junit.Assert.assertEquals;
import net.sf.kerner.utils.transformer.TransformerToStringDefault;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTransformerToStringDefault {

	private TransformerToStringDefault t;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		t = new TransformerToStringDefault();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testTransform01() {

		assertEquals("1", t.transform(Integer.valueOf(1)));
	}

	@Test
	public final void testTransform02() {

		assertEquals("null", t.transform(null));
	}
}
