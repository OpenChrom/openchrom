/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.io.IReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorIon;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mzmlb.model.VendorIon;

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;

public class ReaderProxy implements IReaderProxy {

	private static final Logger logger = Logger.getLogger(ReaderProxy.class);

	private double[] mzs = null;
	private float[] intensities = null;

	private File file;

	private String mzDataset;
	private String intensityDataset;

	public ReaderProxy(File file) {

		this.file = file;
	}

	@Override
	public void setMzDataset(String mzDataset) {

		this.mzDataset = mzDataset;
	}

	@Override
	public void setIntensityDataset(String intensityDataset) {

		this.intensityDataset = intensityDataset;
	}

	@Override
	public void readMassSpectrum(IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		if(mzs == null || intensities == null) {
			try {
				long reader = H5.H5Fopen(file.getAbsolutePath(), HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
				try {
					mzs = MzMLbDatasets.readDoubleArray(reader, mzDataset);
					intensities = MzMLbDatasets.readFloatArray(reader, intensityDataset);
				} finally {
					H5.H5Fclose(reader);
				}
			} catch(HDF5Exception e) {
				logger.error(e);
			}
		}

		for(int i = scanMarker.getOffset(); i < scanMarker.getOffset() + scanMarker.getLength(); i++) {
			float abundance = intensities[i];
			double mz = mzs[i];
			IVendorIon ion = new VendorIon(mz, abundance);
			massSpectrum.addIon(ion, false);
		}
	}
}