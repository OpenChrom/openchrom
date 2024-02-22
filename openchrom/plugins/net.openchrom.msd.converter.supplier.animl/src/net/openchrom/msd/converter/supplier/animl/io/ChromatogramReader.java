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
 * Philip Wenig - fixed return logic
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.animl.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.support.IScanRange;
import org.eclipse.chemclipse.model.support.ScanRange;
import org.eclipse.chemclipse.msd.converter.io.AbstractChromatogramMSDReader;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.PeakBuilderMSD;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;

import net.openchrom.msd.converter.supplier.animl.model.IVendorChromatogram;
import net.openchrom.msd.converter.supplier.animl.model.IVendorIon;
import net.openchrom.msd.converter.supplier.animl.model.IVendorScan;
import net.openchrom.msd.converter.supplier.animl.model.VendorChromatogram;
import net.openchrom.msd.converter.supplier.animl.model.VendorIon;
import net.openchrom.msd.converter.supplier.animl.model.VendorScan;
import net.openchrom.xxd.converter.supplier.animl.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.converter.Common;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.AutoIncrementedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.DeviceType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ParameterTypeType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.model.astm.core.UnitType;

import jakarta.xml.bind.JAXBException;

public class ChromatogramReader extends AbstractChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		if(file.isDirectory() || !file.getName().endsWith(".animl")) {
			return null;
		}
		IVendorChromatogram chromatogram = null;
		try {
			AnIMLType animl = Common.getAnIML(file);
			chromatogram = new VendorChromatogram();
			chromatogram.setFile(file);
			readSample(animl, chromatogram);
			chromatogram.getEditHistory().addAll(Common.readAuditTrail(animl));
			for(ExperimentStepType experimentStep : animl.getExperimentStepSet().getExperimentStep()) {
				if(experimentStep.getTechnique().getName().equals("Chromatography")) {
					readMethod(experimentStep, chromatogram);
					for(ResultType result : experimentStep.getResult()) {
						if(result.getSeriesSet().getName().equals("Separation Monitoring")) {
							for(SeriesType series : result.getSeriesSet().getSeries()) {
								readRetentionTime(series, chromatogram);
							}
						}
						for(ExperimentStepType resultExperimentStep : result.getExperimentStepSet().getExperimentStep()) {
							readTotalIonCurrent(resultExperimentStep, chromatogram);
							readMassSpectra(resultExperimentStep, chromatogram);
							readPeakTable(resultExperimentStep, chromatogram);
						}
					}
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
			AnIMLType animl = Common.getAnIML(file);
			chromatogram = new VendorChromatogram();
			readSample(animl, chromatogram);
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

	private void readSample(AnIMLType animl, IVendorChromatogram chromatogram) {

		SampleType sample = animl.getSampleSet().getSample().get(0);
		chromatogram.setSampleName(sample.getName());
		chromatogram.setBarcode(sample.getBarcode());
		chromatogram.setDetailedInfo(sample.getSampleID());
		chromatogram.setMiscInfo(sample.getComment());
	}

	private void readMethod(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		MethodType method = experimentStep.getMethod();
		if(method != null) {
			AuthorType author = method.getAuthor();
			if(author != null) {
				chromatogram.setOperator(author.getName());
			}
			DeviceType device = method.getDevice();
			if(device != null) {
				chromatogram.setInstrument(device.getName());
			}
		}
	}

	private void readTotalIonCurrent(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		if(experimentStep.getTechnique().getName().equals("Mass Spectrum Time Trace")) {
			List<Integer> retentionTimes = new ArrayList<>();
			List<Float> signals = new ArrayList<>();
			for(ResultType result : experimentStep.getResult()) {
				SeriesSetType seriesSet = result.getSeriesSet();
				if(chromatogram.getNumberOfScans() == seriesSet.getLength()) {
					return;
				}
				if(seriesSet.getName().equals("Mass Chromatogram")) {
					for(SeriesType series : seriesSet.getSeries()) {
						UnitType unit = series.getUnit();
						if(unit.getLabel().equals("Time")) {
							int multiplicator = Common.getTimeMultiplicator(unit);
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
						if(unit.getLabel().equals("Abundance")) {
							for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
								signals.addAll(individualValueSet.getF());
							}
							for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
								float[] decodedValues = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
								for(float f : decodedValues) {
									signals.add(f);
								}
							}
						}
					}
				}
				try {
					for(int i = 0; i < seriesSet.getLength(); i++) {
						IVendorScan scan = new VendorScan();
						VendorIon ion = new VendorIon(IIon.TIC_ION, signals.get(i));
						scan.addIon(ion, false);
						scan.setRetentionTime(Math.round(retentionTimes.get(i)));
						chromatogram.addScan(scan);
					}
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
	}

	private void readRetentionTime(SeriesType series, IVendorChromatogram chromatogram) {

		List<Integer> retentionTimes = new ArrayList<>();
		if(series.getName().equals("Time")) {
			UnitType unit = series.getUnit();
			if(unit != null && unit.getQuantity() != null && unit.getQuantity().equals("Time")) {
				int multiplicator = Common.getTimeMultiplicator(unit);
				for(AutoIncrementedValueSetType autoIncrementedValueSet : series.getAutoIncrementedValueSet()) {
					double start = autoIncrementedValueSet.getStartValue().getD().get(0);
					chromatogram.setScanDelay((int)Math.round(start * multiplicator));
					double increment = autoIncrementedValueSet.getIncrement().getD().get(0);
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
				if(series.getSeriesType().equals(ParameterTypeType.INT_32)) {
					for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
						int[] decodedValues = BinaryReader.decodeIntArray(encodedValueSet.getValue());
						for(int i : decodedValues) {
							retentionTimes.add(multiplicator * i);
						}
					}
				} else if(series.getSeriesType().equals(ParameterTypeType.FLOAT_32)) {
					for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
						float[] decodedValues = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
						for(float f : decodedValues) {
							retentionTimes.add((int)(multiplicator * f));
						}
					}
				}
			}
		}
		for(int rt : retentionTimes) {
			IVendorScan scan = new VendorScan();
			scan.setRetentionTime(rt);
			chromatogram.getScans().add(scan);
		}
	}

	private int spectra = 0;

	private void readMassSpectra(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		if(experimentStep.getTechnique().getName().equals("Mass Spectrometry")) {
			spectra++;
			double[] mzs = null;
			float[] intensities = null;
			for(ResultType result : experimentStep.getResult()) {
				SeriesSetType seriesSet = result.getSeriesSet();
				if(seriesSet.getName().equals("Spectrum")) {
					int length = seriesSet.getLength();
					mzs = new double[length];
					intensities = new float[length];
					for(SeriesType series : seriesSet.getSeries()) {
						if(series.getName().equals("Mass/Charge")) {
							if(series.getSeriesType().equals(ParameterTypeType.FLOAT_64)) {
								for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
									List<Double> doubles = individualValueSet.getD();
									for(int i = 0; i < doubles.size(); i++) {
										mzs[i] = doubles.get(i);
									}
								}
								for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
									mzs = BinaryReader.decodeDoubleArray(encodedValueSet.getValue());
								}
							} else if(series.getSeriesType().equals(ParameterTypeType.FLOAT_32)) {
								for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
									List<Float> floats = individualValueSet.getF();
									for(int i = 0; i < floats.size(); i++) {
										mzs[i] = floats.get(i);
									}
								}
								for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
									float[] floats = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
									for(int i = 0; i < floats.length; i++) {
										mzs[i] = floats[i];
									}
								}
							}
						}
						if(series.getName().equals("Intensity")) {
							if(series.getSeriesType().equals(ParameterTypeType.FLOAT_32)) {
								for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
									List<Float> floats = individualValueSet.getF();
									for(int i = 0; i < floats.size(); i++) {
										intensities[i] = floats.get(i);
									}
								}
								for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
									intensities = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
								}
							} else if(series.getSeriesType().equals(ParameterTypeType.FLOAT_64)) {
								for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
									List<Double> doubles = individualValueSet.getD();
									for(int i = 0; i < doubles.size(); i++) {
										intensities[i] = doubles.get(i).floatValue();
									}
								}
								for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
									double[] doubles = BinaryReader.decodeDoubleArray(encodedValueSet.getValue());
									for(int i = 0; i < doubles.length; i++) {
										intensities[i] = (float)doubles[i];
									}
								}
							}
						}
					}
				}
				try {
					IScanMSD scan = (IScanMSD)chromatogram.getScan(spectra);
					int length = Math.min(mzs.length, intensities.length);
					for(int i = 0; i < length; i++) {
						float intensity = intensities[i];
						if(intensity >= VendorIon.MIN_ABUNDANCE && intensity <= VendorIon.MAX_ABUNDANCE) {
							double mz = AbstractIon.getIon(mzs[i]);
							IVendorIon ion = new VendorIon(mz, intensity);
							scan.addIon(ion, false);
						}
					}
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
	}

	private void readPeakTable(ExperimentStepType experimentStep, IVendorChromatogram chromatogram) {

		if(experimentStep.getName().equals("Peak Table")) {
			List<Float> startTimes = new ArrayList<>();
			List<Float> endTimes = new ArrayList<>();
			List<String> peakNames = new ArrayList<>();
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
							IChromatogramPeakMSD chromatogramPeak = PeakBuilderMSD.createPeak(chromatogram, scanRange, true);
							ILibraryInformation libraryInformation = new LibraryInformation();
							if(peakNames.size() >= p) {
								libraryInformation.setName(peakNames.get(p));
							}
							IComparisonResult comparisonResult = ComparisonResult.COMPARISON_RESULT_BEST_MATCH;
							IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
							chromatogramPeak.getTargets().add(identificationTarget);
							chromatogram.addPeak(chromatogramPeak);
						} catch(Exception e) {
							logger.warn("Peak " + p + " could not be added.", e);
						}
					}
				}
			}
		}
	}
}
