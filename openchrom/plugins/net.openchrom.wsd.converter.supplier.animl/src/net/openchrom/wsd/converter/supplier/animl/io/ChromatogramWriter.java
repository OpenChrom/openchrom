/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.animl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDWriter;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.primitives.Doubles;

import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.Common;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntrySetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ObjectFactory;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.PlotScaleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;
import net.openchrom.xxd.converter.supplier.animl.preferences.PreferenceSupplier;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class ChromatogramWriter extends AbstractChromatogramWSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramWSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			//
			AnIMLType anIML = new AnIMLType();
			anIML.setVersion("0.90");
			anIML.setSampleSet(createSampleSet(chromatogram));
			anIML.setExperimentStepSet(createExperimentStep(chromatogram));
			anIML.setAuditTrailEntrySet(createAuditTrail(chromatogram));
			marshaller.marshal(anIML, file);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(DatatypeConfigurationException e) {
			logger.warn(e);
		}
	}

	private SampleSetType createSampleSet(IChromatogramWSD chromatogram) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setId("OPENCHROM_WSD_EXPORT");
		sample.setName(chromatogram.getHeaderDataOrDefault("Sample Name", chromatogram.getDataName()));
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(FilenameUtils.removeExtension(chromatogram.getFile().getName()));
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IChromatogramWSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		//
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setName("Chromatography");
		chromatographyStep.setExperimentStepID("CHROMATOGRAPHY");
		chromatographyStep.setTechnique(createChromatographyTechnique());
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		//
		ExperimentStepType singleWavelengthStep = new ExperimentStepType();
		singleWavelengthStep.setName("Single Wavelength");
		singleWavelengthStep.setExperimentStepID("SINGLE_WAVELENGTH");
		singleWavelengthStep.getResult().add(createSignal(chromatogram));
		singleWavelengthStep.setMethod(createMethod(chromatogram));
		singleWavelengthStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		singleWavelengthStep.setTechnique(createUltraVioletDetectorTechnique());
		experimentStepSet.getExperimentStep().add(singleWavelengthStep);
		//
		ExperimentStepType multiWavelengthsStep = new ExperimentStepType();
		multiWavelengthsStep.setName("Multi Wavelength");
		multiWavelengthsStep.setExperimentStepID("MULTI_WAVELENGTH");
		multiWavelengthsStep.setResult(createSpectra(chromatogram));
		multiWavelengthsStep.setMethod(createMethod(chromatogram));
		multiWavelengthsStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		multiWavelengthsStep.setTechnique(createDiodeArrayDetectorTechnique());
		experimentStepSet.getExperimentStep().add(multiWavelengthsStep);
		//
		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.setName("Peaks");
		peakStep.setExperimentStepID("PEAKS");
		peakStep.getResult().add(Common.createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		experimentStepSet.getExperimentStep().add(peakStep);
		return experimentStepSet;
	}

	private List<ResultType> createSpectra(IChromatogramWSD chromatogram) {

		List<ResultType> results = new ArrayList<>();
		for(IScan scan : chromatogram.getScans()) {
			IScanWSD scanWSD = (IScanWSD)scan;
			SeriesSetType seriesSet = new SeriesSetType();
			seriesSet.setId("WAVELENGTH_SPECTRUM");
			seriesSet.setName("Spectrum");
			seriesSet.setLength(scanWSD.getNumberOfScanSignals());
			//
			SeriesType wavelengthSeries = new SeriesType();
			wavelengthSeries.setSeriesID("SPECTRUM");
			wavelengthSeries.setName("Spectrum");
			UnitType wavelengthUnit = new UnitType();
			wavelengthUnit.setLabel("Wavelength");
			wavelengthUnit.setQuantity("nm");
			wavelengthSeries.setUnit(wavelengthUnit);
			wavelengthSeries.setDependency(DependencyType.DEPENDENT);
			wavelengthSeries.setSeriesType(ParameterTypeType.FLOAT_32);
			wavelengthSeries.setPlotScale(PlotScaleType.LINEAR);
			//
			SeriesType intensitySeries = new SeriesType();
			intensitySeries.setSeriesID("INTENSITY");
			intensitySeries.setName("Intensity");
			UnitType intensityUnit = new UnitType();
			intensityUnit.setLabel("Absorbance");
			intensityUnit.setQuantity("AU");
			intensitySeries.setUnit(intensityUnit);
			intensitySeries.setDependency(DependencyType.DEPENDENT);
			intensitySeries.setSeriesType(ParameterTypeType.FLOAT_32);
			intensitySeries.setPlotScale(PlotScaleType.LINEAR);
			//
			if(PreferenceSupplier.getChromatogramSaveEncoded()) {
				int scans = scanWSD.getNumberOfScanSignals();
				float[] wavelengths = new float[scans];
				float[] intensities = new float[scans];
				for(int i = 0; i < scans; i++) {
					IScanSignalWSD signal = scanWSD.getScanSignal(i);
					wavelengths[i] = (float)signal.getWavelength();
					intensities[i] = signal.getAbundance();
				}
				EncodedValueSetType encodedWavelengths = new EncodedValueSetType();
				encodedWavelengths.setValue(BinaryReader.encodeArray(wavelengths));
				wavelengthSeries.getEncodedValueSet().add(encodedWavelengths);
				//
				EncodedValueSetType encodedIntensities = new EncodedValueSetType();
				encodedIntensities.setValue(BinaryReader.encodeArray(intensities));
				intensitySeries.getEncodedValueSet().add(encodedIntensities);
			} else {
				IndividualValueSetType wavelengths = new IndividualValueSetType();
				IndividualValueSetType intensities = new IndividualValueSetType();
				for(IScanSignalWSD signal : scanWSD.getScanSignals()) {
					wavelengths.getF().add((float)signal.getWavelength());
					intensities.getF().add(signal.getAbundance());
				}
				wavelengthSeries.getIndividualValueSet().add(wavelengths);
				intensitySeries.getIndividualValueSet().add(intensities);
			}
			seriesSet.getSeries().add(wavelengthSeries);
			seriesSet.getSeries().add(intensitySeries);
			ResultType result = new ResultType();
			result.setSeriesSet(seriesSet);
			results.add(result);
		}
		return results;
	}

	private ResultType createSignal(IChromatogramWSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setId("UV_VIS");
		seriesSet.setName("UV/Vis Trace");
		seriesSet.setLength(chromatogram.getNumberOfScans());
		//
		SeriesType retentionTimeSeries = new SeriesType();
		retentionTimeSeries.setSeriesID("RETENTION_TIME");
		retentionTimeSeries.setName("Retention Time");
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("ms");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		retentionTimeSeries.setDependency(DependencyType.INDEPENDENT);
		retentionTimeSeries.setSeriesType(ParameterTypeType.INT_32);
		//
		SeriesType intensitySeries = new SeriesType();
		UnitType intensityUnit = new UnitType();
		intensityUnit.setLabel("Intensity");
		intensityUnit.setQuantity("arbitrary");
		intensitySeries.setUnit(intensityUnit);
		intensitySeries.setDependency(DependencyType.DEPENDENT);
		intensitySeries.setSeriesType(ParameterTypeType.FLOAT_32);
		//
		int scans = chromatogram.getNumberOfScans();
		if(PreferenceSupplier.getChromatogramSaveEncoded()) {
			int[] retentionTimes = new int[scans];
			float[] abundance = new float[scans];
			for(int i = 1; i <= scans; i++) {
				IScanWSD scan = chromatogram.getSupplierScan(i);
				retentionTimes[i - 1] = scan.getRetentionTime();
				IScanSignalWSD signal = scan.getScanSignal(0);
				abundance[i - 1] = signal.getAbundance();
			}
			EncodedValueSetType encodedRetentionTimes = new EncodedValueSetType();
			encodedRetentionTimes.setValue(BinaryReader.encodeArray(retentionTimes));
			retentionTimeSeries.getEncodedValueSet().add(encodedRetentionTimes);
			//
			EncodedValueSetType encodedIntensities = new EncodedValueSetType();
			encodedIntensities.setValue(BinaryReader.encodeArray(abundance));
			intensitySeries.getEncodedValueSet().add(encodedIntensities);
		} else {
			IndividualValueSetType retentionTimes = new IndividualValueSetType();
			IndividualValueSetType intensities = new IndividualValueSetType();
			for(int i = 1; i <= scans; i++) {
				IScanWSD scan = chromatogram.getSupplierScan(i);
				IScanSignalWSD signal = scan.getScanSignal(0);
				retentionTimes.getI().add(scan.getRetentionTime());
				intensities.getF().add(signal.getAbundance());
			}
			retentionTimeSeries.getIndividualValueSet().add(retentionTimes);
			intensitySeries.getIndividualValueSet().add(intensities);
		}
		seriesSet.getSeries().add(retentionTimeSeries);
		seriesSet.getSeries().add(intensitySeries);
		CategoryType category = new CategoryType();
		category.setName("Acquisition Wavelength");
		ParameterType lowerBounds = new ParameterType();
		ResultType result = new ResultType();
		IScanWSD firstScan = chromatogram.getSupplierScan(1);
		List<IScanSignalWSD> signals = firstScan.getScanSignals();
		double[] wavelengths = signals.stream().mapToDouble(s -> s.getWavelength()).toArray();
		lowerBounds.setName("Lower Bound");
		lowerBounds.getD().add(Doubles.min(wavelengths));
		category.getParameter().add(lowerBounds);
		ParameterType upperBounds = new ParameterType();
		upperBounds.getD().add(Doubles.max(wavelengths));
		upperBounds.setName("Upper Bound");
		result.getCategory().add(category);
		result.setSeriesSet(seriesSet);
		result.setName("UV/VIS");
		return result;
	}

	private MethodType createMethod(IChromatogramWSD chromatogram) {

		AuthorType author = new AuthorType();
		author.setName(chromatogram.getOperator());
		MethodType method = new MethodType();
		method.setAuthor(author);
		method.setSoftware(Common.createSoftware());
		return method;
	}

	private TechniqueType createChromatographyTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("CHROMATOGRAPHY");
		technique.setName("Chromatography");
		technique.setUri("https://github.com/AnIML/techniques/blob/6e30b1e593e6df661a44ae2a9892b6198def0641/chromatography.atdd");
		return technique;
	}

	private TechniqueType createUltraVioletDetectorTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("UV_VIS");
		technique.setName("UV/Vis Trace Detector");
		technique.setUri("https://github.com/AnIML/techniques/blob/995bef06f90afba00a618239f40c6e591a1c0845/uv-vis-trace.atdd");
		return technique;
	}

	private TechniqueType createDiodeArrayDetectorTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("DAD");
		technique.setName("UV/Vis");
		technique.setUri("https://raw.githubusercontent.com/AnIML/techniques/995bef06f90afba00a618239f40c6e591a1c0845/uv-vis.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("CHROMATOGRAPHY_PEAK_TABLE");
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://github.com/AnIML/techniques/blob/d749979fc21322b97c61e2efd6efe60cfa5a29be/chromatography-peak-table.atdd");
		return technique;
	}

	private AuditTrailEntrySetType createAuditTrail(IChromatogramWSD chromatogram) throws DatatypeConfigurationException {

		AuditTrailEntrySetType auditTrailEntrySet = new AuditTrailEntrySetType();
		for(IEditInformation editHistory : chromatogram.getEditHistory()) {
			AuditTrailEntryType auditTrail = new AuditTrailEntryType();
			auditTrail.setSoftware(Common.createSoftware());
			AuthorType author = new AuthorType();
			author.setName(editHistory.getEditor());
			auditTrail.setAuthor(author);
			auditTrail.setComment(editHistory.getDescription());
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(editHistory.getDate());
			auditTrail.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
		}
		return auditTrailEntrySet;
	}
}
