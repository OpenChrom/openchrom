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
package net.sf.kerner.utils.collections.list.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.list.DefaultListVisitorImpl;
import net.sf.kerner.utils.collections.list.ListWalkerDefault;
import net.sf.kerner.utils.collections.list.visitor.VisitorListDefault;
import net.sf.kerner.utils.counter.Counter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestListWalkerDefault {

	private ListWalkerDefault<Integer> w;
	private List<Integer> l;
	private VisitorListDefault<Integer> v;

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

	@Ignore
	@Test
	public final void testAddFilter() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testClearFilters() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testAddVisitor() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testClearVisitors() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testBeforeWalk() {

		fail("Not yet implemented"); // TODO
	}

	@Test(expected = NullPointerException.class)
	public final void testWalk01() {

		w = new ListWalkerDefault<Integer>();
		w.walk(null);
	}

	@Test
	public final void testWalk02() {

		w = new ListWalkerDefault<Integer>();
		l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		w.walk(l);
	}

	@Test
	public final void testWalk03() {

		w = new ListWalkerDefault<Integer>();
		l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		final Counter c2 = new Counter();
		v = new DefaultListVisitorImpl<Integer>() {

			
			public Void visit(final Integer e, final int index) {

				c2.count();
				return null;
			};
		};
		w.addVisitor(v);
		w.walk(l);
		assertEquals(l.size(), c2.getCount());
	}

	@Test
	public final void testWalk04() {

		w = new ListWalkerDefault<Integer>();
		l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
		final Counter c2 = new Counter();
		v = new DefaultListVisitorImpl<Integer>() {

			
			public Void visit(final Integer e, final int index) {

				c2.count();
				return null;
			};
		};
		w.addFilter(new Filter<Integer>() {

			public boolean filter(final Integer element) {

				if(element <= 4)
					return false;
				return true;
			}
		});
		w.addVisitor(v);
		w.walk(l);
		assertEquals(l.size() / 2, c2.getCount());
	}

	@Ignore
	@Test
	public final void testAfterWalk() {

		fail("Not yet implemented"); // TODO
	}
}
