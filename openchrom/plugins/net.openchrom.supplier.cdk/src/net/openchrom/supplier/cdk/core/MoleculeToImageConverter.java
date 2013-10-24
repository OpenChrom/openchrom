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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

public class MoleculeToImageConverter {

	// Hold track of one molecule:
	private IMolecule molecule;
	// Standard width and height of the molecule image
	private int width = 200;
	private int height = 200;
	//
	/**
	 * Generate Molecule out of smilesString and
	 * render.
	 * (@params)
	 **/
	private static MoleculeToImageConverter singleton;

	//
	public static MoleculeToImageConverter getInstance() {

		if(singleton == null) {
			singleton = new MoleculeToImageConverter();
		}
		return singleton;
	}

	//
	public Image smilesToImage(String smilesString) {

		Image image;
		// Generate smiles
		molecule = ChromSmilesParser.getInstance().generate(smilesString);
		// The draw area and the image should be the same size
		Rectangle drawArea = new Rectangle(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		//
		sdg.setMolecule(molecule);
		try {
			sdg.generateCoordinates();
		} catch(Exception e) {
			// Its not possible to parse the molecule => well-formed smiles?
			System.err.println("CANNOT INSTANTIATE COORDINATES: " + e);
			return null;
		}
		molecule = sdg.getMolecule();
		// Generators make the image elements
		List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		//
		// The renderer needs to have a toolkit-specific font manager
		IRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
		//
		renderer.setup(molecule, drawArea);
		// Paint the background
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		// The paint method also needs a toolkit-specific renderer
		renderer.paint(molecule, new AWTDrawVisitor(g2));
		//
		return image;
	}

	//
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
