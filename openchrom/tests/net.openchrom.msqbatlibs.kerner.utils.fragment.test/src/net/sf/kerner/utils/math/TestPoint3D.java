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
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import net.sf.kerner.utils.math.point.Point3D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPoint3D {

	private Point3D p1;
	private Point3D p2;

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
	public final void testHashCode01() {

		p1 = new Point3D(1, 1, 1);
		p2 = new Point3D(1, 1, 1);
		assertEquals(p1.hashCode(), p2.hashCode());
	}

	@Test
	public final void testEqualsObject01() {

		p1 = new Point3D(1, 1, 1);
		p2 = new Point3D(1, 1, 1);
		assertEquals(p1, p2);
	}

	@Test
	public final void testHashCode02() {

		p1 = new Point3D(1, 1, 1);
		p2 = new Point3D(1, 1, 2);
		assertFalse(p1.hashCode() == p2.hashCode());
	}

	@Test
	public final void testEqualsObject02() {

		p1 = new Point3D(1, 1, 1);
		p2 = new Point3D(1, 1, 2);
		assertFalse(p1.equals(p2));
	}
}