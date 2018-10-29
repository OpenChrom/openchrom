/*******************************************************************************
 * Copyright (c) 2017 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.internal.provider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.model.comparator.TargetExtendedComparator;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;
import org.eclipse.chemclipse.swt.ui.preferences.PreferenceSupplier;
import org.eclipse.swt.graphics.Image;

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;

public class CvlmsListLabelProvider extends AbstractChemClipseLabelProvider {

	private TargetExtendedComparator targetExtendedComparator;

	public CvlmsListLabelProvider() {
		targetExtendedComparator = new TargetExtendedComparator(SortOrder.DESC);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {

		if(element instanceof ICalibratedVendorLibraryMassSpectrum) {
			return getText((ICalibratedVendorLibraryMassSpectrum)element, columnIndex);
		} else {
			return "n.a.";
		}
	}

	private String getText(ICalibratedVendorLibraryMassSpectrum massSpectrum, int columnIndex) {

		DecimalFormat decimalFormat = getDecimalFormat();
		String text = "";
		ILibraryInformation libInfo;
		libInfo = massSpectrum.getLibraryInformation();
		switch(columnIndex) {
			case 0: // Name
				if(massSpectrum instanceof CalibratedVendorMassSpectrum) {
					text = "SCAN: " + ((CalibratedVendorMassSpectrum)massSpectrum).getScanName();
				} else {
					if(libInfo != null) {
						text = "LIB: " + libInfo.getName();
					}
				}
				break;
			case 1: // RT
				if(massSpectrum.getRetentionTime() == 0) {
					text = "0";
				} else {
					text = decimalFormat.format(massSpectrum.getRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
				}
				break;
			case 2: // RRT
				if(massSpectrum.getRelativeRetentionTime() == 0) {
					text = "0";
				} else {
					text = decimalFormat.format(massSpectrum.getRelativeRetentionTime() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
				}
				break;
			case 3: // RI
				int retentionIndexNoPrecision = (int)massSpectrum.getRetentionIndex();
				if(retentionIndexNoPrecision == massSpectrum.getRetentionIndex()) {
					text = Integer.toString(retentionIndexNoPrecision);
				} else {
					if(PreferenceSupplier.showRetentionIndexWithoutDecimals()) {
						text = Integer.toString(retentionIndexNoPrecision);
					} else {
						text = decimalFormat.format(massSpectrum.getRetentionIndex());
					}
				}
				break;
			case 4: // Base Peak
				int basePeakNoPrecision = (int)massSpectrum.getBasePeak();
				if(basePeakNoPrecision == massSpectrum.getBasePeak()) {
					text = Integer.toString(basePeakNoPrecision);
				} else {
					text = decimalFormat.format(massSpectrum.getBasePeak());
				}
				break;
			case 5: // Base Peak Abundance
				int basePeakAbundanceNoPrecision = (int)massSpectrum.getBasePeakAbundance();
				if(basePeakAbundanceNoPrecision == massSpectrum.getBasePeakAbundance()) {
					text = Integer.toString(basePeakAbundanceNoPrecision);
				} else {
					// text = decimalFormat.format(massSpectrum.getBasePeakAbundance());
					text = String.format("% .4g", massSpectrum.getBasePeakAbundance());
				}
				break;
			case 6: // Number of Ions
				text = Integer.toString(massSpectrum.getNumberOfIons());
				break;
			case 7: // CAS
				if(libInfo != null) {
					text = libInfo.getCasNumber();
				}
				break;
			case 8: // MW
				if(massSpectrum instanceof CalibratedVendorMassSpectrum) {
					text = "";
				} else {
					if(libInfo != null) {
						int molWeightNoPrecision = (int)libInfo.getMolWeight();
						if(0 < molWeightNoPrecision) {
							if(molWeightNoPrecision == libInfo.getMolWeight()) {
								text = Integer.toString(molWeightNoPrecision);
							} else {
								text = decimalFormat.format(libInfo.getMolWeight());
							}
						} else
							text = "";
					}
				}
				break;
			case 9: // Formula
				if(libInfo != null) {
					text = libInfo.getFormula();
				}
				break;
			case 10:
				if(libInfo != null) {
					text = libInfo.getSmiles();
				}
				break;
			case 11:
				if(libInfo != null) {
					text = libInfo.getInChI();
				}
				break;
			case 12: // Reference Identifier
				if(libInfo != null) {
					text = libInfo.getReferenceIdentifier();
				}
				break;
			case 13:
				if(libInfo != null) {
					text = libInfo.getComments();
				}
				break;
			default:
				text = "n.v.";
		}
		return text;
	} // whw

	@Override
	public Image getColumnImage(Object element, int columnIndex) {

		if(columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public Image getImage(Object element) {

		Image image = ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_MASS_SPECTRUM, IApplicationImage.SIZE_16x16);
		return image;
	}

	private ILibraryInformation getLibraryInformation(List<IIdentificationTarget> targets) {

		ILibraryInformation libraryInformation = null;
		targets = new ArrayList<>(targets);
		Collections.sort(targets, targetExtendedComparator);
		if(targets.size() >= 1) {
			libraryInformation = targets.get(0).getLibraryInformation();
		}
		return libraryInformation;
	}
}
