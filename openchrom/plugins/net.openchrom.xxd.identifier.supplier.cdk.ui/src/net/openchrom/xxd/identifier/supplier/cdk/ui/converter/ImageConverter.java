/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Philip Wenig - additional API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.ui.converter;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.PreferencesSupport;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.color.CDK2DAtomColors;
import org.openscience.cdk.renderer.color.IAtomColorer;
import org.openscience.cdk.renderer.color.UniColor;
import org.openscience.cdk.renderer.generators.standard.StandardGenerator;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import net.openchrom.xxd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import net.openchrom.xxd.identifier.supplier.cdk.converter.IStructureConverter;
import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class ImageConverter {

	private static final Logger logger = Logger.getLogger(ImageConverter.class);

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	/**
	 * Generate Molecule out of MDL MOL chemical table files, SMILES strings or InChI using OPSIN and render.
	 **/
	private static ImageConverter singleton;

	public static ImageConverter getInstance() {

		if(singleton == null) {
			singleton = new ImageConverter();
		}
		return singleton;
	}

	public Image smilesToImage(String smilesString, int width, int height) {

		IAtomContainer molecule = new CDKSmilesToMoleculeConverter().generate(smilesString);
		return createImage(molecule, width, height);
	}

	public BufferedImage moleculeToImage(IAtomContainer molecule, int width, int height) {

		return createImage(molecule, width, height);
	}

	public org.eclipse.swt.graphics.Image moleculeToImage(Display display, IStructureConverter structureConverter, String converterInput, Point point) {

		IAtomContainer molecule = structureConverter.generate(converterInput);
		if(molecule != null) {
			BufferedImage image = moleculeToImage(molecule, point.x, point.y);
			if(image != null) {
				return new org.eclipse.swt.graphics.Image(display, AwtToSwtImageBridge.convertToSWT(image));
			}
		}
		return null;
	}

	/**
	 * Renders the image. May return null.
	 * 
	 * @param molecule
	 * @param width
	 * @param height
	 * @return Image
	 */
	private BufferedImage createImage(IAtomContainer molecule, int width, int height) {

		/*
		 * Only create the image if the molecule is not null.
		 */
		if(molecule == null) {
			return null;
		}

		if(PreferenceSupplier.isShowAtomsH()) {
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(molecule);
		}

		DepictionGenerator depictionGenerator = new DepictionGenerator() //
				.withSize(width, height) //
				.withMargin(0.1) //
				.withZoom(2.0) //
				.withBackgroundColor(TRANSPARENT); //

		if(PreferencesSupport.isDarkTheme()) {
			if(PreferenceSupplier.isColorAtoms()) {
				depictionGenerator = depictionGenerator.withParam(StandardGenerator.AtomColor.class, new IAtomColorer() {

					private final CDK2DAtomColors base = new CDK2DAtomColors();

					@Override
					public Color getAtomColor(IAtom atom) {

						if(atom.getAtomicNumber() != null && atom.getAtomicNumber() == IAtom.C) {
							return Color.WHITE;
						}
						return base.getAtomColor(atom);
					}
				});
			} else {
				depictionGenerator = depictionGenerator.withParam(StandardGenerator.AtomColor.class, new UniColor(Color.WHITE));
			}
		} else {
			if(PreferenceSupplier.isColorAtoms()) {
				depictionGenerator = depictionGenerator.withParam(StandardGenerator.AtomColor.class, new CDK2DAtomColors());
			} else {
				depictionGenerator = depictionGenerator.withParam(StandardGenerator.AtomColor.class, new UniColor(Color.BLACK));
			}
		}

		try {
			return depictionGenerator.depict(molecule).toImg();
		} catch(CDKException e) {
			logger.error(e);
		}
		return null;
	}
}