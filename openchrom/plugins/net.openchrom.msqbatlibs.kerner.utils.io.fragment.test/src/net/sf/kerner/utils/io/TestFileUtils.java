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
package net.sf.kerner.utils.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import net.sf.kerner.utils.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestFileUtils {

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
	public final void testDirCheck01() {

		assertTrue(UtilFile.dirCheck(Util.WORKING_DIR, false));
	}

	@Ignore
	@Test
	public final void testDirCheck02() {

		final File dir = new File("/mnt/autofs/exported-spectra");
		System.out.println(dir.canRead());
		System.out.println(dir.isDirectory());
		System.out.println(dir.length());
		assertTrue(UtilFile.dirCheck(new File("/mnt/autofs/exported-spectra"), false));
	}

	@Ignore
	@Test
	public final void testFileCheck01() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testDelete01() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testDeleteDir01() {

		fail("Not yet implemented"); // TODO
	}
}
