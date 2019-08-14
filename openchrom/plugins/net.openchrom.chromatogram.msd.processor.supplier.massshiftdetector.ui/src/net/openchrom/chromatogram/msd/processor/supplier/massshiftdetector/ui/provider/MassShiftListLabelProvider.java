/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IMassShift;

public class MassShiftListLabelProvider extends AbstractChemClipseLabelProvider {

	private DecimalFormat decimalFormat;

	public MassShiftListLabelProvider() {
		decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		String text = "";
		if(element instanceof IMassShift) {
			IMassShift massShift = (IMassShift)element;
			switch(columnIndex) {
				case 0:
					text = Double.toString(massShift.getMz());
					break;
				case 1:
					text = decimalFormat.format(massShift.getRetentionTimeReference() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
					break;
				case 2:
					text = decimalFormat.format(massShift.getRetentionTimeIsotope() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
					break;
				case 3:
					text = Integer.toString(massShift.getMassShiftLevel());
					break;
				case 4:
					text = Double.toString(massShift.getCertainty());
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
