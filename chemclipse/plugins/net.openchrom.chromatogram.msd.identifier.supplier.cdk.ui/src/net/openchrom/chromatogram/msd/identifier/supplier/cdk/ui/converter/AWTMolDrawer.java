/*******************************************************************************
 * Copyright (c) 2013, 2015 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.converter;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.chemclipse.logging.core.Logger;

/**
 * A Simple utility class for converting smile Strings to PNG files,
 * where the resultant image file is stored in the specified directory.
 * Though it is currently not being used because of Input/Output overhead!
 * => Still it looks like an interesting possibility to be able to export
 * molecules as they are represented in the CDK to the PNG file format!
 * 
 * @author administrator_marwin
 * 
 */
public class AWTMolDrawer {

	/**
	 * Generate Molecule out of smilesString and render it.
	 * 
	 * @param smilesString
	 */
	private static final Logger logger = Logger.getLogger(AWTMolDrawer.class);

	public AWTMolDrawer(String smilesString, String imageFile, int width, int height) {
		Image image = ImageConverter.getInstance().smilesToImage(smilesString, width, height);
		try {
			ImageIO.write((RenderedImage)image, "PNG", new File(imageFile));
		} catch(IOException e) {
			logger.warn("Some IO Error occured while instantiating AWTMolDrawer object. Here is more information:\n" + e);
		}
	}
}
