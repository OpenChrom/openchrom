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
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.io;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.chemclipse.converter.io.AbstractChromatogramReader;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.internal.model.CVParam;
import net.openchrom.msd.converter.supplier.mz5.internal.model.CVReference;
import net.openchrom.msd.converter.supplier.mz5.internal.model.Mz5;
import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mz5.io.support.ScanMarker;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mz5.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.mz5.model.VendorIon;
import net.openchrom.msd.converter.supplier.mz5.model.VendorScan;
import net.openchrom.msd.converter.supplier.mz5.model.VendorScanProxy;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ChromatogramReader extends AbstractChromatogramReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		try (IHDF5SimpleReader reader = HDF5Factory.openForReading(file)) {
			chromatogram = new VendorChromatogram();
			CVParam[] cvParams = reader.readCompoundArray(Mz5.CV_PARAM, CVParam.class);
			CVReference[] cvReferences = reader.readCompoundArray(Mz5.CV_REFERENCE, CVReference.class);
			readDate(cvReferences, cvParams, chromatogram);
			readTIC(reader, cvReferences, cvParams, chromatogram);
		}
		return chromatogram;
	}

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		chromatogram = new VendorChromatogram();
		chromatogram.setFile(file);
		try (IHDF5SimpleReader reader = HDF5Factory.openForReading(file)) {
			CVParam[] cvParams = reader.readCompoundArray(Mz5.CV_PARAM, CVParam.class);
			CVReference[] cvReferences = reader.readCompoundArray(Mz5.CV_REFERENCE, CVReference.class);
			readDate(cvReferences, cvParams, chromatogram);
			readTIC(reader, cvReferences, cvParams, chromatogram);
			readScanProxies(reader, cvReferences, cvParams, chromatogram);
		}
		return chromatogram;
	}

	private void readTIC(IHDF5SimpleReader reader, CVReference[] cvReferences, CVParam[] cvParams, IVendorChromatogram chromatogram) {

		if(!reader.exists(Mz5.CHROMATOGRAM_TIME) || !reader.exists(Mz5.CHROMATOGRAM_INTENSITY) || !reader.exists(Mz5.CHROMATOGRAM_INDEX)) {
			return;
		}
		double timeMultiplicator = getTimeArrayMultiplicator(cvReferences, cvParams);
		double[] retentionTimes = reader.readDoubleArray(Mz5.CHROMATOGRAM_TIME);
		double[] totalIntensities = reader.readDoubleArray(Mz5.CHROMATOGRAM_INTENSITY);
		int[] chromatogramIndex = reader.readIntArray(Mz5.CHROMATOGRAM_INDEX);
		for(int i = 0; i < chromatogramIndex[0]; i++) {
			IRegularMassSpectrum scan = new VendorScan();
			VendorIon ion = new VendorIon(IIon.TIC_ION, (float)totalIntensities[i]);
			scan.addIon(ion, false);
			scan.setRetentionTime((int)Math.round(retentionTimes[i] * timeMultiplicator));
			chromatogram.addScan(scan);
		}
	}

	private void readDate(CVReference[] cvReferences, CVParam[] cvParams, IVendorChromatogram chromatogram) {

		int dateReference = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000747 && cvReferences[c].name.equals("completion time")) {
				dateReference = c;
			}
		}
		for(CVParam cvParam : cvParams) {
			if(cvParam.cvRefID == dateReference) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd+hh:mm");
				try {
					Date date = format.parse(cvParam.value);
					chromatogram.setDate(date);
				} catch(ParseException e) {
					logger.warn(e);
				}
			}
		}
	}

	private void readScanProxies(IHDF5SimpleReader reader, CVReference[] cvReferences, CVParam[] cvParams, IVendorChromatogram chromatogram) {

		if(!reader.exists(Mz5.SPECTRUM_INDEX) || !reader.exists(Mz5.SPECTRUM_MZ) || !reader.exists(Mz5.SPECTRUM_INTENSITY)) {
			return;
		}
		int[] spectrumIndex = reader.readIntArray(Mz5.SPECTRUM_INDEX);
		int[] retentionTimes = new int[spectrumIndex.length];
		String[] spectrumTitles = new String[spectrumIndex.length];
		short[] msLevels = new short[spectrumIndex.length];
		double[] selectedIon = new double[spectrumIndex.length];
		int p = 0;
		for(CVParam cvParam : cvParams) {
			if(cvParam.cvRefID == getScanStartTimeReference(cvReferences)) {
				CVReference unit = cvReferences[cvParam.uRefID];
				retentionTimes[p] = Math.round(Float.parseFloat(cvParam.value) * getTimeMultiplicator(unit));
				p++;
			}
			if(cvParam.cvRefID == getSpectrumTitleReference(cvReferences)) {
				spectrumTitles[p] = cvParam.value;
			}
			if(cvParam.cvRefID == getMassSpectrumLevelReference(cvReferences)) {
				msLevels[p] = Short.parseShort(cvParam.value);
			}
			if(cvParam.cvRefID == getSelectedIonReference(cvReferences)) {
				selectedIon[p] = Double.parseDouble(cvParam.value);
			}
		}
		try {
			double[] mzs = reader.readDoubleArray(Mz5.SPECTRUM_MZ);
			float[] spectrumIntensity = reader.readFloatArray(Mz5.SPECTRUM_INTENSITY);
			int start = 0;
			int cycleNumber = isMultiStageMassSpectrum(cvParams, cvReferences) ? 1 : 0;
			for(int i = 0; i < spectrumIndex.length; i++) {
				int offset = spectrumIndex[i];
				IScanMarker scanMarker = new ScanMarker(start, offset);
				IVendorScanProxy scanProxy = new VendorScanProxy(mzs, spectrumIntensity, scanMarker);
				scanProxy.setScanNumber(i);
				scanProxy.setIdentifier(spectrumTitles[i]);
				scanProxy.setRetentionTime(retentionTimes[i]);
				scanProxy.setMassSpectrometer(msLevels[i]);
				if(scanProxy.getMassSpectrometer() < 2) {
					cycleNumber++;
				} else {
					scanProxy.setPrecursorIon(selectedIon[i]);
				}
				if(cycleNumber >= 1) {
					scanProxy.setCycleNumber(cycleNumber);
				}
				float totalSignal = 0;
				for(int o = start; o < offset; o++) {
					totalSignal = totalSignal + spectrumIntensity[o];
				}
				scanProxy.setTotalSignal(totalSignal);
				chromatogram.addScan(scanProxy);
				start = offset;
			}
		} catch(OutOfMemoryError e) {
			logger.error(e);
		}
	}

	private double getTimeArrayMultiplicator(CVReference[] cvReferences, CVParam[] cvParams) {

		int timeMultiplicator = 1; // miliseconds
		for(CVParam cvParam : cvParams) {
			if(cvParam.cvRefID == getTimeArrayReference(cvReferences)) {
				CVReference unit = cvReferences[cvParam.uRefID];
				timeMultiplicator = getTimeMultiplicator(unit);
			}
		}
		return timeMultiplicator;
	}

	private int getMassSpectrumLevelReference(CVReference[] cvReferences) {

		int msLevelReference = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000511 && cvReferences[c].name.equals("ms level")) {
				msLevelReference = c;
			}
		}
		return msLevelReference;
	}

	private int getSpectrumTitleReference(CVReference[] cvReferences) {

		int spectrumTitleReference = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000796 && cvReferences[c].name.equals("spectrum title")) {
				spectrumTitleReference = c;
			}
		}
		return spectrumTitleReference;
	}

	private int getScanStartTimeReference(CVReference[] cvReferences) {

		int scanStartTime = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000016 && cvReferences[c].name.equals("scan start time")) {
				scanStartTime = c;
			}
		}
		return scanStartTime;
	}

	private int getTimeArrayReference(CVReference[] cvReferences) {

		int scanStartTime = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000595 && cvReferences[c].name.equals("time array")) {
				scanStartTime = c;
			}
		}
		return scanStartTime;
	}

	private int getTimeMultiplicator(CVReference unit) {

		int multiplicator = 1; // to milisecond
		if(unit.accession == 10 && unit.name.equals("second")) {
			multiplicator = (int)IChromatogramOverview.SECOND_CORRELATION_FACTOR;
		}
		if(unit.accession == 31 && unit.name.equals("minute")) {
			multiplicator = (int)IChromatogramOverview.MINUTE_CORRELATION_FACTOR;
		}
		return multiplicator;
	}

	private boolean isMultiStageMassSpectrum(CVParam[] cvParams, CVReference[] cvReferences) {

		for(CVParam cvParam : cvParams) {
			if(cvParam.cvRefID == getMassSpectrumLevelReference(cvReferences)) {
				if(Short.parseShort(cvParam.value) > 1) {
					return true;
				}
			}
		}
		return false;
	}

	private int getSelectedIonReference(CVReference[] cvReferences) {

		int selectedIonRef = 0;
		for(int c = 0; c < cvReferences.length; c++) {
			if(cvReferences[c].accession == 1000744 && cvReferences[c].name.equals("selected ion m/z")) {
				selectedIonRef = c;
			}
		}
		return selectedIonRef;
	}
}
