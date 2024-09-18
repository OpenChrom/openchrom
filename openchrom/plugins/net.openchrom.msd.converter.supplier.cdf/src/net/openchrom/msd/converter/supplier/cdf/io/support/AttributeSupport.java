/*******************************************************************************
 * Copyright (c) 2013, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;

import net.openchrom.msd.converter.supplier.cdf.model.VendorChromatogram;

import ucar.nc2.Attribute;
import ucar.nc2.write.NetcdfFormatWriter.Builder;

public class AttributeSupport {

	private AttributeSupport() {

	}

	public static void setAttributes(Builder builder, IChromatogramMSD chromatogram) {

		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_DATASET_COMPLETENESS, "C1+C2"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_MS_TEMPLATE_REVISION, "1.0.1"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_NETCDF_REVISION, "2.3.2"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_LANGUAGES, "English"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_DATASET_ORIGIN, "Palo Alto, CA"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_NETCDF_FILE_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate())));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_EXPERIMENT_TITLE, chromatogram.getMiscInfo()));
		if(chromatogram instanceof VendorChromatogram chrom) {
			builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_EXPERIMENT_DATE_TIME_STAMP, DateSupport.getDate(chrom.getDateOfExperiment())));
		} else {
			builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_EXPERIMENT_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate())));
		}
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME, chromatogram.getOperator()));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_EXTERNAL_FILE_REF_0, "DB5PWF30"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_EXPERIMENT_TYPE, "Centroided Mass Spectrum"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_NUMBER_OF_TIMES_PROCESSED, 1));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_NUMBER_OF_TIMES_CALIBRATED, 0));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_SAMPLE_STATE, "Other State"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_SEPARATION_TYPE, "No Chromatography"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_MS_INLET, "Capillary Direct"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_IONIZATION_MODE, "Electron Impact"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_IONIZATION_POLARITY, "Positive Polarity"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_DETECTOR_TYPE, "Electron Multiplier"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_RESOLUTION_TYPE, "Constant Resolution"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_SCAN_FUNCTION, "Mass Scan"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_SCAN_DIRECTION, "Up"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_TEST_SCAN_LAW, "Linear"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_RAW_DATA_MASS_FORMAT, "Float"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_RAW_DATA_TIME_FORMAT, "Short"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_RAW_DATA_INTENSITY_FORMAT, "Float"));
	}
}
