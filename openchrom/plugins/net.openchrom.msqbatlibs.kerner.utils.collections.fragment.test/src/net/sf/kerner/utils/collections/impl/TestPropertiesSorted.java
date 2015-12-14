/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.impl;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;

import net.sf.kerner.utils.collections.PropertiesSorted;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPropertiesSorted {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private PropertiesSorted p;

	@Before
	public void setUp() throws Exception {

		p = new PropertiesSorted();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testKeys01() {

		p.put("2", "zwei");
		p.put("3", "drei");
		p.put("1", "eins");
		final Set<String> s = p.keySet();
		final Iterator<String> it = s.iterator();
		assertEquals("1", it.next());
		assertEquals("2", it.next());
		assertEquals("3", it.next());
	}

	@Test
	public final void testKeySet01() {

		p.put("2", "zwei");
		p.put("3", "drei");
		p.put("1", "eins");
		final Iterator<String> it = p.keySet().iterator();
		assertEquals("1", it.next());
		assertEquals("2", it.next());
		assertEquals("3", it.next());
	}
}
