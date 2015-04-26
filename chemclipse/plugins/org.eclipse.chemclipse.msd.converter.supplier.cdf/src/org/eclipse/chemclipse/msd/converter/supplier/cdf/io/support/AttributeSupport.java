/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.converter.supplier.cdf.io.support;

import org.eclipse.chemclipse.msd.converter.supplier.cdf.model.VendorChromatogram;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;

import ucar.nc2.NetcdfFileWriteable;

@SuppressWarnings("deprecation")
public class AttributeSupport {

	public static void setAttributes(NetcdfFileWriteable cdfChromatogram, IChromatogramMSD chromatogram) {

		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_COMPLETENESS, "C1+C2");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_MS_TEMPLATE_REVISION, "1.0.1");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_NETCDF_REVISION, "2.3.2");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_LANGUAGES, "English");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_ORIGIN, "Palo Alto, CA");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_NETCDF_FILE_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate()));
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_EXPERIMENT_TITLE, chromatogram.getMiscInfo());
		if(chromatogram instanceof VendorChromatogram) {
			VendorChromatogram chrom = (VendorChromatogram)chromatogram;
			cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_EXPERIMENT_DATE_TIME_STAMP, DateSupport.getDate(chrom.getDateOfExperiment()));
		} else {
			cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_EXPERIMENT_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate()));
		}
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME, chromatogram.getOperator());
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_EXTERNAL_FILE_REF_0, "DB5PWF30");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_EXPERIMENT_TYPE, "Centroided Mass Spectrum");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_NUMBER_OF_TIMES_PROCESSED, 1);
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_NUMBER_OF_TIMES_CALIBRATED, 0);
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_SAMPLE_STATE, "Other State");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_SEPARATION_TYPE, "No Chromatography");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_MS_INLET, "Capillary Direct");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_IONIZATION_MODE, "Electron Impact");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_IONIZATION_POLARITY, "Positive Polarity");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_DETECTOR_TYPE, "Electron Multiplier");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_RESOLUTION_TYPE, "Constant Resolution");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_SCAN_FUNCTION, "Mass Scan");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_SCAN_DIRECTION, "Up");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_TEST_SCAN_LAW, "Linear");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_RAW_DATA_MASS_FORMAT, "Float");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_RAW_DATA_TIME_FORMAT, "Short");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_RAW_DATA_INTENSITY_FORMAT, "Float");
	}
}
