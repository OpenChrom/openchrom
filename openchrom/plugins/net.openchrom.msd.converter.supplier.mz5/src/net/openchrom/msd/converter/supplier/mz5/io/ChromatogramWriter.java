/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.internal.model.CVParam;
import net.openchrom.msd.converter.supplier.mz5.internal.model.CVReference;
import net.openchrom.msd.converter.supplier.mz5.internal.model.Mz5;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;

public class ChromatogramWriter extends AbstractChromatogramWriter implements IChromatogramMSDWriter {

	private List<CVReference> cvReferences = new ArrayList<>();
	private List<CVParam> cvParams = new ArrayList<>();

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try (IHDF5SimpleWriter writer = HDF5Factory.open(file)) {
			addDate(chromatogram);
			addTIC(chromatogram, writer);
			addLevel(chromatogram, addMassSpectrometerLevelReference());
			addScans(chromatogram, writer);
			writer.writeCompoundArray(Mz5.CV_REFERENCE, cvReferences.toArray(new CVReference[0]));
			writer.writeCompoundArray(Mz5.CV_PARAM, cvParams.toArray(new CVParam[0]));
		}
	}

	private void addTIC(IChromatogramMSD chromatogram, IHDF5SimpleWriter writer) {

		addTimeArray(addUnitMinute(), addTimeArrayReference());
		addIntensityArray(addUnitDetectorCount(), addIntensityArrayReference());
		int scans = chromatogram.getNumberOfScans();
		double[] chromatogramTimes = new double[scans];
		double[] chromatogramIntensities = new double[scans];
		for(IScan scan : chromatogram.getScans()) {
			int i = scan.getScanNumber() - 1;
			chromatogramTimes[i] = scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR;
			chromatogramIntensities[i] = scan.getTotalSignal();
		}
		writer.writeDoubleArray(Mz5.CHROMATOGRAM_TIME, chromatogramTimes);
		writer.writeDoubleArray(Mz5.CHROMATOGRAM_INTENSITY, chromatogramIntensities);
	}

	private void addScans(IChromatogramMSD chromatogram, IHDF5SimpleWriter writer) {

		int spectrumTitleRefID = addSpectrumTitleReference();
		int scanStartTimeRefID = addScanStartTimeReference();
		int unitSecondRefID = addUnitSecond();
		int scans = chromatogram.getNumberOfScans();
		int[] spectrumIndex = new int[scans];
		int ions = chromatogram.getNumberOfScanIons();
		double[] spectrumMZs = new double[ions];
		float[] spectrumIntensity = new float[ions];
		int s = 0;
		int i = 0;
		for(IScan scan : chromatogram.getScans()) {
			spectrumIndex[i] = s;
			addSpectrumTitle(scan, spectrumTitleRefID);
			addRetentionTime(scan, scanStartTimeRefID, unitSecondRefID);
			if(scan instanceof IScanMSD scanMSD) {
				for(IIon ion : scanMSD.getIons()) {
					spectrumMZs[s] = ion.getIon();
					spectrumIntensity[s] = ion.getAbundance();
					s++;
				}
			}
			i++;
		}
		writer.writeIntArray(Mz5.SPECTRUM_INDEX, spectrumIndex);
		writer.writeDoubleArray(Mz5.SPECTRUM_MZ, spectrumMZs);
		writer.writeFloatArray(Mz5.SPECTRUM_INTENSITY, spectrumIntensity);
	}

	private int addUnitMinute() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 31;
		cvReference.name = "minute";
		cvReference.prefix = "UO";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addUnitSecond() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 10;
		cvReference.name = "second";
		cvReference.prefix = "UO";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addUnitDetectorCount() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000131;
		cvReference.name = "number of detector counts";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private void addTimeArray(int timeArrayRefID, int unitMinuteRefID) {

		CVParam cvParam = new CVParam();
		cvParam.cvRefID = timeArrayRefID;
		cvParam.uRefID = unitMinuteRefID;
		cvParam.value = "";
		cvParams.add(cvParam);
	}

	private void addIntensityArray(int intensityArrayRefID, int unitMinuteRefID) {

		CVParam cvParam = new CVParam();
		cvParam.cvRefID = intensityArrayRefID;
		cvParam.uRefID = unitMinuteRefID;
		cvParam.value = "";
		cvParams.add(cvParam);
	}

	private int addTimeArrayReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000595;
		cvReference.name = "time array";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addIntensityArrayReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000515;
		cvReference.name = "intensity array";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addSpectrumTitleReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000796;
		cvReference.name = "spectrum title";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addDateReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000747;
		cvReference.name = "completion time";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addScanStartTimeReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000016;
		cvReference.name = "scan start time";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private int addMassSpectrometerLevelReference() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 1000511;
		cvReference.name = "ms level";
		cvReference.prefix = "MS";
		cvReferences.add(cvReference);
		return cvReferences.size() - 1;
	}

	private void addDate(IChromatogramMSD chromatogram) {

		if(chromatogram.getDate() == null) {
			return;
		}
		CVParam cvParam = new CVParam();
		cvParam.cvRefID = addDateReference();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd+hh:mm");
		cvParam.value = simpleDateFormat.format(chromatogram.getDate());
		cvParams.add(cvParam);
	}

	private void addSpectrumTitle(IScan scan, int spectrumTitleRefID) {

		if(scan.getIdentifier() == null) {
			return;
		}
		CVParam cvParam = new CVParam();
		cvParam.cvRefID = spectrumTitleRefID;
		cvParam.value = scan.getIdentifier();
		cvParams.add(cvParam);
	}

	private void addRetentionTime(IScan scan, int scanStartTimeRefID, int unitSecondRefID) {

		CVParam cvParam = new CVParam();
		cvParam.cvRefID = scanStartTimeRefID;
		cvParam.uRefID = unitSecondRefID;
		cvParam.value = Float.toString(scan.getRetentionTime() / (float)IChromatogramOverview.SECOND_CORRELATION_FACTOR);
		cvParams.add(cvParam);
	}

	private void addLevel(IChromatogramMSD chromatogram, int msLevelRefID) {

		if(chromatogram.getScan(0) instanceof IRegularMassSpectrum massSpectrum) {
			CVParam cvParam = new CVParam();
			cvParam.cvRefID = msLevelRefID;
			cvParam.value = Short.toString(massSpectrum.getMassSpectrometer());
			cvParams.add(cvParam);
		}
	}
}
