/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.internal.model.CVParam;
import net.openchrom.msd.converter.supplier.mz5.internal.model.CVReference;
import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mz5.io.support.ScanMarker;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mz5.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mz5.model.VendorScanProxy;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	public ChromatogramReader() {

	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IVendorChromatogram chromatogram = null;
		try {
			chromatogram = new VendorChromatogram();
			//
			IHDF5SimpleReader reader = HDF5Factory.openForReading(file);
			CVReference[] cvReferences = reader.readCompoundArray("CVReference", CVReference.class);
			int dateReference = 0;
			for(int c = 0; c < cvReferences.length; c++) {
				if(cvReferences[c].accession == 1000747 && cvReferences[c].name.equals("completion time"))
					dateReference = c;
			}
			CVParam[] cvParams = reader.readCompoundArray("CVParam", CVParam.class);
			for(CVParam cvParam : cvParams) {
				if(cvParam.cvRefID == dateReference) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd+hh:mm");
					Date date = format.parse(cvParam.value);
					chromatogram.setDate(date);
				}
			}
		} catch(ParseException e) {
			logger.warn(e);
		}
		return chromatogram;
	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IVendorChromatogram chromatogram = null;
		chromatogram = new VendorChromatogram();
		IHDF5SimpleReader reader = HDF5Factory.openForReading(file);
		CVReference[] cvReferences = reader.readCompoundArray("CVReference", CVReference.class);
		int retentionTimeReference = 0;
		int spectrumTitleReference = 0;
		int msLevelReference = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000016 && cvReferences[c].name.equals("scan start time"))
				retentionTimeReference = c;
			if(cvReferences[c].accession == 1000796 && cvReferences[c].name.equals("spectrum title"))
				spectrumTitleReference = c;
			if(cvReferences[c].accession == 1000511 && cvReferences[c].name.equals("ms level"))
				msLevelReference = c;
		}
		int[] spectrumIndex = reader.readIntArray("SpectrumIndex");
		CVParam[] cvParams = reader.readCompoundArray("CVParam", CVParam.class);
		int[] retentionTimes = new int[spectrumIndex.length];
		String[] spectrumTitles = new String[spectrumIndex.length];
		short[] msLevels = new short[spectrumIndex.length];
		int p = 0;
		for(CVParam cvParam : cvParams) {
			if(cvParam.cvRefID == retentionTimeReference) {
				int multiplicator = 1; // to milisecond
				CVReference unit = cvReferences[cvParam.uRefID];
				if(unit.accession == 10 && unit.name.equals("second"))
					multiplicator = 1000;
				if(unit.accession == 31 && unit.name.equals("minute"))
					multiplicator = 60 * 1000;
				retentionTimes[p] = Math.round(Float.parseFloat(cvParam.value) * multiplicator);
				p++;
			}
			if(cvParam.cvRefID == spectrumTitleReference) {
				spectrumTitles[p] = cvParam.value;
			}
			if(cvParam.cvRefID == msLevelReference) {
				msLevels[p] = Short.parseShort(cvParam.value);
			}
		}
		try {
			double[] mzs = reader.readDoubleArray("SpectrumMZ");
			float[] spectrumIntensity = reader.readFloatArray("SpectrumIntensity");
			int start = 0;
			for(int i = 0; i < spectrumIndex.length; i++) {
				int offset = spectrumIndex[i];
				IScanMarker scanMarker = new ScanMarker(start, offset);
				IVendorScanProxy scanProxy = new VendorScanProxy(mzs, spectrumIntensity, scanMarker);
				scanProxy.setScanNumber(i);
				scanProxy.setIdentifier(spectrumTitles[i]);
				scanProxy.setRetentionTime(retentionTimes[i]);
				scanProxy.setMassSpectrometer(msLevels[i]);
				float totalSignal = 0;
				for(int o = start; o < offset; o++) {
					totalSignal = totalSignal + spectrumIntensity[o];
				}
				scanProxy.setTotalSignal(totalSignal);
				chromatogram.addScan(scanProxy);
				start = offset;
			}
			chromatogram.setFile(file);
		} catch(OutOfMemoryError e) {
			logger.error(e);
		}
		return chromatogram;
	}
}
