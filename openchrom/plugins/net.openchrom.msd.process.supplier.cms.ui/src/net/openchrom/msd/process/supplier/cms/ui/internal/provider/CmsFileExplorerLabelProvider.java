/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.io.File;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.ux.extension.msd.ui.support.MassSpectrumIdentifier;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class CmsFileExplorerLabelProvider extends LabelProvider implements ILabelProvider {

	public MassSpectrumIdentifier massSpectrumIdentifier = new MassSpectrumIdentifier();

	@Override
	public Image getImage(Object element) {

		ImageDescriptor descriptor = null;
		if(element instanceof File file) {
			/*
			 * Root, directory or file.
			 */
			if(file.getName().equals("")) {
				descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_DRIVE, IApplicationImageProvider.SIZE_16x16);
			} else {
				if(file.isDirectory()) {
					/*
					 * Check if the directory could be a registered
					 * chromatogram.
					 */
					if(massSpectrumIdentifier.isSupplierFile(file)) {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImageProvider.SIZE_16x16);
					} else {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FOLDER_OPENED, IApplicationImageProvider.SIZE_16x16);
					}
				} else {
					/*
					 * Check if the file could be a registered chromatogram.
					 */
					if(massSpectrumIdentifier.isSupplierFile(file)) {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImageProvider.SIZE_16x16);
					} else {
						descriptor = ApplicationImageFactory.getInstance().getImageDescriptor(IApplicationImage.IMAGE_FILE, IApplicationImageProvider.SIZE_16x16);
					}
				}
			}
			return descriptor.createImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {

		if(element instanceof File file) {
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
