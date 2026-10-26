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
package net.openchrom.msd.converter.supplier.mzmlb.internal.io;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;

/**
 * Reads and writes one-dimensional mzMLb datasets.
 * Numeric data is converted by HDF5 into the requested type.
 */
public class MzMLbDatasets {

	private MzMLbDatasets() {

	}

	public static byte[] readByteArray(long file, String name) {

		long dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
		try {
			byte[] data = new byte[getNumberOfElements(dataset)];
			if(data.length > 0) {
				H5.H5Dread(dataset, HDF5Constants.H5T_NATIVE_SCHAR, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
			return data;
		} finally {
			H5.H5Dclose(dataset);
		}
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

	public static void writeByteArray(long file, String name, byte[] data) {

		long dataset = createDataset(file, name, HDF5Constants.H5T_STD_I8LE, data.length);
		try {
			if(data.length > 0) {
				H5.H5Dwrite(dataset, HDF5Constants.H5T_NATIVE_SCHAR, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			}
		} finally {
			H5.H5Dclose(dataset);
		}
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

	public static void writeLongArray(long file, String name, long[] data) {

		long dataset = createDataset(file, name, HDF5Constants.H5T_STD_I64LE, data.length);
		try {
			if(data.length > 0) {
				H5.H5Dwrite_long(dataset, HDF5Constants.H5T_NATIVE_LLONG, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
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
}
