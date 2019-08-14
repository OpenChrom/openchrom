/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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
import net.sf.kerner.utils.pair.KeyValue;
import net.sf.kerner.utils.transformer.ViewKeyValueKey;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestViewKeyValueKey {

	private KeyValue<String, String> k;
	private ViewKeyValueKey<String> v;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		v = new ViewKeyValueKey<String>();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testTransform01() {

		k = new KeyValue<String, String>("key");
		assertEquals("key", v.transform(k));
	}
}
