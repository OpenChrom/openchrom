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
/**
 * 
 */
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-03-01
 */
@RunWith(Parameterized.class)
public class TestPointGetDistance {

	private Point x;
	private Point y;
	private double result;

	@Parameters
	public static Collection<Object[]> getParams() {

		return Arrays.asList(new Object[][]{
				// x, y, result
		{new Point(1, 1), new Point(1, 1), 0}, {new Point(1, 1), new Point(1, 2), 1}, {new Point(1, 1), new Point(2, 1), 1}, {new Point(1, 1), new Point(2, 2), 1.41}, {new Point(0, 0), new Point(2, 2), 2.82}, {new Point(2, 2), new Point(0, 0), 2.82},});
	}

	public TestPointGetDistance(Point x, Point y, double result) {

		this.x = x;
		this.y = y;
		this.result = result;
	}

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

	/**
	 * Test method for {@link net.sf.kerner.utils.math.Point#getDistance(net.sf.kerner.utils.math.Point)} .
	 */
	@Test
	public final void testGetDistance() {

		assertEquals(result, x.getDistance(y), 0.01);
	}
}
