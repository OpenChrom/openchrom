/*******************************************************************************
 * Copyright (c) 2013 administrator_marwin.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * administrator_marwin - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.ReactionRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

/**
 * A Simple utility class for converting smile Strings to PNG files,
 * where the resultant image file is stored in the specified directory.
 * 
 * @author administrator_marwin
 * 
 */
public class AWTMolDrawer {

	// want to draw one molecule:
	IMolecule _mol;
	// Standard width and height of the molecule image
	int WIDTH = 200;
	int HEIGHT = 200;
	//
	private Image _image;
	//
	private String _filePath = "/home/administrator_marwin/Dokumente/test/";
	//
	private String _nameOfImage = "aTestMolecule.png";

	// Generate Molecule out of smilesString and
	// render
	public AWTMolDrawer(String smilesString) {

		_image = MoleculeToImageConverter.getInstance().smilesToImage(smilesString);
		try {
			ImageIO.write((RenderedImage)_image, "PNG", new File(_filePath + _nameOfImage));
		} catch(IOException e) {
			System.err.println("Some IO Error occured!");
		}
	}

	// --------------------------------------------------------------------------- //
	// Getters and Setters
	public void setWidth(int w) {

		WIDTH = w;
	}

	public void setHeight(int h) {

		HEIGHT = h;
	}

	public void setNameOfImageFile(String name) {

		_nameOfImage = name;
	}

	public void setFilePath(String path) {

		_filePath = path;
	}

	//
	public int getWidth() {

		return WIDTH;
	}

	public int getHeight() {

		return HEIGHT;
	}

	public String getNameOfImageFile() {

		return _nameOfImage;
	}

	public String getFilePath() {

		return _filePath;
	}

	// End of Getters and Setters
	// --------------------------------------------------------------------------- //
	//
	// client
	public static void main(String[] args) {

		AWTMolDrawer dr = new AWTMolDrawer("c1(nnc)cccc1c");
	}
}
