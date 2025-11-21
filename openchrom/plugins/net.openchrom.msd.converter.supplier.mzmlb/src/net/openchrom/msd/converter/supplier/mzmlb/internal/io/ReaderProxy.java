/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactor m/z and abundance limit
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.internal.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.io.IReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorIon;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorIon;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ReaderProxy implements IReaderProxy {

	private double[] mzs = null;
	private float[] intensities = null;

	private File file;

	public ReaderProxy(File file) {

		this.file = file;
	}

	public void setMzDataset(String mzDataset) {

		try (IHDF5SimpleReader reader = HDF5Factory.openForReading(file)) {
			if(mzDataset.contains("float64")) {
				mzs = reader.readDoubleArray(mzDataset);
			} else if(mzDataset.contains("float32")) {
				mzs = floatsToDoubles(reader.readFloatArray(mzDataset));
			}
		}
	}

	public void setIntensityDataset(String intensityDataset) {

		try (IHDF5SimpleReader reader = HDF5Factory.openForReading(file)) {

			if(intensityDataset.contains("float64")) {
				intensities = doublesToFloats(reader.readDoubleArray(intensityDataset));
			} else if(intensityDataset.contains("float32")) {
				intensities = reader.readFloatArray(intensityDataset);
			}
		}
	}

	@Override
	public void readMassSpectrum(IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		for(int i = scanMarker.getOffset(); i < scanMarker.getOffset() + scanMarker.getLength(); i++) {
			float abundance = Array.getFloat(intensities, i);
			double mz = Array.getDouble(mzs, i);
			IVendorIon ion = new VendorIon(mz, abundance);
			massSpectrum.addIon(ion);
		}
	}

	private static float[] doublesToFloats(final double[] input) {

		float[] output = new float[input.length];
		for(int i = 0; i < input.length; i++) {
			output[i] = (float)input[i];
		}
		return output;
	}

	private static double[] floatsToDoubles(final float[] input) {

		double[] output = new double[input.length];
		for(int i = 0; i < input.length; i++) {
			output[i] = input[i];
		}
		return output;
	}
}