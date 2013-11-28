/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.openchrom.logging.core.Logger;
import net.openchrom.supplier.cdk.TestPathHelper;

import junit.framework.TestCase;

/**
 * A simple Unit test for the class MoleculeToImageConverter
 * 
 * @author administrator_marwin
 * 
 */
public class MoleculeToImageConverter_1_ITest extends TestCase {

	private static final Logger logger = Logger.getLogger(MoleculeToImageConverter_1_ITest.class);

	public void tearDown() throws Exception {

		super.tearDown();
	}

	public void setUp() throws Exception {

		super.setUp();
		testMethod_1();
	}

	//
	public void testMethod_1() {

		String smiles = "c1=cc=cc=c1";
		Image hexane = MoleculeToImageConverter.getInstance().smilesToImage(smiles);
		try {
			ImageIO.write(//
			(RenderedImage)hexane, "PNG",//
					new File(//
					TestPathHelper.getAbsolutePath(TestPathHelper.TEST_DIRECTORY_EXPORT_PNG) + "TEST.png"));
		} catch(IOException e) {
			logger.warn("Some IO Error occured while parsing the molecule " + hexane + "\n And this is because the following error occured:\n" + e);
		}
	}
}
