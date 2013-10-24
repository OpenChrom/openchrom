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
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

public class MoleculeToImageConverter {

	// want to draw one molecule:
	private IMolecule _mol;
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
	//
	private static MoleculeToImageConverter _singleton;

	//
	public static MoleculeToImageConverter getInstance() {

		if(_singleton == null)
			_singleton = new MoleculeToImageConverter();
		return _singleton;
	}

	//
	public Image smilesToImage(String smilesString) {

		// generates smile
		_mol = ChromSmilesParser.getInstance().generate(smilesString);
		// the draw area and the image should be the same size
		Rectangle drawArea = new Rectangle(WIDTH, HEIGHT);
		_image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		//
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		//
		sdg.setMolecule(_mol);
		try {
			sdg.generateCoordinates();
		} catch(Exception ex) {
			// its not possible to parse the molecule => well-formed smiles?
			System.err.println("CANNOT INSTATIATE COORDINATES");
			return null;
		}
		_mol = sdg.getMolecule();
		// generators make the image elements
		List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		//
		// the renderer needs to have a toolkit-specific font manager
		IRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
		// the call to 'setup' only needs to be done on the first paint
		renderer.setup(_mol, drawArea);
		// paint the background
		Graphics2D g2 = (Graphics2D)_image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		// the paint method also needs a toolkit-specific renderer
		renderer.paint(_mol, new AWTDrawVisitor(g2));
		//
		return _image;
	}

	//
	// client
	public static void main(String[] args) {

		String smiles = "c1=cc=cc=c1";
		Image hexane = MoleculeToImageConverter.getInstance().smilesToImage(smiles);
		try {
			ImageIO.write((RenderedImage)hexane, "PNG", new File("/home/administrator_marwin/Dokumente/test/hexane.png"));
		} catch(IOException e) {
			System.err.println("Some IO Error occured!");
		}
	}
}
