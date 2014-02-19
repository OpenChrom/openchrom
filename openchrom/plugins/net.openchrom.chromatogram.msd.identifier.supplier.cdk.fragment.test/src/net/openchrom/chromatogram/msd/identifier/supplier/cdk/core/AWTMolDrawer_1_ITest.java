/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.TestPathHelper;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.converter.ImageConverter;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.support.AWTMolDrawer;

import junit.framework.TestCase;

public class AWTMolDrawer_1_ITest extends TestCase {

	private AWTMolDrawer awtMolDrawer;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		String imageFile = TestPathHelper.getAbsolutePath(TestPathHelper.TEST_DIRECTORY_EXPORT_PNG) + "TEST.png";
		awtMolDrawer = new AWTMolDrawer("c1(nnc)cccc1c", imageFile, ImageConverter.DEFAULT_WIDTH, ImageConverter.DEFAULT_HEIGHT);
	}

	@Override
	protected void tearDown() throws Exception {

		awtMolDrawer = null;
		super.tearDown();
	}

	public void testDrawer_1() {

		assertNotNull(awtMolDrawer);
	}
}
