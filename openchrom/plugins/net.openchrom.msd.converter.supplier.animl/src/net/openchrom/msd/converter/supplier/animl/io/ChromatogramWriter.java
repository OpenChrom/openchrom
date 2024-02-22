/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.animl.io;

import java.io.File;
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
import org.eclipse.chemclipse.msd.converter.io.AbstractChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.converter.supplier.animl.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.converter.Common;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuditTrailEntrySetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ExperimentStepSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ObjectFactory;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.PlotScaleType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SampleSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.UnitType;
import net.openchrom.xxd.converter.supplier.animl.preferences.PreferenceSupplier;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class ChromatogramWriter extends AbstractChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(createAnIML(chromatogram), file);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(DatatypeConfigurationException e) {
			logger.warn(e);
		}
	}

	private AnIMLType createAnIML(IChromatogramMSD chromatogram) throws DatatypeConfigurationException {

		AnIMLType anIML = new AnIMLType();
		anIML.setVersion("0.90");
		anIML.setSampleSet(createSampleSet(chromatogram));
		anIML.setExperimentStepSet(createChromatographyExperimentStep(chromatogram));
		anIML.setAuditTrailEntrySet(createAuditTrail(chromatogram));
		return anIML;
	}

	private SampleSetType createSampleSet(IChromatogramMSD chromatogram) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setName(chromatogram.getSampleName());
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(FilenameUtils.removeExtension(chromatogram.getFile().getName()));
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createChromatographyExperimentStep(IChromatogramMSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setTechnique(createChromatographyTechnique());
		chromatographyStep.setName("Q1");
		chromatographyStep.setExperimentStepID("1");
		chromatographyStep.setResult(createSeparationMonitoring(chromatogram));
		chromatographyStep.setMethod(createMethod(chromatogram));
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		return experimentStepSet;
	}

	private List<ResultType> createSeparationMonitoring(IChromatogramMSD chromatogram) {

		List<ResultType> results = new ArrayList<>();
		ResultType result = new ResultType();
		result.setName("Separation Monitoring");
		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("Separation Monitoring");
		seriesSet.getSeries().add(createRetentionTimeSeries(chromatogram));
		result.setSeriesSet(seriesSet);
		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		experimentStepSet.getExperimentStep().add(createTotalIonCurrentExperiment(chromatogram));
		experimentStepSet.getExperimentStep().addAll(createMassSpectrumExperimentStep(chromatogram));
		experimentStepSet.getExperimentStep().add(createPeakTableExperimentStep(chromatogram));
		result.setExperimentStepSet(experimentStepSet);
		results.add(result);
		return results;
	}

	private SeriesType createRetentionTimeSeries(IChromatogramMSD chromatogram) {

		SeriesType series = new SeriesType();
		series.setName("Time");
		series.setId("timeSeries");
		series.setUnit(Common.createMilisecond());
		series.setDependency(DependencyType.INDEPENDENT);
		series.setPlotScale(PlotScaleType.LINEAR);
		series.setSeriesType(ParameterTypeType.FLOAT_32);
		EncodedValueSetType encodedValueSet = new EncodedValueSetType();
		encodedValueSet.setValue(null);
		float[] retentionTimes = new float[chromatogram.getNumberOfScans()];
		for(int i = 1; i < retentionTimes.length; i++) {
			retentionTimes[i] = chromatogram.getScan(i).getRetentionTime();
		}
		EncodedValueSetType encodedIntensities = new EncodedValueSetType();
		encodedIntensities.setValue(BinaryReader.encodeArray(retentionTimes));
		series.getEncodedValueSet().add(encodedIntensities);
		return series;
	}

	private ExperimentStepType createTotalIonCurrentExperiment(IChromatogramMSD chromatogram) {

		ExperimentStepType totalSignalStep = new ExperimentStepType();
		totalSignalStep.setName("Total Signal");
		totalSignalStep.getResult().add(createTotalIonCurrentResult(chromatogram));
		totalSignalStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		totalSignalStep.setTechnique(createMassSpectrumTraceTechnique());
		return totalSignalStep;
	}

	private ResultType createTotalIonCurrentResult(IChromatogramMSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setId("TIC");
		seriesSet.setName("Mass Chromatogram");
		seriesSet.setLength(chromatogram.getNumberOfScans());
		//
		SeriesType retentionTimeSeries = new SeriesType();
		retentionTimeSeries.setName("Retention Time");
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("ms");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		retentionTimeSeries.setDependency(DependencyType.INDEPENDENT);
		retentionTimeSeries.setSeriesType(ParameterTypeType.INT_32);
		retentionTimeSeries.setPlotScale(PlotScaleType.LINEAR);
		//
		SeriesType totalSignalSeries = new SeriesType();
		totalSignalSeries.setName("Total Signal");
		UnitType totalSignalUnit = new UnitType();
		totalSignalUnit.setLabel("Abundance");
		totalSignalUnit.setQuantity("arbitrary");
		totalSignalSeries.setUnit(totalSignalUnit);
		totalSignalSeries.setDependency(DependencyType.DEPENDENT);
		totalSignalSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		totalSignalSeries.setPlotScale(PlotScaleType.LINEAR);
		//
		if(PreferenceSupplier.getChromatogramSaveEncoded()) {
			int scans = chromatogram.getNumberOfScans();
			int[] retentionTimes = new int[scans];
			float[] totalSignals = new float[scans];
			int i = 0;
			for(IScan scan : chromatogram.getScans()) {
				retentionTimes[i] = scan.getRetentionTime();
				totalSignals[i] = scan.getTotalSignal();
				i++;
			}
			EncodedValueSetType encodedRetentionTimes = new EncodedValueSetType();
			encodedRetentionTimes.setValue(BinaryReader.encodeArray(retentionTimes));
			retentionTimeSeries.getEncodedValueSet().add(encodedRetentionTimes);
			//
			EncodedValueSetType encodedTotalSignals = new EncodedValueSetType();
			encodedTotalSignals.setValue(BinaryReader.encodeArray(totalSignals));
			totalSignalSeries.getEncodedValueSet().add(encodedTotalSignals);
		} else {
			IndividualValueSetType retentionTimes = new IndividualValueSetType();
			IndividualValueSetType totalSignals = new IndividualValueSetType();
			for(IScan scan : chromatogram.getScans()) {
				retentionTimes.getI().add(scan.getRetentionTime());
				totalSignals.getF().add(scan.getTotalSignal());
			}
			retentionTimeSeries.getIndividualValueSet().add(retentionTimes);
			totalSignalSeries.getIndividualValueSet().add(totalSignals);
		}
		seriesSet.getSeries().add(retentionTimeSeries);
		seriesSet.getSeries().add(totalSignalSeries);
		ResultType result = new ResultType();
		CategoryType category = new CategoryType();
		ParameterType parameter = new ParameterType();
		parameter.getS().add("TIC");
		category.getParameter().add(parameter);
		result.getCategory().add(category);
		result.setSeriesSet(seriesSet);
		return result;
	}

	private List<ExperimentStepType> createMassSpectrumExperimentStep(IChromatogramMSD chromatogram) {

		List<ExperimentStepType> experimentSteps = new ArrayList<>();
		for(IScan scan : chromatogram.getScans()) {
			IScanMSD scanMSD = (IScanMSD)scan;
			SeriesSetType seriesSet = new SeriesSetType();
			seriesSet.setName("Spectrum");
			seriesSet.setLength(scanMSD.getNumberOfIons());
			SeriesType massChargeSeries = createMassChargeSeries();
			SeriesType intensitySeries = createIntensitySeries();
			if(PreferenceSupplier.getChromatogramSaveEncoded()) {
				int scans = scanMSD.getNumberOfIons();
				double[] ions = new double[scans];
				float[] intensities = new float[scans];
				int i = 0;
				for(IIon ion : scanMSD.getIons()) {
					ions[i] = ion.getIon();
					intensities[i] = ion.getAbundance();
					i++;
				}
				EncodedValueSetType encodedIons = new EncodedValueSetType();
				encodedIons.setValue(BinaryReader.encodeArray(ions));
				massChargeSeries.getEncodedValueSet().add(encodedIons);
				//
				EncodedValueSetType encodedIntensities = new EncodedValueSetType();
				encodedIntensities.setValue(BinaryReader.encodeArray(intensities));
				intensitySeries.getEncodedValueSet().add(encodedIntensities);
			} else {
				IndividualValueSetType ions = new IndividualValueSetType();
				IndividualValueSetType intensities = new IndividualValueSetType();
				for(IIon ion : scanMSD.getIons()) {
					ions.getD().add(ion.getIon());
					intensities.getF().add(ion.getAbundance());
				}
				massChargeSeries.getIndividualValueSet().add(ions);
				intensitySeries.getIndividualValueSet().add(intensities);
			}
			seriesSet.getSeries().add(massChargeSeries);
			seriesSet.getSeries().add(intensitySeries);
			ResultType result = new ResultType();
			result.setName("Spectrum");
			result.setSeriesSet(seriesSet);
			ExperimentStepType experimentStep = new ExperimentStepType();
			experimentStep.setExperimentStepID("ms-" + scan.getScanNumber());
			experimentStep.setName("MS #" + scan.getScanNumber());
			experimentStep.setTechnique(createMassSpectrometryTechnique());
			experimentStep.getResult().add(result);
			experimentSteps.add(experimentStep);
		}
		return experimentSteps;
	}

	private ExperimentStepType createPeakTableExperimentStep(IChromatogramMSD chromatogram) {

		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.setName("Peak Table");
		peakStep.getResult().add(Common.createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		return peakStep;
	}

	private SeriesType createMassChargeSeries() {

		SeriesType massChargeSeries = new SeriesType();
		massChargeSeries.setName("Mass/Charge");
		UnitType massChargeUnit = new UnitType();
		massChargeUnit.setLabel("Mass/Charge Ratio");
		massChargeUnit.setQuantity("mz");
		massChargeSeries.setUnit(massChargeUnit);
		massChargeSeries.setDependency(DependencyType.DEPENDENT);
		massChargeSeries.setSeriesType(ParameterTypeType.FLOAT_64);
		massChargeSeries.setPlotScale(PlotScaleType.LINEAR);
		return massChargeSeries;
	}

	private SeriesType createIntensitySeries() {

		SeriesType intensitySeries = new SeriesType();
		intensitySeries.setName("Intensity");
		UnitType intensityUnit = new UnitType();
		intensityUnit.setLabel("Abundance");
		intensityUnit.setQuantity("arbitrary");
		intensitySeries.setUnit(intensityUnit);
		intensitySeries.setDependency(DependencyType.DEPENDENT);
		intensitySeries.setSeriesType(ParameterTypeType.FLOAT_32);
		intensitySeries.setPlotScale(PlotScaleType.LINEAR);
		return intensitySeries;
	}

	private MethodType createMethod(IChromatogramMSD chromatogram) {

		AuthorType author = new AuthorType();
		author.setName(chromatogram.getOperator());
		MethodType method = new MethodType();
		method.setAuthor(author);
		method.setSoftware(Common.createSoftware());
		return method;
	}

	private TechniqueType createChromatographyTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Chromatography");
		technique.setUri("https://animl.openchrom.net/chromatography.atdd");
		return technique;
	}

	private TechniqueType createMassSpectrumTraceTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Mass Spectrum Time Trace");
		technique.setUri("https://animl.openchrom.net/ms-trace.atdd");
		return technique;
	}

	private TechniqueType createMassSpectrometryTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Mass Spectrometry");
		technique.setUri("https://animl.openchrom.net/mass-spec.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://animl.openchrom.net/chromatography-peak-table.atdd");
		return technique;
	}

	private AuditTrailEntrySetType createAuditTrail(IChromatogramMSD chromatogram) throws DatatypeConfigurationException {

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
