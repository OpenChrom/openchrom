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
package net.openchrom.csd.converter.supplier.animl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.csd.converter.io.AbstractChromatogramCSDWriter;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.Common;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntrySetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;
import net.openchrom.xxd.converter.supplier.animl.preferences.PreferenceSupplier;

public class ChromatogramWriter extends AbstractChromatogramCSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramCSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

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

	private SampleSetType createSampleSet(IChromatogramCSD chromatogram) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setId("OPENCHROM_CSD_EXPORT");
		sample.setName(chromatogram.getHeaderDataOrDefault("Sample Name", chromatogram.getDataName()));
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(FilenameUtils.removeExtension(chromatogram.getFile().getName()));
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IChromatogramCSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		//
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setName("Chromatography");
		chromatographyStep.setExperimentStepID("CHROMATOGRAPHY");
		chromatographyStep.setTechnique(createChromatographyTechnique());
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		//
		ExperimentStepType detectorStep = new ExperimentStepType();
		detectorStep.setName("Detector");
		detectorStep.setExperimentStepID("DETECTOR");
		detectorStep.getResult().add(createResult(chromatogram));
		detectorStep.setMethod(createMethod(chromatogram));
		detectorStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		detectorStep.setTechnique(createFlameIonizationTechnique());
		experimentStepSet.getExperimentStep().add(detectorStep);
		//
		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.setName("Peaks");
		peakStep.setExperimentStepID("PEAKS");
		peakStep.getResult().add(Common.createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		experimentStepSet.getExperimentStep().add(peakStep);
		return experimentStepSet;
	}

	private ResultType createResult(IChromatogramCSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setId("FID");
		seriesSet.setName("FID Trace");
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
		retentionTimeSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		//
		SeriesType totalSignalSeries = new SeriesType();
		totalSignalSeries.setSeriesID("TOTAL_SIGNAL");
		totalSignalSeries.setName("Total Signal");
		UnitType totalSignalUnit = new UnitType();
		totalSignalUnit.setLabel("Total Signal");
		totalSignalUnit.setQuantity("arbitrary");
		totalSignalSeries.setUnit(totalSignalUnit);
		totalSignalSeries.setDependency(DependencyType.DEPENDENT);
		totalSignalSeries.setSeriesType(ParameterTypeType.FLOAT_32);
		//
		if(PreferenceSupplier.getChromatogramSaveEncoded()) {
			int scans = chromatogram.getNumberOfScans();
			float[] retentionTimes = new float[scans];
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
				retentionTimes.getF().add((float)scan.getRetentionTime());
				totalSignals.getF().add(scan.getTotalSignal());
			}
			retentionTimeSeries.getIndividualValueSet().add(retentionTimes);
			totalSignalSeries.getIndividualValueSet().add(totalSignals);
		}
		seriesSet.getSeries().add(retentionTimeSeries);
		seriesSet.getSeries().add(totalSignalSeries);
		ResultType result = new ResultType();
		result.setName("FID");
		result.setSeriesSet(seriesSet);
		return result;
	}

	private MethodType createMethod(IChromatogramCSD chromatogram) {

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

	private TechniqueType createFlameIonizationTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("FID");
		technique.setName("Flame Ionization Detector");
		technique.setUri("https://github.com/AnIML/techniques/blob/6e30b1e593e6df661a44ae2a9892b6198def0641/fid-trace.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setId("CHROMATOGRAPHY_PEAK_TABLE");
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://github.com/AnIML/techniques/blob/d749979fc21322b97c61e2efd6efe60cfa5a29be/chromatography-peak-table.atdd");
		return technique;
	}

	private AuditTrailEntrySetType createAuditTrail(IChromatogramCSD chromatogram) throws DatatypeConfigurationException {

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
