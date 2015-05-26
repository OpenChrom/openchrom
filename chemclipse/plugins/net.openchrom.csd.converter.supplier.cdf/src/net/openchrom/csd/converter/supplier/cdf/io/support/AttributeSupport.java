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
package net.openchrom.csd.converter.supplier.cdf.io.support;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;

import ucar.nc2.NetcdfFileWriteable;

@SuppressWarnings("deprecation")
public class AttributeSupport {

	public static void setAttributes(NetcdfFileWriteable cdfChromatogram, IChromatogramCSD chromatogram) {

		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_COMPLETENESS, "C1+C2");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_AIA_TEMPLATE_REVISION, "1.0.1");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_NETCDF_REVISION, "2.3.2");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_LANGUAGES, "English");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_ORIGIN, "Palo Alto, CA");
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate()));
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_INJECTION_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate()));
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME, chromatogram.getOperator());
		cdfChromatogram.addGlobalAttribute(CDFConstants.ATTRIBUTE_RETENTION_UNIT, "Seconds");
	}
}
