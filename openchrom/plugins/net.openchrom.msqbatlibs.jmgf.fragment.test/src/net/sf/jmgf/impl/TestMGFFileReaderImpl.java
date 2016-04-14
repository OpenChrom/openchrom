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
package net.sf.jmgf.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.jmgf.MGFFileReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMGFFileReaderImpl {

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
	public void test01() throws Exception {

		final MGFFileReader reader = new MGFFileReaderImpl(new File("src/test/resources/singleElement.mgf"));
		final MGFFile file = reader.read();
		reader.close();
		assertNotNull(file);
		assertEquals(1, file.getElements().size());
		final MGFElement element = file.getElements().iterator().next();
		assertNotNull(element);
		assertEquals(45, element.getPeaks().size());
		assertEquals("1+", element.getTags(MGFElement.Identifier.CHARGE));
	}

	@Test
	public void test02() throws Exception {

		final MGFFileReader reader = new MGFFileReaderImpl(new File("src/test/resources/manyElements.mgf"));
		final MGFFile file = reader.read();
		reader.close();
		assertNotNull(file);
		assertEquals(7532, file.getElements().size());
	}

	@Test
	public void test03() throws Exception {

		final MGFFileReader reader = new MGFFileReaderImpl(new File("src/test/resources/unexpectedEnd.mgf"));
		final MGFFile file = reader.read();
		reader.close();
		assertNotNull(file);
		assertEquals(1, file.getElements().size());
		assertEquals(3, file.getElements().get(0).getPeaks().size());
	}

	@Test
	public void test04() throws Exception {

		final MGFFileReader reader = new MGFFileReaderImpl(new File("src/test/resources/Testgemisch_U_PP_LXQ_20141210.mgf"));
		final MGFFile file = reader.read();
		reader.close();
		assertNotNull(file);
		assertEquals(2259, file.getElements().size());
	}
}
