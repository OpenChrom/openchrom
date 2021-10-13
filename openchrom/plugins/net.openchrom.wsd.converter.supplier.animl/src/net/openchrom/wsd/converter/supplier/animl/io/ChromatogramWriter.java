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
package net.openchrom.wsd.converter.supplier.animl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.support.history.IEditInformation;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDWriter;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Version;

import com.google.common.primitives.Doubles;

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

public class ChromatogramWriter extends AbstractChromatogramWSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramWSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

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

	private SampleSetType createSampleSet(IChromatogramWSD chromatogram) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setName(chromatogram.getDataName());
		sample.setBarcode(chromatogram.getBarcode());
		sample.setComment(chromatogram.getMiscInfo());
		sample.setSampleID(chromatogram.getDetailedInfo());
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IChromatogramWSD chromatogram) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		//
		ExperimentStepType chromatographyStep = new ExperimentStepType();
		chromatographyStep.setTechnique(createChromatographyTechnique());
		experimentStepSet.getExperimentStep().add(chromatographyStep);
		//
		ExperimentStepType detectorStep = new ExperimentStepType();
		detectorStep.getResult().add(createSignal(chromatogram));
		detectorStep.setMethod(createMethod(chromatogram));
		detectorStep.setSourceDataLocation(chromatogram.getFile().getAbsolutePath());
		detectorStep.setTechnique(createUltraVioletDetectorTechnique());
		experimentStepSet.getExperimentStep().add(detectorStep);
		//
		ExperimentStepType peakStep = new ExperimentStepType();
		peakStep.getResult().add(createPeaks(chromatogram));
		peakStep.setTechnique(createPeakTableTechnique());
		experimentStepSet.getExperimentStep().add(peakStep);
		return experimentStepSet;
	}

	private ResultType createSignal(IChromatogramWSD chromatogram) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("UV/Vis Trace");
		seriesSet.setLength(chromatogram.getNumberOfScans());
		//
		SeriesType retentionTimeSeries = new SeriesType();
		UnitType retentionTimeUnit = new UnitType();
		retentionTimeUnit.setLabel("Time");
		retentionTimeUnit.setQuantity("ms");
		retentionTimeSeries.setUnit(retentionTimeUnit);
		//
		SeriesType intensitySeries = new SeriesType();
		UnitType intensityUnit = new UnitType();
		intensityUnit.setLabel("Intensity");
		intensityUnit.setQuantity("arbitrary");
		intensitySeries.setUnit(intensityUnit);
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
		return result;
	}

	private ResultType createPeaks(IChromatogramWSD chromatogram) {

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
		for(IChromatogramPeakWSD peak : chromatogram.getPeaks()) {
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

	private MethodType createMethod(IChromatogramWSD chromatogram) {

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

	private TechniqueType createUltraVioletDetectorTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("UV/Vis Trace Detector");
		technique.setUri("https://github.com/AnIML/techniques/blob/995bef06f90afba00a618239f40c6e591a1c0845/uv-vis-trace.atdd");
		return technique;
	}

	private TechniqueType createPeakTableTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Chromatography Peak Table");
		technique.setUri("https://github.com/AnIML/techniques/blob/d749979fc21322b97c61e2efd6efe60cfa5a29be/chromatography-peak-table.atdd");
		return technique;
	}

	private AuditTrailEntrySetType createAuditTrail(IChromatogramWSD chromatogram) throws DatatypeConfigurationException {

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