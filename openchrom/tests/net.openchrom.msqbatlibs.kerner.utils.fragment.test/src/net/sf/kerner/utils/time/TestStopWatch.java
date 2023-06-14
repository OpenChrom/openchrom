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
package net.sf.kerner.utils.time;

import net.sf.kerner.utils.time.StopWatch.AlreadyRunningException;
import net.sf.kerner.utils.time.StopWatch.NotRunningException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStopWatch {

	private StopWatch w;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		w = new StopWatch();
	}

	@After
	public void tearDown() throws Exception {

		w = null;
	}

	@Test
	public final void testStart01() {

		w.start();
	}

	@Test(expected = AlreadyRunningException.class)
	public final void testStart02() {

		w.start();
		w.start();
	}

	@Test(expected = NotRunningException.class)
	public final void testRound01() {

		w.round();
	}

	@Test
	public final void testRound02() {

		w.start();
		w.round();
	}

	@Test(expected = NotRunningException.class)
	public final void testStop01() {

		w.stop();
	}

	@Test
	public final void testStop02() {

		w.start();
		w.stop();
	}
}
