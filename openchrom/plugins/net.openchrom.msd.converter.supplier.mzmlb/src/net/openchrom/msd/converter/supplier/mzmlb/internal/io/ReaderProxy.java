/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactor m/z and abundance limit
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.internal.io;

import java.io.IOException;
import java.lang.reflect.Array;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.io.IReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorIon;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorIon;

import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ReaderProxy implements IReaderProxy {

	@Override
	public void readMassSpectrum(IHDF5SimpleReader reader, IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		double[] mzs = null;
		String mzDataset = scanMarker.getMassesDataset();
		if(mzDataset.contains("float64")) {
			mzs = reader.readDoubleArray(mzDataset);
		} else if(mzDataset.contains("float32")) {
			mzs = floatsToDoubles(reader.readFloatArray(mzDataset));
		}
		float[] intensities = null;
		String intensityDataset = scanMarker.getIntensitiesDataset();
		if(intensityDataset.contains("float64")) {
			intensities = doublesToFloats(reader.readDoubleArray(intensityDataset));
		} else if(intensityDataset.contains("float32")) {
			intensities = reader.readFloatArray(intensityDataset);
		}
		for(int i = scanMarker.getOffset(); i < scanMarker.getOffset() + scanMarker.getLength(); i++) {
			float abundance = Array.getFloat(intensities, i);
			double mz = Array.getDouble(mzs, i);
			if(abundance >= VendorIon.MIN_ABUNDANCE && abundance <= VendorIon.MAX_ABUNDANCE && mz > VendorIon.MIN_ION && mz < VendorIon.MAX_ION) {
				IVendorIon ion = new VendorIon(mz, abundance);
				massSpectrum.addIon(ion);
			}
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