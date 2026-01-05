/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.cdf.io.support;

import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;

import ucar.nc2.Attribute;
import ucar.nc2.write.NetcdfFormatWriter.Builder;

public class AttributeSupport {

	private AttributeSupport() {

	}

	public static void setAttributes(Builder builder, IChromatogramWSD chromatogram) {

		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_DATASET_COMPLETENESS, "C1+C2"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_AIA_TEMPLATE_REVISION, "1.0.1"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_NETCDF_REVISION, "2.3.2"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_LANGUAGES, "English"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_DATASET_ORIGIN, "Palo Alto, CA"));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_DATASET_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate())));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_INJECTION_DATE_TIME_STAMP, DateSupport.getDate(chromatogram.getDate())));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME, chromatogram.getOperator()));
		builder.addAttribute(new Attribute(CDFConstants.ATTRIBUTE_RETENTION_UNIT, "Seconds"));
	}
}
