/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.io.XmlWriter110;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.BinaryDataArrayListType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.BinaryDataArrayType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.CVParamType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.MzMLType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.ObjectFactory;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.RunType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.ScanListType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.ScanType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.SpectrumListType;
import org.eclipse.chemclipse.xxd.converter.supplier.mzml.model.v110.SpectrumType;
import org.eclipse.core.runtime.IProgressMonitor;

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class ChromatogramWriter extends AbstractChromatogramWriter implements IChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try (IHDF5SimpleWriter writer = HDF5Factory.open(file)) {
			writeMzML(createMzML(chromatogram), writer);
			writeScans(chromatogram, writer);
		}
	}

	private void writeScans(IChromatogramMSD chromatogram, IHDF5SimpleWriter writer) {

		int dataPoints = chromatogram.getNumberOfScanIons();
		double[] mzs = new double[dataPoints];
		float[] intensities = new float[dataPoints];
		long[] byteOffsets = new long[chromatogram.getNumberOfScans()];
		int lastByteOffset = 0;
		int index = 0;
		for(IScan scan : chromatogram.getScans()) {
			byteOffsets[scan.getScanNumber() - 1] = lastByteOffset;
			if(scan instanceof IScanMSD scanMSD) {
				for(IIon ion : scanMSD.getIons()) {
					mzs[index] = ion.getIon();
					intensities[index] = ion.getAbundance();
					index++;
				}
				lastByteOffset += scanMSD.getNumberOfIons() * (Double.BYTES + Float.BYTES);
			}
		}
		writer.writeDoubleArray("spectrum_MS_1000514_float64", mzs);
		writer.writeFloatArray("spectrum_MS_1000515_float32", intensities);
		writer.writeLongArray("mzML_spectrumIndex", byteOffsets);
		writer.writeLongArray("mzML_chromatogramIndex", new long[]{byteOffsets[byteOffsets.length - 1]});
	}

	private RunType createRun(IChromatogramMSD chromatogram) {

		RunType run = new RunType();
		run.setSpectrumList(createSpectrumList(chromatogram));
		return run;
	}

	private MzMLType createMzML(IChromatogramMSD chromatogram) {

		MzMLType mzML = new MzMLType();
		mzML.setId(chromatogram.getFile().getName());
		mzML.setRun(createRun(chromatogram));
		return mzML;
	}

	private void writeMzML(MzMLType mzML, IHDF5SimpleWriter writer) {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			marshaller.marshal(mzML, byteArrayOutputStream);
			byte[] mzMLbyteArray = byteArrayOutputStream.toByteArray();
			writer.writeByteArray("mzML", mzMLbyteArray);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	private SpectrumListType createSpectrumList(IChromatogramMSD chromatogram) {

		SpectrumListType spectrumList = new SpectrumListType();
		spectrumList.setCount(BigInteger.valueOf(chromatogram.getNumberOfScans()));
		int offset = 0;
		for(IScan scan : chromatogram.getScans()) {
			if(scan instanceof IScanMSD scanMSD) {
				spectrumList.getSpectrum().add(createSpectrum(scanMSD, offset));
				offset += scanMSD.getNumberOfIons();
			}
		}
		return spectrumList;
	}

	private SpectrumType createSpectrum(IScanMSD scanMSD, int offset) {

		SpectrumType spectrum = new SpectrumType();
		spectrum.setId("scan=" + scanMSD.getScanNumber());
		spectrum.setIndex(BigInteger.valueOf((scanMSD.getScanNumber() - 1)));
		spectrum.getCvParam().add(createTotalIonCurrentType(scanMSD));
		if(scanMSD instanceof IRegularMassSpectrum massSpectrum) {
			spectrum.getCvParam().add(createSpectrumLevel(massSpectrum));
		}
		spectrum.setScanList(createScanList(scanMSD));
		spectrum.setBinaryDataArrayList(createBinaryDataArrayList(scanMSD, offset));
		return spectrum;
	}

	private BinaryDataArrayListType createBinaryDataArrayList(IScanMSD scanMSD, int offset) {

		BinaryDataArrayListType binaryDataArrayListType = new BinaryDataArrayListType();
		binaryDataArrayListType.setCount(BigInteger.valueOf(2));
		binaryDataArrayListType.getBinaryDataArray().add(mzArrayType(scanMSD, offset));
		binaryDataArrayListType.getBinaryDataArray().add(createIntensityArrayType(scanMSD, offset));
		return binaryDataArrayListType;
	}

	private BinaryDataArrayType mzArrayType(IScanMSD scanMSD, int offset) {

		BinaryDataArrayType mzArrayType = new BinaryDataArrayType();
		mzArrayType.getCvParam().add(createMzArrayType());
		mzArrayType.getCvParam().add(createZlibCompressionParamType());
		mzArrayType.getCvParam().add(createDoubleParamType());
		mzArrayType.getCvParam().add(createExternalHdf5MzDataset());
		mzArrayType.getCvParam().add(createExternalArrayLength(scanMSD));
		mzArrayType.getCvParam().add(createExternalOffset(offset));
		return mzArrayType;
	}

	private CVParamType createMzArrayType() {

		CVParamType cvParamMz = new CVParamType();
		cvParamMz.setCvRef(XmlWriter110.MS);
		cvParamMz.setAccession("MS:1000514");
		cvParamMz.setName("m/z array");
		cvParamMz.setUnitCvRef(XmlWriter110.MS);
		cvParamMz.setUnitAccession("MS:1000040");
		cvParamMz.setUnitName("m/z");
		return cvParamMz;
	}

	private BinaryDataArrayType createIntensityArrayType(IScanMSD scanMSD, int offset) {

		BinaryDataArrayType intensityArrayType = new BinaryDataArrayType();
		intensityArrayType.getCvParam().add(XmlWriter110.createIntensityArrayType()); // TODO rename
		intensityArrayType.getCvParam().add(createZlibCompressionParamType());
		intensityArrayType.getCvParam().add(createFloatParamType());
		intensityArrayType.getCvParam().add(createExternalHdf5IntensityDataset());
		intensityArrayType.getCvParam().add(createExternalArrayLength(scanMSD));
		intensityArrayType.getCvParam().add(createExternalOffset(offset));
		return intensityArrayType;
	}

	private CVParamType createZlibCompressionParamType() { // TODO: deduplicate

		CVParamType cvParamCompression = new CVParamType();
		cvParamCompression.setCvRef(XmlWriter110.MS);
		cvParamCompression.setAccession("MS:1000574");
		cvParamCompression.setName("zlib compression");
		return cvParamCompression;
	}

	private CVParamType createFloatParamType() { // TODO: deduplicate

		CVParamType cvParamData = new CVParamType();
		cvParamData.setCvRef(XmlWriter110.MS);
		cvParamData.setAccession("MS:1000521");
		cvParamData.setName("32-bit float");
		return cvParamData;
	}

	private CVParamType createDoubleParamType() { // TODO: deduplicate

		CVParamType cvParamData = new CVParamType();
		cvParamData.setCvRef(XmlWriter110.MS);
		cvParamData.setAccession("MS:1000523");
		cvParamData.setName("64-bit float");
		return cvParamData;
	}

	private CVParamType createExternalHdf5MzDataset() {

		CVParamType cvParamExternalHdf5Datase = new CVParamType();
		cvParamExternalHdf5Datase.setCvRef(XmlWriter110.MS);
		cvParamExternalHdf5Datase.setAccession("MS:1002841");
		cvParamExternalHdf5Datase.setName("external HDF5 dataset");
		cvParamExternalHdf5Datase.setValue("spectrum_MS_1000514_float64");
		return cvParamExternalHdf5Datase;
	}

	private CVParamType createExternalHdf5IntensityDataset() {

		CVParamType cvParamExternalHdf5Datase = new CVParamType();
		cvParamExternalHdf5Datase.setCvRef(XmlWriter110.MS);
		cvParamExternalHdf5Datase.setAccession("MS:1002841");
		cvParamExternalHdf5Datase.setName("external HDF5 dataset");
		cvParamExternalHdf5Datase.setValue("spectrum_MS_1000515_float32");
		return cvParamExternalHdf5Datase;
	}

	private CVParamType createExternalOffset(int offset) {

		CVParamType cvParamExternalOffset = new CVParamType();
		cvParamExternalOffset.setCvRef(XmlWriter110.MS);
		cvParamExternalOffset.setAccession("MS:1002842");
		cvParamExternalOffset.setName("external offset");
		cvParamExternalOffset.setValue(String.valueOf(offset));
		cvParamExternalOffset.setUnitCvRef(XmlWriter110.UO);
		cvParamExternalOffset.setUnitAccession("UO:0000189");
		cvParamExternalOffset.setUnitName("count unit");
		return cvParamExternalOffset;
	}

	private CVParamType createExternalArrayLength(IScanMSD scanMSD) {

		CVParamType cvParamExternalArrayLength = new CVParamType();
		cvParamExternalArrayLength.setCvRef(XmlWriter110.MS);
		cvParamExternalArrayLength.setAccession("MS:1002843");
		cvParamExternalArrayLength.setName("external array length");
		cvParamExternalArrayLength.setValue(String.valueOf(scanMSD.getNumberOfIons()));
		cvParamExternalArrayLength.setUnitCvRef(XmlWriter110.UO);
		cvParamExternalArrayLength.setUnitAccession("UO:0000189");
		cvParamExternalArrayLength.setUnitName("count unit");
		return cvParamExternalArrayLength;
	}

	private ScanListType createScanList(IScan scan) {

		ScanListType scanList = new ScanListType();
		scanList.getScan().add(createScanType(scan));
		return scanList;
	}

	private ScanType createScanType(IScan scan) {

		ScanType scanType = new ScanType();
		scanType.getCvParam().add(createScanStartTimeType(scan));
		return scanType;
	}

	private CVParamType createScanStartTimeType(IScan scan) { // TODO: deduplicate

		CVParamType cvParamScanStartTime = new CVParamType();
		cvParamScanStartTime.setCvRef(XmlWriter110.MS);
		cvParamScanStartTime.setAccession("MS:1000016");
		cvParamScanStartTime.setName("scan start time");
		cvParamScanStartTime.setUnitCvRef(XmlWriter110.UO);
		cvParamScanStartTime.setUnitAccession("UO:0000031");
		cvParamScanStartTime.setUnitName("minute");
		cvParamScanStartTime.setValue(String.valueOf(scan.getRetentionTime() / IChromatogramOverview.MINUTE_CORRELATION_FACTOR));
		return cvParamScanStartTime;
	}

	private CVParamType createTotalIonCurrentType(IScan scan) { // TODO: deduplicate

		CVParamType cvParamTotalIonCurrent = new CVParamType();
		cvParamTotalIonCurrent.setCvRef(XmlWriter110.MS);
		cvParamTotalIonCurrent.setAccession("MS:1000285");
		cvParamTotalIonCurrent.setName("total ion current");
		cvParamTotalIonCurrent.setValue(String.valueOf(scan.getTotalSignal()));
		return cvParamTotalIonCurrent;
	}

	private CVParamType createSpectrumLevel(IRegularMassSpectrum massSpectrum) { // TODO: deduplicate

		CVParamType cvParamLevel = new CVParamType();
		cvParamLevel.setCvRef(XmlWriter110.MS);
		cvParamLevel.setAccession("MS:1000511");
		cvParamLevel.setName("ms level");
		cvParamLevel.setValue(String.valueOf(massSpectrum.getMassSpectrometer()));
		return cvParamLevel;
	}
}
