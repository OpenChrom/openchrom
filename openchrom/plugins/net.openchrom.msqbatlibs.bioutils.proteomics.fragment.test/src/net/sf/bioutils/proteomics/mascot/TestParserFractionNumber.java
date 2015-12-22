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
package net.sf.bioutils.proteomics.mascot;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestParserFractionNumber {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private ParserFractionNumber parser;
	private String string;

	@Before
	public void setUp() throws Exception {

		parser = new ParserFractionNumber();
		string = "Locus:1.4VP_7mu.12.223.17";
	}

	@After
	public void tearDown() throws Exception {

		parser = null;
		string = null;
	}

	@Test
	public final void testParseFractionNumber01() throws IOException {

		assertEquals(223, parser.parseFractionNumber(string));
	}
}
