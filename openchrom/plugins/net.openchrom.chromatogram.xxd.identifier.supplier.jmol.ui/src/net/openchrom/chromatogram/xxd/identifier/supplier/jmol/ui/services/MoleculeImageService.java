/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.services;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.swt.ui.services.IMoleculeImageService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.jmol.api.JmolViewer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.MDLV2000Writer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.Activator;
import net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.preferences.PreferencePage;
import net.openchrom.xxd.identifier.supplier.cdk.converter.CDKSmilesToMoleculeConverter;
import net.openchrom.xxd.identifier.supplier.cdk.converter.IStructureConverter;
import net.openchrom.xxd.identifier.supplier.cdk.converter.OPSINIupacToMoleculeConverter;

@Component(service = {IMoleculeImageService.class}, configurationPolicy = ConfigurationPolicy.OPTIONAL)
public class MoleculeImageService implements IMoleculeImageService {

	private static final Logger logger = Logger.getLogger(MoleculeImageService.class);

	@Activate
	public void start() {

		logger.info("Service started: " + getName());
	}

	@Override
	public String getName() {

		return "JMol";
	}

	@Override
	public String getVersion() {

		Bundle bundle = Activator.getDefault().getBundle();
		Version version = bundle.getVersion();
		return version.getMajor() + "." + version.getMinor() + "." + version.getMicro();
	}

	@Override
	public String getDescription() {

		return "This service tries to create an image by using JMol.";
	}

	@Override
	public Image create(Display display, ILibraryInformation libraryInformation, int width, int height) {

		Image image = null;
		if(display != null && libraryInformation != null) {
			image = createImage(display, libraryInformation, width, height);
		}
		return image;
	}

	@Override
	public Class<? extends IWorkbenchPreferencePage> getPreferencePage() {

		return PreferencePage.class;
	}

	private Image createImage(Display display, ILibraryInformation libraryInformation, int width, int height) {

		Image image = null;
		/*
		 * MDL MOL
		 */
		String moleculeStructure = libraryInformation.getMoleculeStructure();
		if(!moleculeStructure.isEmpty()) {
			image = convertMoleculeToImage(display, moleculeStructure, width, height);
		}
		/*
		 * SMILES (Jmol would resolve these via a web service, so let CDK do it offline)
		 */
		if(image == null) {
			String smiles = libraryInformation.getSmiles().trim();
			if(!smiles.isEmpty()) {
				image = convertStructureToImage(display, new CDKSmilesToMoleculeConverter(), smiles, width, height);
			}
		}
		/*
		 * IUPAC
		 */
		if(image == null) {
			String name = libraryInformation.getName().trim();
			if(!name.isEmpty()) {
				image = convertStructureToImage(display, new OPSINIupacToMoleculeConverter(), name, width, height);
			}
		}
		return image;
	}

	private Image convertStructureToImage(Display display, IStructureConverter structureConverter, String input, int width, int height) {

		IAtomContainer molecule = structureConverter.generate(input);
		if(molecule == null || molecule.isEmpty()) {
			return null;
		}
		String mdl = toFlatMolfile(molecule);
		if(mdl == null) {
			return null;
		}
		return convertMoleculeToImage(display, mdl, width, height);
	}

	/**
	 * Lays out the molecule in 2D. Jmol adds the hydrogens and minimizes it to 3D (defaultLoadFilter 2D).
	 */
	private String toFlatMolfile(IAtomContainer molecule) {

		StringWriter stringWriter = new StringWriter();
		try (MDLV2000Writer mdlWriter = new MDLV2000Writer(stringWriter)) {
			new StructureDiagramGenerator().generateCoordinates(molecule);
			mdlWriter.write(molecule);
		} catch(CDKException | IOException e) {
			logger.warn(e);
			return null;
		}
		return stringWriter.toString();
	}

	private Image convertMoleculeToImage(Display display, String mdl, int width, int height) {

		Image image = null;
		JmolViewer jmolViewer = createViewer(width, height);
		jmolViewer.openStringInline(mdl);
		jmolViewer.evalString("color atoms cpk; zoom 25");
		String[] errors = new String[1];
		byte[] imageAsBytes = jmolViewer.getImageAsBytes("PNG", width, height, 100, errors);
		if(errors[0] != null) {
			logger.error(errors[0]);
		}
		try (ByteArrayInputStream stream = new ByteArrayInputStream(imageAsBytes)) {
			ImageLoader loader = new ImageLoader();
			ImageData[] data = loader.load(stream);
			if(data.length > 0) {
				return new Image(display, data[0]);
			}
		} catch(IOException e) {
			logger.error(errors[0]);
		}
		return image;
	}

	private JmolViewer createViewer(int width, int height) {

		JFrame frame = new JFrame();
		JmolPanel jmolPanel = new JmolPanel();
		jmolPanel.setPreferredSize(new Dimension(width, height));
		frame.add(jmolPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(false);
		JmolViewer jmolViewer = jmolPanel.getViewer();
		jmolViewer.setColorBackground("white");
		jmolViewer.setScreenDimension(width, height);
		jmolViewer.setStringProperty("defaultLoadFilter", "2D");
		return jmolViewer;
	}

	@Override
	public boolean isOnline() {

		return false;
	}
}
