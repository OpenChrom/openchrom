/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.ms.converter.supplier.cdf.io.support;

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
	public static final String DIMENSION_RANGE = "range";
	public static final String DIMENSION_POINT_NUMBER = "point_number";
	public static final String DIMENSION_ERROR_NUMBER = "error_number";
	public static final String DIMENSION_SCAN_NUMBER = "scan_number";
	public static final String DIMENSION_INSTRUMENT_NUMBER = "instrument_number";
	public static final String VARIABLE_A_D_SAMPLING_RATE = "a_d_sampling_rate";
	public static final String VARIABLE_A_D_COADDITION_FACTOR = "a_d_coaddition_factor";
	public static final String VARIABLE_SCAN_ACQUISITION_TIME = "scan_acquisition_time";
	public static final String VARIABLE_SCAN_DURATION = "scan_duration";
	public static final String VARIABLE_INTER_SCAN_TIME = "inter_scan_time";
	public static final String VARIABLE_RESOLUTION = "resolution";
	public static final String VARIABLE_ACTUAL_SCAN_NUMBER = "actual_scan_number";
	public static final String VARIABLE_TOTAL_INTENSITY = "total_intensity";
	public static final String VARIABLE_MASS_RANGE_MIN = "mass_range_min";
	public static final String VARIABLE_MASS_RANGE_MAX = "mass_range_max";
	public static final String VARIABLE_TIME_RANGE_MIN = "time_range_min";
	public static final String VARIABLE_TIME_RANGE_MAX = "time_range_max";
	public static final String VARIABLE_SCAN_INDEX = "scan_index";
	public static final String VARIABLE_POINT_COUNT = "point_count";
	public static final String VARIABLE_FLAG_COUNT = "flag_count";
	public static final String VARIABLE_MASS_VALUES = "mass_values";
	public static final String VARIABLE_TIME_VALUES = "time_values";
	public static final String VARIABLE_INTENSITY_VALUES = "intensity_values";
	public static final String VARIABLE_INSTRUMENT_NAME = "instrument_name";
	public static final String VARIABLE_INSTRUMENT_ID = "instrument_id";
	public static final String VARIABLE_INSTRUMENT_MFR = "instrument_mfr";
	public static final String VARIABLE_INSTRUMENT_MODEL = "instrument_model";
	public static final String VARIABLE_INSTRUMENT_SERIAL_NO = "instrument_serial_no";
	public static final String VARIABLE_INSTRUMENT_SW_VERSION = "instrument_sw_version";
	public static final String VARIABLE_INSTRUMENT_FW_VERSION = "instrument_fw_version";
	public static final String VARIABLE_INSTRUMENT_OS_VERSION = "instrument_os_version";
	public static final String VARIABLE_INSTRUMENT_APP_VERSION = "instrument_app_version";
	public static final String VARIABLE_INSTRUMENT_COMMENTS = "instrument_comments";
	public static final String VARIABLE_ERROR_LOG = "error_log";
	public static final String ATTRIBUTE_DATASET_COMPLETENESS = "dataset_completeness";
	public static final String ATTRIBUTE_MS_TEMPLATE_REVISION = "ms_template_revision";
	public static final String ATTRIBUTE_NETCDF_REVISION = "netcdf_revision";
	public static final String ATTRIBUTE_LANGUAGES = "languages";
	public static final String ATTRIBUTE_DATASET_ORIGIN = "dataset_origin";
	public static final String ATTRIBUTE_NETCDF_FILE_DATE_TIME_STAMP = "netcdf_file_date_time_stamp";
	public static final String ATTRIBUTE_EXPERIMENT_TITLE = "experiment_title";
	public static final String ATTRIBUTE_EXPERIMENT_DATE_TIME_STAMP = "experiment_date_time_stamp";
	public static final String ATTRIBUTE_OPERATOR_NAME = "operator_name";
	public static final String ATTRIBUTE_EXTERNAL_FILE_REF_0 = "external_file_ref_0";
	public static final String ATTRIBUTE_EXPERIMENT_TYPE = "experiment_type";
	public static final String ATTRIBUTE_NUMBER_OF_TIMES_PROCESSED = "number_of_times_processed";
	public static final String ATTRIBUTE_NUMBER_OF_TIMES_CALIBRATED = "number_of_times_calibrated";
	public static final String ATTRIBUTE_SAMPLE_STATE = "sample_state";
	public static final String ATTRIBUTE_TEST_SEPARATION_TYPE = "test_separation_type";
	public static final String ATTRIBUTE_TEST_MS_INLET = "test_ms_inlet";
	public static final String ATTRIBUTE_TEST_IONIZATION_MODE = "test_ionization_mode";
	public static final String ATTRIBUTE_TEST_IONIZATION_POLARITY = "test_ionization_polarity";
	public static final String ATTRIBUTE_TEST_DETECTOR_TYPE = "test_detector_type";
	public static final String ATTRIBUTE_TEST_RESOLUTION_TYPE = "test_resolution_type";
	public static final String ATTRIBUTE_TEST_SCAN_FUNCTION = "test_scan_function";
	public static final String ATTRIBUTE_TEST_SCAN_DIRECTION = "test_scan_direction";
	public static final String ATTRIBUTE_TEST_SCAN_LAW = "test_scan_law";
	public static final String ATTRIBUTE_RAW_DATA_MASS_FORMAT = "raw_data_mass_format";
	public static final String ATTRIBUTE_RAW_DATA_TIME_FORMAT = "raw_data_time_format";
	public static final String ATTRIBUTE_RAW_DATA_INTENSITY_FORMAT = "raw_data_intensity_format";
}
