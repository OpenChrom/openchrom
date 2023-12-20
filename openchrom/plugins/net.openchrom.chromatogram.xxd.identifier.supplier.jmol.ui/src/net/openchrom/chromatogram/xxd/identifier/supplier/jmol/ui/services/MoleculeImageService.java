/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.services;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.IOException;

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
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.Activator;
import net.openchrom.chromatogram.xxd.identifier.supplier.jmol.ui.preferences.PreferencePage;

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
		String moleculeStructure = libraryInformation.getMoleculeStructure();
		if(!moleculeStructure.isEmpty()) {
			image = convertMoleculeToImage(display, moleculeStructure, width, height);
		}
		// TODO https://wiki.jmol.org/index.php/Load_SMILES (depends on a web service)
		return image;
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
		return jmolViewer;
	}

	@Override
	public boolean isOnline() {

		return false;
	}
}
