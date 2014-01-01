/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.converter;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.openchrom.supplier.cdk.TestPathHelper;
import net.openchrom.supplier.cdk.converter.ImageConverter;

import junit.framework.TestCase;

/**
 * A simple Unit test for the class MoleculeToImageConverter
 * 
 * @author administrator_marwin
 * 
 */
public class ImageConverter_1_ITest extends TestCase {

	private Image imageHexane;

	public void setUp() throws Exception {

		super.setUp();
		imageHexane = ImageConverter.getInstance().smilesToImage("c1=cc=cc=c1", ImageConverter.DEFAULT_WIDTH, ImageConverter.DEFAULT_HEIGHT);
	}

	public void tearDown() throws Exception {

		super.tearDown();
	}

	public void testMethod_1() {

		assertNotNull(imageHexane);
	}

	public void testMethod_4() {

		try {
			boolean success = ImageIO.write((RenderedImage)imageHexane, "PNG", new File(TestPathHelper.getAbsolutePath(TestPathHelper.TEST_DIRECTORY_EXPORT_PNG) + "TEST.png"));
			assertTrue(success);
		} catch(IOException e) {
			assertTrue(false);
		}
	}
}
