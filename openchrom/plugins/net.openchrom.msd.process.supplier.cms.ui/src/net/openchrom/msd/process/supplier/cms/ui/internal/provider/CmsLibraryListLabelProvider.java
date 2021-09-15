/*******************************************************************************
 * Copyright (c) 2017, 2021 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.swt.graphics.Image;

public class CmsLibraryListLabelProvider extends AbstractChemClipseLabelProvider {

	private DecimalFormat decimalFormatMZ = ValueFormat.getDecimalFormatEnglish("0.0000");
	private DecimalFormat decimalFormatMW = ValueFormat.getDecimalFormatEnglish("0.0000");
	private DecimalFormat decimalFormatSignal = ValueFormat.getDecimalFormatEnglish("0.0000E00");

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		if(element instanceof IRegularLibraryMassSpectrum) {
			/*
			 * Library Entry
			 */
			IRegularLibraryMassSpectrum libraryMassSpectrum = (IRegularLibraryMassSpectrum)element;
			ILibraryInformation libraryInformation = libraryMassSpectrum.getLibraryInformation();
			return getText(libraryMassSpectrum, libraryInformation, columnIndex);
		} else {
			return "n.a.";
		}
	}

	@Override
	public Image getImage(Object element) {

		Image image = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImage.SIZE_16x16);
		return image;
	}

	private String getText(IScanMSD massSpectrum, ILibraryInformation libraryInformation, int columnIndex) {

		String text = "";
		switch(columnIndex) {
			case 0: // Name
				if(libraryInformation != null) {
					text = libraryInformation.getName();
				}
				break;
			case 1: // CAS Number
				if(libraryInformation != null) {
					text = libraryInformation.getCasNumber();
				}
				break;
			case 2: // Molecular Weight
				if(libraryInformation != null) {
					text = decimalFormatMW.format(libraryInformation.getMolWeight());
				}
				break;
			case 3: // Base Peak
				text = decimalFormatMZ.format(massSpectrum.getBasePeak());
				break;
			case 4: // Base Peak Abundance
				text = decimalFormatSignal.format(massSpectrum.getBasePeakAbundance());
				break;
			case 5: // Number of Ions
				text = Integer.toString(massSpectrum.getNumberOfIons());
				break;
			case 6: // Formula
				if(libraryInformation != null) {
					text = libraryInformation.getFormula();
				}
				break;
			default:
				text = "n.v.";
		}
		return text;
	}
}
