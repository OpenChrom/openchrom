/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
