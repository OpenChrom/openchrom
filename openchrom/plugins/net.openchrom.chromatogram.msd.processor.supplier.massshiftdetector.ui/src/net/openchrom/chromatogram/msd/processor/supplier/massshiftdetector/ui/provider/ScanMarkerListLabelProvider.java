/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider;

import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ScanMarker_v1000;

public class ScanMarkerListLabelProvider extends AbstractChemClipseLabelProvider {

	public ScanMarkerListLabelProvider() {
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SELECTED_SCAN, IApplicationImage.SIZE_16x16);
		} else if(columnIndex == 1) {
			if(element instanceof ScanMarker_v1000) {
				IScanMarker scanMarker = (IScanMarker)element;
				if(scanMarker.isValidated()) {
					return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SELECTED, IApplicationImage.SIZE_16x16);
				} else {
					return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16);
				}
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof ScanMarker_v1000) {
			IScanMarker scanMarker = (IScanMarker)element;
			switch(columnIndex) {
				case 0:
					text = Integer.toString(scanMarker.getScanNumber());
					break;
				case 1: // Validated
					text = "";
					break;
				default:
					break;
			}
		}
		return text;
	}

	public Image getImage(Object element) {

		return ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ION, IApplicationImage.SIZE_16x16);
	}
}
