/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
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

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;

public class ChromatogramWriter extends AbstractChromatogramWriter implements IChromatogramMSDWriter {

	private List<CVReference> cvReferences = new ArrayList<>();
	private List<CVParam> cvParams = new ArrayList<>();
	private int unitRefID;
	private int spectrumTitleRefID;
	private int scanStartTimeRefID;
	private int msLevelRefID;

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		unitRefID = addRetentionTimeUnit();
		spectrumTitleRefID = addSpectrumTitleReference();
		scanStartTimeRefID = addScanStartTimeReference();
		msLevelRefID = addMassSpectrometerLevelReference();
		try (IHDF5SimpleWriter writer = HDF5Factory.open(file)) {
			addDate(chromatogram);
			addLevel(chromatogram);
			addScans(chromatogram, writer);
			writer.writeCompoundArray("CVReference", cvReferences.toArray(new CVReference[0]));
			writer.writeCompoundArray("CVParam", cvParams.toArray(new CVParam[0]));
		}
	}

	private int addRetentionTimeUnit() {

		CVReference cvReference = new CVReference();
		cvReference.accession = 10;
		cvReference.name = "second";
		cvReference.prefix = "UO";
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

	private void addScans(IChromatogramMSD chromatogram, IHDF5SimpleWriter writer) {

		int scans = chromatogram.getNumberOfScans();
		int[] spectrumIndex = new int[scans];
		int ions = chromatogram.getNumberOfScanIons();
		double[] spectrumMZs = new double[ions];
		float[] spectrumIntensity = new float[ions];
		int s = 0;
		int i = 0;
		for(IScan scan : chromatogram.getScans()) {
			spectrumIndex[i] = s;
			addSpectrumTitle(scan);
			addRetentionTime(scan);
			if(scan instanceof IScanMSD scanMSD) {
				for(IIon ion : scanMSD.getIons()) {
					spectrumMZs[s] = ion.getIon();
					spectrumIntensity[s] = ion.getAbundance();
					s++;
				}
			}
			i++;
		}
		writer.writeIntArray("SpectrumIndex", spectrumIndex);
		writer.writeDoubleArray("SpectrumMZ", spectrumMZs);
		writer.writeFloatArray("SpectrumIntensity", spectrumIntensity);
	}

	private void addSpectrumTitle(IScan scan) {

		CVParam cvParam = new CVParam();
		cvParam.cvRefID = spectrumTitleRefID;
		cvParam.value = scan.getIdentifier();
		cvParams.add(cvParam);
	}

	private void addRetentionTime(IScan scan) {

		CVParam cvParam = new CVParam();
		cvParam.cvRefID = scanStartTimeRefID;
		cvParam.uRefID = unitRefID;
		cvParam.value = Float.toString(scan.getRetentionTime() / (float)IChromatogramOverview.SECOND_CORRELATION_FACTOR);
		cvParams.add(cvParam);
	}

	private void addLevel(IChromatogramMSD chromatogram) {

		if(chromatogram.getScan(0) instanceof IRegularMassSpectrum massSpectrum) {
			CVParam cvParam = new CVParam();
			cvParam.cvRefID = msLevelRefID;
			cvParam.value = Short.toString(massSpectrum.getMassSpectrometer());
			cvParams.add(cvParam);
		}
	}
}
