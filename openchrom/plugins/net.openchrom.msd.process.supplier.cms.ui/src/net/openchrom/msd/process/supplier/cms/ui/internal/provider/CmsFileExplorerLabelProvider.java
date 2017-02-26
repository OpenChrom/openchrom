/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.io.File;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.ux.extension.msd.ui.internal.support.MassSpectrumIdentifier;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class CmsFileExplorerLabelProvider extends LabelProvider implements ILabelProvider {

	@Override
	public Image getImage(Object element) {

		ImageDescriptor descriptor = null;
		if(element instanceof File) {
			File file = (File)element;
			/*
			 * Root, directory or file.
			 */
			if(file.getName().equals("")) {
				descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_DRIVE, IApplicationImage.SIZE_16x16);
			} else {
				if(file.isDirectory()) {
					/*
					 * Check if the directory could be a registered
					 * chromatogram.
					 */
					if(MassSpectrumIdentifier.isMassSpectrumDirectory(file)) {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImage.SIZE_16x16);
					} else {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FOLDER_OPENED, IApplicationImage.SIZE_16x16);
					}
				} else {
					/*
					 * Check if the file could be a registered chromatogram.
					 */
					if(MassSpectrumIdentifier.isMassSpectrum(file)) {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImage.SIZE_16x16);
					} else {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FILE, IApplicationImage.SIZE_16x16);
					}
				}
			}
			Image image = descriptor.createImage();
			return image;
		}
		return null;
	}

	@Override
	public String getText(Object element) {

		if(element instanceof File) {
			File file = (File)element;
			String name;
			if(file.getName().equals("")) {
				name = file.getAbsolutePath();
			} else {
				name = file.getName();
			}
			return name;
		}
		return "";
	}
}
