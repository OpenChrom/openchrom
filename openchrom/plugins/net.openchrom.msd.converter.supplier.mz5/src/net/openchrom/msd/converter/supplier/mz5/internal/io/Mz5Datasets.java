/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.internal.io;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import net.openchrom.msd.converter.supplier.mz5.internal.model.CVParam;
import net.openchrom.msd.converter.supplier.mz5.internal.model.CVReference;
import net.openchrom.msd.converter.supplier.mz5.internal.model.Mz5;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;

/**
 * Reads and writes the mz5 datasets with the types used by ProteoWizard.
 * Compound members are looked up by name, so files with other string
 * layouts, e.g. written by earlier OpenChrom versions, can be read too.
 */
public class Mz5Datasets {

	private static final int CV_PARAM_VALUE_LENGTH = 128;

	private Mz5Datasets() {

	}

	public static boolean exists(long file, String name) {

		return H5.H5Lexists(file, name, HDF5Constants.H5P_DEFAULT);
	}

	public static double[] readDoubleArray(long file, String name) {

		long dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
		try {
			double[] data = new double[getNumberOfElements(dataset)];
			if(data.length > 0) {
				H5.H5Dread_double(dataset, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
			return data;
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	public static float[] readFloatArray(long file, String name) {

		long dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
		try {
			float[] data = new float[getNumberOfElements(dataset)];
			if(data.length > 0) {
				H5.H5Dread_float(dataset, HDF5Constants.H5T_NATIVE_FLOAT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
			return data;
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	public static int[] readIntArray(long file, String name) {

		long dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
		try {
			int[] data = new int[getNumberOfElements(dataset)];
			if(data.length > 0) {
				H5.H5Dread_int(dataset, HDF5Constants.H5T_NATIVE_INT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
			return data;
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	public static CVParam[] readCVParams(long file) {

		List<CVParam> cvParams = new ArrayList<>();
		readCompoundArray(file, Mz5.CV_PARAM, (memberIndex, record) -> {
			CVParam cvParam = new CVParam();
			cvParam.setValue(getString(record, memberIndex.get("value")));
			cvParam.setControlledVocabularyReferenceID(getInt(record, memberIndex.get("cvRefID")));
			cvParam.setUnitReferenceID(getInt(record, memberIndex.get("uRefID")));
			cvParams.add(cvParam);
		});
		return cvParams.toArray(new CVParam[0]);
	}

	public static CVReference[] readCVReferences(long file) {

		List<CVReference> cvReferences = new ArrayList<>();
		readCompoundArray(file, Mz5.CV_REFERENCE, (memberIndex, record) -> {
			CVReference cvReference = new CVReference();
			cvReference.setName(getString(record, memberIndex.get("name")));
			cvReference.setPrefix(getString(record, memberIndex.get("prefix")));
			cvReference.setAccession(getInt(record, memberIndex.get("accession")));
			cvReferences.add(cvReference);
		});
		return cvReferences.toArray(new CVReference[0]);
	}

	public static void writeDoubleArray(long file, String name, double[] data) {

		long dataset = createDataset(file, name, HDF5Constants.H5T_IEEE_F64LE, data.length);
		try {
			if(data.length > 0) {
				H5.H5Dwrite_double(dataset, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	public static void writeFloatArray(long file, String name, float[] data) {

		long dataset = createDataset(file, name, HDF5Constants.H5T_IEEE_F32LE, data.length);
		try {
			if(data.length > 0) {
				H5.H5Dwrite_float(dataset, HDF5Constants.H5T_NATIVE_FLOAT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	/**
	 * Indices are unsigned in mz5.
	 */
	public static void writeIntArray(long file, String name, int[] data) {

		long dataset = createDataset(file, name, HDF5Constants.H5T_STD_U32LE, data.length);
		try {
			if(data.length > 0) {
				H5.H5Dwrite_int(dataset, HDF5Constants.H5T_NATIVE_UINT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	public static void writeCVParams(long file, CVParam[] cvParams) {

		long valueType = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
		long type = H5.H5Tcreate(HDF5Constants.H5T_COMPOUND, CV_PARAM_VALUE_LENGTH + 8L);
		try {
			H5.H5Tset_size(valueType, CV_PARAM_VALUE_LENGTH);
			H5.H5Tset_strpad(valueType, HDF5Constants.H5T_STR_NULLTERM);
			H5.H5Tinsert(type, "value", 0, valueType);
			H5.H5Tinsert(type, "cvRefID", CV_PARAM_VALUE_LENGTH, HDF5Constants.H5T_NATIVE_UINT);
			H5.H5Tinsert(type, "uRefID", CV_PARAM_VALUE_LENGTH + 4L, HDF5Constants.H5T_NATIVE_UINT);
			List<?>[] records = new List<?>[cvParams.length];
			for(int i = 0; i < cvParams.length; i++) {
				CVParam cvParam = cvParams[i];
				String value = truncate(cvParam.getValue(), CV_PARAM_VALUE_LENGTH - 1);
				records[i] = new ArrayList<>(List.of(value, cvParam.getControlledVocabularyReferenceID(), cvParam.getUnitReferenceID()));
			}
			writeCompoundArray(file, Mz5.CV_PARAM, type, records);
		} finally {
			H5.H5Tclose(type);
			H5.H5Tclose(valueType);
		}
	}

	public static void writeCVReferences(long file, CVReference[] cvReferences) {

		long stringType = H5.H5Tcopy(HDF5Constants.H5T_C_S1);
		long type = -1;
		try {
			H5.H5Tset_size(stringType, HDF5Constants.H5T_VARIABLE);
			long stringSize = H5.H5Tget_size(stringType);
			type = H5.H5Tcreate(HDF5Constants.H5T_COMPOUND, 2 * stringSize + 4);
			H5.H5Tinsert(type, "name", 0, stringType);
			H5.H5Tinsert(type, "prefix", stringSize, stringType);
			H5.H5Tinsert(type, "accession", 2 * stringSize, HDF5Constants.H5T_NATIVE_UINT);
			List<?>[] records = new List<?>[cvReferences.length];
			for(int i = 0; i < cvReferences.length; i++) {
				CVReference cvReference = cvReferences[i];
				records[i] = new ArrayList<>(List.of(nonNull(cvReference.getName()), nonNull(cvReference.getPrefix()), cvReference.getAccession()));
			}
			writeCompoundArray(file, Mz5.CV_REFERENCE, type, records);
		} finally {
			if(type >= 0) {
				H5.H5Tclose(type);
			}
			H5.H5Tclose(stringType);
		}
	}

	private static void readCompoundArray(long file, String name, BiConsumer<Map<String, Integer>, List<?>> consumer) {

		long dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
		try {
			long fileType = H5.H5Dget_type(dataset);
			try {
				long memoryType = H5.H5Tget_native_type(fileType);
				try {
					Map<String, Integer> memberIndex = new HashMap<>();
					int members = H5.H5Tget_nmembers(memoryType);
					for(int i = 0; i < members; i++) {
						memberIndex.put(H5.H5Tget_member_name(memoryType, i), i);
					}
					List<?>[] records = new ArrayList<?>[getNumberOfElements(dataset)];
					if(records.length > 0) {
						H5.H5DreadVL(dataset, memoryType, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, records);
					}
					for(List<?> record : records) {
						consumer.accept(memberIndex, record);
					}
				} finally {
					H5.H5Tclose(memoryType);
				}
			} finally {
				H5.H5Tclose(fileType);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	private static void writeCompoundArray(long file, String name, long type, List<?>[] records) {

		long dataset = createDataset(file, name, type, records.length);
		try {
			if(records.length > 0) {
				H5.H5DwriteVL(dataset, type, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, records);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	private static long createDataset(long file, String name, long type, int length) {

		long dataspace = H5.H5Screate_simple(1, new long[]{length}, null);
		try {
			return H5.H5Dcreate(file, name, type, dataspace, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
		} finally {
			H5.H5Sclose(dataspace);
		}
	}

	private static int getNumberOfElements(long dataset) {

		long dataspace = H5.H5Dget_space(dataset);
		try {
			return (int)H5.H5Sget_simple_extent_npoints(dataspace);
		} finally {
			H5.H5Sclose(dataspace);
		}
	}

	private static String getString(List<?> record, Integer index) {

		if(index == null || record.get(index) == null) {
			return "";
		}
		return record.get(index).toString();
	}

	private static int getInt(List<?> record, Integer index) {

		if(index == null || !(record.get(index) instanceof Number number)) {
			return 0;
		}
		return number.intValue();
	}

	private static String nonNull(String value) {

		return value == null ? "" : value;
	}

	/**
	 * Cuts the value to fit into a fixed length string including the null terminator.
	 */
	private static String truncate(String value, int maxBytes) {

		String result = nonNull(value);
		while(result.getBytes(StandardCharsets.UTF_8).length > maxBytes) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
