/*******************************************************************************
 * Copyright (c) 2014, 2016 Lablicate GmbH.
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

/**
 * This class stores all the dimensions, variables and attributes which are
 * important to read and write a valid cdf file.
 * 
 * @author eselmeister
 */
public class CDFConstants {

	public static final String DIMENSION_2_BYTE_STRING = "_2_byte_string";
	public static final String DIMENSION_4_BYTE_STRING = "_4_byte_string";
	public static final String DIMENSION_8_BYTE_STRING = "_8_byte_string";
	public static final String DIMENSION_16_BYTE_STRING = "_16_byte_string";
	public static final String DIMENSION_32_BYTE_STRING = "_32_byte_string";
	public static final String DIMENSION_64_BYTE_STRING = "_64_byte_string";
	public static final String DIMENSION_128_BYTE_STRING = "_128_byte_string";
	public static final String DIMENSION_255_BYTE_STRING = "_255_byte_string";
	public static final String DIMENSION_ERROR_NUMBER = "error_number";
	public static final String DIMENSION_POINT_NUMBER = "point_number";
	//
	public static final String ATTRIBUTE_DATASET_COMPLETENESS = "dataset_completeness";
	public static final String ATTRIBUTE_AIA_TEMPLATE_REVISION = "aia_template_revision";
	public static final String ATTRIBUTE_NETCDF_REVISION = "netcdf_revision";
	public static final String ATTRIBUTE_LANGUAGES = "languages";
	public static final String ATTRIBUTE_DATASET_ORIGIN = "dataset_origin";
	public static final String ATTRIBUTE_DATASET_DATE_TIME_STAMP = "dataset_date_time_stamp";
	public static final String ATTRIBUTE_RETENTION_UNIT = "retention_unit";
	public static final String ATTRIBUTE_INJECTION_DATE_TIME_STAMP = "injection_date_time_stamp";
	public static final String ATTRIBUTE_OPERATOR_NAME = "operator_name";
	//
	public static final String VARIABLE_DETECTOR_MAXIMUM_VALUE = "detector_maximum_value";
	public static final String VARIABLE_DETECTOR_MINIMUM_VALUE = "detector_minimum_value";
	public static final String VARIABLE_ACTUAL_RUN_TIME_LENGTH = "actual_run_time_length";
	public static final String VARIABLE_ACTUAL_SAMPLING_INTERVAL = "actual_sampling_interval";
	public static final String VARIABLE_ACTUAL_DELAY_TIME = "actual_delay_time";
	public static final String VARIABLE_ORDINATE_VALUES = "ordinate_values";
}
