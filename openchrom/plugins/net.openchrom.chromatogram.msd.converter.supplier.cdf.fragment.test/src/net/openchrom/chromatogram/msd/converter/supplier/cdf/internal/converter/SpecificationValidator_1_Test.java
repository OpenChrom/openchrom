/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.converter;

import java.io.File;

import net.openchrom.chromatogram.msd.converter.supplier.cdf.TestPathHelper;

import junit.framework.TestCase;

public class SpecificationValidator_1_Test extends TestCase {

	private File file;
	private String spec;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		spec = TestPathHelper.getAbsolutePath(TestPathHelper.VALIDATOR_TEST_SPEC);
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void testValidateAgilentSpecification_1() {

		file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.VALIDATOR_TEST_1));
		file = SpecificationValidator.validateCDFSpecification(file);
		assertEquals("File", spec, file.getAbsolutePath());
	}

	/*
	 * public void testValidateAgilentSpecification_2() { file = new
	 * File(TestPathHelper.getAbsolutePath(TestPathHelper.VALIDATOR_TEST_2));
	 * file = SpecificationValidator.validateCDFSpecification(file);
	 * assertEquals("File", spec, file.getAbsolutePath()); }
	 */
	public void testValidateAgilentSpecification_3() {

		file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.VALIDATOR_TEST_3));
		file = SpecificationValidator.validateCDFSpecification(file);
		assertEquals("File", spec, file.getAbsolutePath());
	}

	public void testValidateAgilentSpecification_4() {

		file = new File(TestPathHelper.getAbsolutePath(TestPathHelper.VALIDATOR_TEST_4));
		file = SpecificationValidator.validateCDFSpecification(file);
		assertEquals("File", spec, file.getAbsolutePath());
	}
}
