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
package net.openchrom.wsd.converter.supplier.animl.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.support.PeakBuilderWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;

import net.openchrom.wsd.converter.supplier.animl.converter.FileContentMatcher;
import net.openchrom.wsd.converter.supplier.animl.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.animl.model.IVendorScanSignalWSD;
import net.openchrom.wsd.converter.supplier.animl.model.VendorChromatogram;
import net.openchrom.wsd.converter.supplier.animl.model.VendorScan;
import net.openchrom.wsd.converter.supplier.animl.model.VendorScanSignalWSD;
import net.openchrom.xxd.converter.supplier.animl.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.converter.XmlReader;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AutoIncrementedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.TechniqueType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.UnitType;

import jakarta.xml.bind.JAXBException;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		try {
			chromatogram = new VendorChromatogram();
			chromatogram.setFile(file);
			AnIMLType animl = XmlReader.getAnIML(file);
			chromatogram.getEditHistory().addAll(XmlReader.readAuditTrail(animl));
			chromatogram = readSample(animl, chromatogram);
			for(ExperimentStepType experimentStep : animl.getExperimentStepSet().getExperimentStep()) {
				TechniqueType technique = experimentStep.getTechnique();
				if(technique == null) {
					continue;
				}
				IVendorChromatogram referencedChromatogram = null;
				if(chromatogram.getNumberOfScans() > 0) {
					referencedChromatogram = new VendorChromatogram();
				}
				if(FileContentMatcher.isMatchingTechnique(technique.getUri())) {
					MethodType method = experimentStep.getMethod();
					if(method != null) {
						AuthorType author = method.getAuthor();
						if(referencedChromatogram != null) {
							referencedChromatogram.setOperator(author.getName());
						} else {
							chromatogram.setOperator(author.getName());
						}
					}
					for(ResultType result : experimentStep.getResult()) {
						SeriesSetType seriesSet = result.getSeriesSet();
						if(seriesSet.getName().equals("Intensity")) {
							if(referencedChromatogram != null) {
								readValueSets(method, result, seriesSet, referencedChromatogram);
							} else {
								readValueSets(method, result, seriesSet, chromatogram);
							}
						}
					}
				}
				if(referencedChromatogram != null) {
					readSpectra(experimentStep, referencedChromatogram);
					readPeakTable(experimentStep, referencedChromatogram);
				} else {
					readSpectra(experimentStep, chromatogram);
					readPeakTable(experimentStep, chromatogram);
				}
				if(referencedChromatogram != null && referencedChromatogram.getNumberOfScans() > 0) {
					chromatogram.addReferencedChromatogram(referencedChromatogram);
				}
			}
		} catch(SAXException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		}
		return chromatogram;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		IVendorChromatogram chromatogram = null;
		try {
			AnIMLType animl = XmlReader.getAnIML(file);
			chromatogram = new VendorChromatogram();
			chromatogram = readSample(animl, chromatogram);
		} catch(SAXException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		}
		return chromatogram;
	}

	private IVendorChromatogram readSample(AnIMLType animl, IVendorChromatogram chromatogram) {

		SampleType sample = animl.getSampleSet().getSample().get(0);
		chromatogram.setSampleName(sample.getName());
		chromatogram.setBarcode(sample.getBarcode());
		chromatogram.setDetailedInfo(sample.getSampleID());
		chromatogram.setMiscInfo(sample.getComment());
		return chromatogram;
	}

	private double readWavelength(MethodType method, ResultType result) {

		double wavelength = 0d;
		if(method != null) {
			for(CategoryType category : method.getCategory()) {
				if(category.getName().equals("Extraction Parameters")) {
					for(ParameterType parameter : category.getParameter()) {
						if(parameter.getParameterType() == ParameterTypeType.FLOAT_32) {
							wavelength = parameter.getF().get(0);
						} else if(parameter.getParameterType() == ParameterTypeType.FLOAT_64) {
							wavelength = parameter.getD().get(0);
						}
					}
				}
			}
		}
		for(CategoryType category : result.getCategory()) {
			if(category.getName().equals("Acquisition Wavelength")) {
				double lower = 0d;
				double upper = 0d;
				for(ParameterType parameter : category.getParameter()) {
					if(parameter.getName().equals("Lower Bound")) {
						lower = parameter.getD().get(0);
					}
					if(parameter.getName().equals("Upper Bound")) {
						upper = parameter.getD().get(0);
					}
				}
				if(lower != 0 && upper != 0) {
					wavelength = Math.round((lower + upper) / 2);
				} else {
					wavelength = Math.max(lower, upper);
				}
			}
		}
		return wavelength;
	}

	private void readValueSets(MethodType method, ResultType result, SeriesSetType seriesSet, IVendorChromatogram chromatogram) {

		List<Integer> retentionTimes = new ArrayList<>();
		List<Float> intensities = new ArrayList<>();
		for(SeriesType series : seriesSet.getSeries()) {
			UnitType unit = series.getUnit();
			if(unit.getQuantity() != null && unit.getQuantity().equals("Time")) {
				int multiplicator = XmlReader.getTimeMultiplicator(unit);
				for(AutoIncrementedValueSetType autoIncrementedValueSet : series.getAutoIncrementedValueSet()) {
					Double start = autoIncrementedValueSet.getStartValue().getD().get(0);
					chromatogram.setScanDelay((int)Math.round(start * multiplicator));
					Double increment = autoIncrementedValueSet.getIncrement().getD().get(0);
					chromatogram.setScanInterval((int)Math.round(increment * multiplicator));
				}
				for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
					for(float f : individualValueSet.getF()) {
						retentionTimes.add(Math.round(multiplicator * f));
					}
					for(int i : individualValueSet.getI()) {
						retentionTimes.add(multiplicator * i);
					}
				}
				for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
					int[] decodedValues = BinaryReader.decodeIntArray(encodedValueSet.getValue());
					for(int i : decodedValues) {
						retentionTimes.add(multiplicator * i);
					}
				}
			}
			if(series.getName().equals("Absorbance")) {
				for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
					if(series.getSeriesType() == ParameterTypeType.FLOAT_32) {
						intensities.addAll(individualValueSet.getF());
					} else if(series.getSeriesType() == ParameterTypeType.FLOAT_64) {
						for(Double absorbance : individualValueSet.getD()) {
							intensities.add(absorbance.floatValue());
						}
					}
				}
				for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
					if(series.getSeriesType() == ParameterTypeType.FLOAT_32) {
						Float[] decodedValues = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
						for(Float f : decodedValues) {
							intensities.add(f);
						}
					} else if(series.getSeriesType() == ParameterTypeType.FLOAT_64) {
						Double[] decodedValues = BinaryReader.decodeDoubleArray(encodedValueSet.getValue());
						for(Double d : decodedValues) {
							intensities.add(d.floatValue());
						}
					}
				}
			}
		}
		double wavelength = readWavelength(method, result);
		for(int i = 0; i < seriesSet.getLength(); i++) {
			VendorScan scan = new VendorScan();
			IVendorScanSignalWSD signal = new VendorScanSignalWSD();
			signal.setAbundance(intensities.get(i));
			signal.setWavelength((float)wavelength);
			scan.addScanSignal(signal);
			if(!retentionTimes.isEmpty()) {
				scan.setRetentionTime(Math.round(retentionTimes.get(i)));
			}
			chromatogram.addScan(scan);
		}
		chromatogram.recalculateRetentionTimes();
	}

	private void readSpectra(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		int spectra = 0;
		Float[] wavelengths = null;
		Float[] aborbances = null;
		if(experimentStep.getTechnique().getName().equals("UV/Vis")) {
			for(ResultType result : experimentStep.getResult()) {
				SeriesSetType seriesSet = result.getSeriesSet();
				if(seriesSet.getName().equals("Spectrum")) {
					spectra++;
					int length = seriesSet.getLength();
					wavelengths = new Float[length];
					aborbances = new Float[length];
					for(SeriesType series : seriesSet.getSeries()) {
						if(series.getName().equals("Spectrum")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								List<Float> floats = individualValueSet.getF();
								for(int i = 0; i < floats.size(); i++) {
									wavelengths[i] = floats.get(i);
								}
							}
							for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
								wavelengths = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
							}
						}
						if(series.getName().equals("Intensity")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								List<Float> floats = individualValueSet.getF();
								for(int i = 0; i < floats.size(); i++) {
									aborbances[i] = floats.get(i);
								}
							}
							for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
								aborbances = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
							}
						}
					}
				}
				IScanWSD scan = (IScanWSD)chromatogram.getScan(spectra);
				scan.removeScanSignal(0); // replace fixed wavelengths with full DAD spectra
				for(int i = 0; i < seriesSet.getLength(); i++) {
					float wavelength = wavelengths[i];
					float aborbance = aborbances[i];
					IVendorScanSignalWSD signal = new VendorScanSignalWSD();
					signal.setWavelength(wavelength);
					signal.setAbundance(aborbance);
					scan.addScanSignal(signal);
				}
			}
		}
	}

	private void readPeakTable(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		List<Float> startTimes = new ArrayList<>();
		List<Float> endTimes = new ArrayList<>();
		List<String> peakNames = new ArrayList<>();
		if(experimentStep.getTechnique().getName().equals("Chromatography Peak Table")) {
			for(ResultType result : experimentStep.getResult()) {
				SeriesSetType seriesSet = result.getSeriesSet();
				if(seriesSet.getName().equals("Peak Table")) {
					for(SeriesType series : seriesSet.getSeries()) {
						if(series.getName().equals("Start Time")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								startTimes.addAll(individualValueSet.getF());
							}
						}
						if(series.getName().equals("End Time")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								endTimes.addAll(individualValueSet.getF());
							}
						}
						if(series.getName().equals("Name")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								peakNames.addAll(individualValueSet.getS());
							}
						}
					}
					int peaks = seriesSet.getLength();
					for(int p = 0; p < peaks; p++) {
						int startScan = chromatogram.getScanNumber(startTimes.get(p));
						int stopScan = chromatogram.getScanNumber(endTimes.get(p));
						IScanRange scanRange = new ScanRange(startScan, stopScan);
						try {
							IChromatogramPeakWSD chromatogramPeak = PeakBuilderWSD.createPeak(chromatogram, scanRange, true);
							ILibraryInformation libraryInformation = new LibraryInformation();
							if(peakNames.size() >= p) {
								libraryInformation.setName(peakNames.get(p));
							}
							IComparisonResult comparisonResult = ComparisonResult.COMPARISON_RESULT_BEST_MATCH;
							IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
							chromatogramPeak.getTargets().add(identificationTarget);
							chromatogram.addPeak(chromatogramPeak);
						} catch(Exception e) {
							logger.warn("Peak " + p + " could not be added.");
						}
					}
				}
			}
		}
	}
}