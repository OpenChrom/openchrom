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
package net.openchrom.msd.converter.supplier.animl.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;
import net.openchrom.xxd.converter.supplier.animl.preferences.PreferenceSupplier;

public class ChromatogramWriter extends AbstractChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AnIMLType.class);
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

	private SampleSetType createSampleSet(IChromatogramMSD chromatogram) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setId("OPENCHROM_MSD_EXPORT");
		sample.setName(chromatogram.getHeaderDataOrDefault("Sample Name", chromatogram.getDataName()));
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(FilenameUtils.removeExtension(chromatogram.getFile().getName()));
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IChromatogramMSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		//
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setTechnique(createChromatographyTechnique());
		chromatographyStep.setName("Chromatography");
		chromatographyStep.setId("CHROMATOGRAPHY");
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		//
		ExperimentStepType totalSignalStep = new ExperimentStepType();
		totalSignalStep.setName("Total Signal");
		totalSignalStep.setId("TOTAL_SIGNAL");
		totalSignalStep.getResult().add(createTIC(chromatogram));
		totalSignalStep.setMethod(createMethod(chromatogram));
		totalSignalStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		totalSignalStep.setTechnique(createTotalIonCurrentTechnique());
		experimentStepSet.getExperimentStep().add(totalSignalStep);
		//
		ExperimentStepType detectorStep = new ExperimentStepType();
		detectorStep.setName("Detector");
		detectorStep.setId("DETECTOR");
		detectorStep.setResult(createMS(chromatogram));
		detectorStep.setMethod(createMethod(chromatogram));
		detectorStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		detectorStep.setTechnique(createMassSpectrometryTechnique());
		experimentStepSet.getExperimentStep().add(detectorStep);
		//
		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.setName("Peaks");
		peakStep.setId("PEAKS");
		peakStep.getResult().add(Common.createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		experimentStepSet.getExperimentStep().add(peakStep);
		return experimentStepSet;
	}

	private List<ResultType> createMS(IChromatogramMSD chromatogram) {

		List<ResultType> results = new ArrayList<>();
		for(IScan scan : chromatogram.getScans()) {
			IScanMSD scanMSD = (IScanMSD)scan;
			SeriesSetType seriesSet = new SeriesSetType();
			seriesSet.setId("MASS_SPECTRUM");
			seriesSet.setName("Spectrum");
			seriesSet.setLength(scanMSD.getNumberOfIons());
			//
			SeriesType massChargeSeries = new SeriesType();
			massChargeSeries.setSeriesID("MASS_CHARGE");
			massChargeSeries.setName("Mass/Charge");
			UnitType massChargeUnit = new UnitType();
			massChargeUnit.setLabel("Mass/Charge Ratio");
			massChargeUnit.setQuantity("mz");
			massChargeSeries.setUnit(massChargeUnit);
			massChargeSeries.setDependency(DependencyType.DEPENDENT);
			massChargeSeries.setSeriesType(ParameterTypeType.FLOAT_64);
			//
			SeriesType intensitySeries = new SeriesType();
			intensitySeries.setSeriesID("INTENSITY");
			intensitySeries.setName("Intensity");
			UnitType intensityUnit = new UnitType();
			intensityUnit.setLabel("Abundance");
			intensityUnit.setQuantity("arbitrary");
			intensitySeries.setUnit(intensityUnit);
			intensitySeries.setDependency(DependencyType.DEPENDENT);
			intensitySeries.setSeriesType(ParameterTypeType.FLOAT_32);
			//
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
			result.setSeriesSet(seriesSet);
			results.add(result);
		}
		return results;
	}

	private ResultType createTIC(IChromatogramMSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setId("TIC");
		seriesSet.setName("Mass Chromatogram");
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
		SeriesType totalSignalSeries = new SeriesType();
		totalSignalSeries.setSeriesID("TOTAL_SIGNAL");
		totalSignalSeries.setName("Total Signal");
		UnitType totalSignalUnit = new UnitType();
		totalSignalUnit.setLabel("Abundance");
		totalSignalUnit.setQuantity("arbitrary");
		totalSignalSeries.setUnit(totalSignalUnit);
		totalSignalSeries.setDependency(DependencyType.DEPENDENT);
		totalSignalSeries.setSeriesType(ParameterTypeType.FLOAT_32);
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
		technique.setId("CHROMATOGRAPHY");
		technique.setName("Chromatography");
		technique.setUri("https://github.com/AnIML/techniques/blob/6e30b1e593e6df661a44ae2a9892b6198def0641/chromatography.atdd");
		return technique;
	}

	private TechniqueType createTotalIonCurrentTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("MS_TRACE");
		technique.setName("Mass Spectrum Time Trace");
		technique.setUri("https://github.com/AnIML/techniques/blob/2c9e7fbf5f6435b4dee9f112bdd0d81d135085ff/ms-trace.atdd");
		return technique;
	}

	private TechniqueType createMassSpectrometryTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("MASS_SPEC");
		technique.setName("Mass Spectrometry");
		technique.setUri("https://github.com/AnIML/techniques/blob/db3fcd831c4fce00b647b7e6d8c21f753b76f361/mass-spec.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("PEAKS");
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://github.com/AnIML/techniques/blob/d749979fc21322b97c61e2efd6efe60cfa5a29be/chromatography-peak-table.atdd");
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