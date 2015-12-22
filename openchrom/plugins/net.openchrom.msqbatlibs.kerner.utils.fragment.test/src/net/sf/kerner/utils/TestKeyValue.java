/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import net.sf.kerner.utils.pair.KeyValue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-04-25
 */
public class TestKeyValue {

	private String key1;
	private String key2;
	private KeyValue<String, String> k1;
	private KeyValue<String, String> k2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		key1 = "key1";
		key2 = "key2";
		k1 = new KeyValue<String, String>(key1);
		k2 = new KeyValue<String, String>(key2);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testHashCode() {

		assertNotSame(key1.hashCode(), key2.hashCode());
	}

	@Test
	public final void testHashCode01() {

		k2 = new KeyValue<String, String>(key1);
		assertEquals(k1.hashCode(), k2.hashCode());
	}

	@Test
	public final void testKeyValueKV() {

		k1 = new KeyValue<String, String>("key1", "value1");
	}

	@Test
	public final void testKeyValueKV01() {

		k1 = new KeyValue<String, String>("key1", "value1");
		assertEquals("key1", k1.getKey());
	}

	@Test
	public final void testKeyValueKV02() {

		k1 = new KeyValue<String, String>("key1", "value");
		assertEquals("value", k1.getValue());
	}

	@Test(expected = NullPointerException.class)
	public final void testKeyValueKV03() {

		k1 = new KeyValue<String, String>(null, null);
	}

	@Test(expected = NullPointerException.class)
	public final void testKeyValueK() {

		k1 = new KeyValue<String, String>((KeyValue<String, String>)null);
	}

	@Test(expected = NullPointerException.class)
	public final void testKeyValueK01() {

		k1 = new KeyValue<String, String>((String)null);
	}

	@Test
	public final void testKeyValueKeyValueOfKV() {

		k1 = new KeyValue<String, String>(k2);
		assertEquals(k2.getKey(), k1.getKey());
	}

	@Test
	public final void testKeyValueKeyValueOfKV01() {

		k1 = new KeyValue<String, String>(k2);
		assertEquals(k2.getValue(), k1.getValue());
	}

	@Test
	public final void testEqualsObject() {

		k1 = new KeyValue<String, String>(k2);
		assertEquals(k1, k2);
	}

	@Test
	public final void testEqualsObject01() {

		assertEquals(k1, k1);
	}

	@Test
	public final void testEqualsObject02() {

		k2 = new KeyValue<String, String>("key1", null);
		assertEquals(k1, k2);
	}

	@Test
	public final void testEqualsObject03() {

		k1 = new KeyValue<String, String>("key1", "value");
		k2 = new KeyValue<String, String>("key1", "value");
		assertEquals(k1, k2);
	}

	@Test
	public final void testEqualsObject04() {

		assertNotSame(k1, k2);
	}

	@Test
	@Ignore
	public final void testGetKey() {

		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public final void testGetValue() {

		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetValue() {

		k2.setValue("hans");
		assertEquals("hans", k2.getValue());
	}
}
