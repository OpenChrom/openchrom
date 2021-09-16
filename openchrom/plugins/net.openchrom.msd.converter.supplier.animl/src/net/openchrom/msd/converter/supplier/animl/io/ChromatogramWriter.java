/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.msd.converter.io.AbstractChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntrySetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SoftwareType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;
import net.openchrom.xxd.converter.supplier.animl.preferences.PreferenceSupplier;

public class ChromatogramWriter extends AbstractChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AnIMLType.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			//
			AnIMLType anIML = new AnIMLType();
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
		sample.setName(chromatogram.getDataName());
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(chromatogram.getDetailedInfo());
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IChromatogramMSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		//
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setTechnique(createChromatographyTechnique());
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		//
		ExperimentStepType detectorStep = new ExperimentStepType();
		detectorStep.getResult().add(createTIC(chromatogram));
		detectorStep.setMethod(createMethod(chromatogram));
		detectorStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		detectorStep.setTechnique(createMassSpectrometryTechnique());
		experimentStepSet.getExperimentStep().add(detectorStep);
		//
		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.getResult().add(createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		experimentStepSet.getExperimentStep().add(peakStep);
		return experimentStepSet;
	}

	private ResultType createTIC(IChromatogramMSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("Mass Chromatogram");
		seriesSet.setLength(chromatogram.getNumberOfScans());
		//
		SeriesType retentionTimeSeries = new SeriesType();
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("ms");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		//
		SeriesType totalSignalSeries = new SeriesType();
		UnitType totalSignalUnit = new UnitType();
		totalSignalUnit.setLabel("Abundance");
		totalSignalUnit.setQuantity("arbitrary");
		totalSignalSeries.setUnit(totalSignalUnit);
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

	private ResultType createPeaks(IChromatogramMSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("Peak Table");
		seriesSet.setLength(chromatogram.getNumberOfPeaks());
		//
		SeriesType numberSeries = new SeriesType();
		numberSeries.setName("Number");
		//
		SeriesType idSeries = new SeriesType();
		idSeries.setName("ID");
		//
		SeriesType groupIdSeries = new SeriesType();
		groupIdSeries.setName("Group ID");
		//
		SeriesType nameSeries = new SeriesType();
		nameSeries.setName("Name");
		//
		SeriesType retentionTimeSeries = new SeriesType();
		retentionTimeSeries.setName("Retention Time");
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("min");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		//
		SeriesType adjustedRetentionTimeSeries = new SeriesType();
		adjustedRetentionTimeSeries.setName("Adjusted Retention Time");
		UnitType adjustedRetentionTimeUnit = new UnitType();
		adjustedRetentionTimeUnit.setLabel("Time");
		adjustedRetentionTimeUnit.setQuantity("min");
		adjustedRetentionTimeSeries.setUnit(adjustedRetentionTimeUnit);
		//
		SeriesType startTimeSeries = new SeriesType();
		startTimeSeries.setName("Start Time");
		UnitType startTimeUnit = new UnitType();
		startTimeUnit.setLabel("Time");
		startTimeUnit.setQuantity("min");
		startTimeSeries.setUnit(startTimeUnit);
		//
		SeriesType endTimeSeries = new SeriesType();
		endTimeSeries.setName("End Time");
		UnitType endTimeUnit = new UnitType();
		endTimeUnit.setLabel("Time");
		endTimeUnit.setQuantity("min");
		endTimeSeries.setUnit(endTimeUnit);
		//
		SeriesType peakHeightSeries = new SeriesType();
		peakHeightSeries.setName("Value");
		UnitType peakHeightUnit = new UnitType();
		peakHeightUnit.setLabel("Arbitrary");
		peakHeightUnit.setQuantity("arbitrary");
		peakHeightSeries.setUnit(peakHeightUnit);
		//
		IndividualValueSetType numbers = new IndividualValueSetType();
		IndividualValueSetType ids = new IndividualValueSetType();
		IndividualValueSetType groups = new IndividualValueSetType();
		IndividualValueSetType names = new IndividualValueSetType();
		IndividualValueSetType retentionTimes = new IndividualValueSetType();
		IndividualValueSetType adjustedRetentionTimes = new IndividualValueSetType();
		IndividualValueSetType startTimes = new IndividualValueSetType();
		IndividualValueSetType endTimes = new IndividualValueSetType();
		IndividualValueSetType peakHeights = new IndividualValueSetType();
		int i = 0;
		for(IChromatogramPeakMSD peak : chromatogram.getPeaks()) {
			numbers.getI().add(i);
			Optional<IIdentificationTarget> target = peak.getTargets().stream().findFirst();
			if(target.isPresent()) {
				ids.getS().add(target.get().getIdentifier());
				Optional<String> classifier = target.get().getLibraryInformation().getClassifier().stream().findFirst();
				if(classifier.isPresent()) {
					groups.getS().add(classifier.get());
				}
				names.getS().add(target.get().getLibraryInformation().getName());
			}
			retentionTimes.getD().add(peak.getX() / 1000 / 60);
			float relativeRetentionTime = chromatogram.getScan(peak.getScanMax()).getRelativeRetentionTime() / 1000f / 60f;
			if(relativeRetentionTime > 0) {
				adjustedRetentionTimes.getF().add(relativeRetentionTime);
			}
			startTimes.getF().add(peak.getPeakModel().getStartRetentionTime() / 1000f / 60f);
			endTimes.getF().add(peak.getPeakModel().getStopRetentionTime() / 1000f / 60f);
			peakHeights.getF().add(peak.getPeakModel().getPeakMaximum().getTotalSignal());
			i++;
		}
		numberSeries.getIndividualValueSet().add(numbers);
		idSeries.getIndividualValueSet().add(ids);
		groupIdSeries.getIndividualValueSet().add(groups);
		nameSeries.getIndividualValueSet().add(names);
		retentionTimeSeries.getIndividualValueSet().add(retentionTimes);
		adjustedRetentionTimeSeries.getIndividualValueSet().add(adjustedRetentionTimes);
		startTimeSeries.getIndividualValueSet().add(startTimes);
		endTimeSeries.getIndividualValueSet().add(endTimes);
		peakHeightSeries.getIndividualValueSet().add(peakHeights);
		//
		seriesSet.getSeries().add(numberSeries);
		seriesSet.getSeries().add(idSeries);
		seriesSet.getSeries().add(groupIdSeries);
		seriesSet.getSeries().add(nameSeries);
		seriesSet.getSeries().add(retentionTimeSeries);
		seriesSet.getSeries().add(adjustedRetentionTimeSeries);
		seriesSet.getSeries().add(startTimeSeries);
		seriesSet.getSeries().add(endTimeSeries);
		seriesSet.getSeries().add(peakHeightSeries);
		//
		ResultType result = new ResultType();
		result.setSeriesSet(seriesSet);
		return result;
	}

	private MethodType createMethod(IChromatogramMSD chromatogram) {

		AuthorType author = new AuthorType();
		author.setName(chromatogram.getOperator());
		MethodType method = new MethodType();
		method.setAuthor(author);
		method.setSoftware(createSoftware());
		return method;
	}

	private SoftwareType createSoftware() {

		SoftwareType software = new SoftwareType();
		software.setName("OpenChrom");
		IProduct product = Platform.getProduct();
		Version version = product.getDefiningBundle().getVersion();
		software.setVersion(version.toString());
		software.setManufacturer("Lablicate GmbH");
		software.setOperatingSystem(System.getProperty("os.name"));
		return software;
	}

	private TechniqueType createChromatographyTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Chromatography");
		technique.setUri("https://github.com/AnIML/techniques/blob/6e30b1e593e6df661a44ae2a9892b6198def0641/chromatography.atdd");
		return technique;
	}

	private TechniqueType createMassSpectrometryTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Mass Spectrum Time Trace");
		technique.setUri("https://github.com/AnIML/techniques/blob/2c9e7fbf5f6435b4dee9f112bdd0d81d135085ff/ms-trace.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://github.com/AnIML/techniques/blob/d749979fc21322b97c61e2efd6efe60cfa5a29be/chromatography-peak-table.atdd");
		return technique;
	}

	private AuditTrailEntrySetType createAuditTrail(IChromatogramMSD chromatogram) throws DatatypeConfigurationException {

		AuditTrailEntrySetType auditTrailEntrySet = new AuditTrailEntrySetType();
		for(IEditInformation editHistory : chromatogram.getEditHistory()) {
			AuditTrailEntryType auditTrail = new AuditTrailEntryType();
			auditTrail.setSoftware(createSoftware());
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