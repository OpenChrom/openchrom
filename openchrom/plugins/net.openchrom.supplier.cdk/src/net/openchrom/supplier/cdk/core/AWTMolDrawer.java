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

/**
 * A Simple utility class for converting smile Strings to PNG files,
 * where the resultant image file is stored in the specified directory.
 * 
 * @author administrator_marwin
 * 
 */
public class AWTMolDrawer {

	/*
	 * Standard width and height of the molecule image
	 */
	private int width = 200;
	private int height = 200;

	/**
	 * Generate Molecule out of smilesString and render it.
	 * 
	 * @param smilesString
	 */
	public AWTMolDrawer(String smilesString, String imageFile) {

		// OpenChromSettings.getSettingsDirectory().getAbsolutePath() + File.separator + "net.openchrom.supplier.cdk" + File.separator + "aTestMolecule.png";
		Image image = MoleculeToImageConverter.getInstance().smilesToImage(smilesString);
		try {
			ImageIO.write((RenderedImage)image, "PNG", new File(imageFile));
		} catch(IOException e) {
			System.err.println("Some IO Error occured!");
		}
	}

	public void setWidth(int width) {

		this.width = width;
	}

	public void setHeight(int height) {

		this.height = height;
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}
}
