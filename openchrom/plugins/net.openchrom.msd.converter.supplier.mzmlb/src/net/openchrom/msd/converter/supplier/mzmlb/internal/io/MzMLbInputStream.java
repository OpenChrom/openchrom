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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;

/**
 * Streams a one-dimensional byte dataset (e.g. the embedded mzML document)
 * block by block, so that datasets larger than a Java array can be read.
 */
public class MzMLbInputStream extends InputStream {

	private static final int BLOCK_SIZE = 4 * 1024 * 1024;

	private final long file;
	private final long dataset;
	private final long fileSpace;
	private final long size;
	private final byte[] buffer;

	private long position = 0; // dataset offset of the next block
	private int bufferPosition = 0;
	private int bufferLength = 0;
	private boolean closed = false;

	public MzMLbInputStream(File mzMLb, String name) {

		file = H5.H5Fopen(mzMLb.getAbsolutePath(), HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
		try {
			dataset = H5.H5Dopen(file, name, HDF5Constants.H5P_DEFAULT);
			try {
				fileSpace = H5.H5Dget_space(dataset);
				size = H5.H5Sget_simple_extent_npoints(fileSpace);
			} catch(RuntimeException e) {
				H5.H5Dclose(dataset);
				throw e;
			}
		} catch(RuntimeException e) {
			H5.H5Fclose(file);
			throw e;
		}
		buffer = new byte[(int)Math.min(BLOCK_SIZE, Math.max(size, 1))];
	}

	public long size() {

		return size;
	}

	@Override
	public int read() throws IOException {

		if(!fill()) {
			return -1;
		}
		return buffer[bufferPosition++] & 0xFF;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {

		if(len == 0) {
			return 0;
		}
		if(!fill()) {
			return -1;
		}
		int n = Math.min(len, bufferLength - bufferPosition);
		System.arraycopy(buffer, bufferPosition, b, off, n);
		bufferPosition += n;
		return n;
	}

	@Override
	public int available() {

		return bufferLength - bufferPosition;
	}

	private boolean fill() throws IOException {

		if(closed) {
			throw new IOException("Stream closed");
		}
		if(bufferPosition < bufferLength) {
			return true;
		}
		if(position >= size) {
			return false;
		}
		int length = (int)Math.min(buffer.length, size - position);
		try {
			H5.H5Sselect_hyperslab(fileSpace, HDF5Constants.H5S_SELECT_SET, new long[]{position}, null, new long[]{length}, null);
			long memorySpace = H5.H5Screate_simple(1, new long[]{length}, null);
			try {
				H5.H5Dread(dataset, HDF5Constants.H5T_NATIVE_SCHAR, memorySpace, fileSpace, HDF5Constants.H5P_DEFAULT, buffer);
			} finally {
				H5.H5Sclose(memorySpace);
			}
		} catch(HDF5Exception e) {
			throw new IOException(e);
		}
		position += length;
		bufferPosition = 0;
		bufferLength = length;
		return true;
	}

	@Override
	public void close() throws IOException {

		if(closed) {
			return;
		}
		closed = true;
		try {
			try {
				H5.H5Sclose(fileSpace);
			} finally {
				try {
					H5.H5Dclose(dataset);
				} finally {
					H5.H5Fclose(file);
				}
			}
		} catch(HDF5Exception e) {
			throw new IOException(e);
		}
	}
}
