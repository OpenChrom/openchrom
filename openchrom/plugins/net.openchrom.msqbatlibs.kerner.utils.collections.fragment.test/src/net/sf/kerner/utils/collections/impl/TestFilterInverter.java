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
package net.sf.kerner.utils.collections.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.filter.FilterInverter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFilterInverter {

	private FilterInverter<Boolean> fi;
	private Filter<Boolean> f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testFilterInverter01() {

		f = new Filter<Boolean>() {

			public boolean filter(final Boolean element) {

				return element;
			}
		};
		fi = new FilterInverter<Boolean>(f);
	}

	@Test(expected = NullPointerException.class)
	public final void testFilterInverter02() {

		fi = new FilterInverter<Boolean>(null);
	}

	@Test
	public final void testVisit01() {

		f = new Filter<Boolean>() {

			public boolean filter(final Boolean element) {

				return element;
			}
		};
		fi = new FilterInverter<Boolean>(f);
		assertFalse(fi.filter(Boolean.TRUE));
	}

	@Test
	public final void testVisit02() {

		f = new Filter<Boolean>() {

			public boolean filter(final Boolean element) {

				return element;
			}
		};
		fi = new FilterInverter<Boolean>(f);
		assertTrue(fi.filter(Boolean.FALSE));
	}
}
