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
 * Dr. Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.converter;

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

import net.openchrom.logging.core.Logger;

public class ImageConverter {

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;
	private static final Logger logger = Logger.getLogger(ImageConverter.class);
	/**
	 * Generate Molecule out of smilesString and
	 * render.
	 **/
	private static ImageConverter singleton;

	public static ImageConverter getInstance() {

		if(singleton == null) {
			singleton = new ImageConverter();
		}
		return singleton;
	}

	//
	public Image smilesToImage(String smilesString, int width, int height) {

		IMolecule molecule = new CDKSmilesToMoleculeConverter().generate(smilesString);
		return createImage(molecule, width, height);
	}

	public Image moleculeToImage(IMolecule molecule, int width, int height) {

		return createImage(molecule, width, height);
	}

	/**
	 * Renders the image. May return null.
	 * 
	 * @param molecule
	 * @param width
	 * @param height
	 * @return Image
	 */
	private Image createImage(IMolecule molecule, int width, int height) {

		Image image = null;
		if(molecule != null) {
			/*
			 * Only create the image if the molecule is not null.
			 */
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Rectangle drawArea = new Rectangle(width, height);
			//
			StructureDiagramGenerator structureDiagramGenerator = new StructureDiagramGenerator();
			structureDiagramGenerator.setMolecule(molecule);
			try {
				structureDiagramGenerator.generateCoordinates();
			} catch(Exception e) {
				/*
				 * It's not possible to parse the molecule => well-formed smiles?
				 */
				logger.warn("CANNOT INSTANTIATE COORDINATES: " + e);
				return null;
			}
			/*
			 * Use another name for the diagram molecule. Otherwise it's a bit confusing.
			 */
			IMolecule diagramMolecule = structureDiagramGenerator.getMolecule();
			// Generators make the image elements
			List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
			generators.add(new BasicSceneGenerator());
			generators.add(new BasicBondGenerator());
			generators.add(new BasicAtomGenerator());
			//
			IRenderer<IAtomContainer> renderer = new AtomContainerRenderer(generators, new AWTFontManager());
			renderer.setup(diagramMolecule, drawArea);
			// Paint the background
			Graphics2D g2 = (Graphics2D)image.getGraphics();
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, width, height);
			// The paint method also needs a toolkit-specific renderer
			renderer.paint(diagramMolecule, new AWTDrawVisitor(g2));
		}
		//
		return image;
	}
}
