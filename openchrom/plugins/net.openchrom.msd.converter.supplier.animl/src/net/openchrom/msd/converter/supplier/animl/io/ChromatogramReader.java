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
 * Philip Wenig - fixed return logic
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.animl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
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
import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.XmlReader;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuthorType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.MethodType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.UnitType;

public class ChromatogramReader extends AbstractChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws IOException {

		if(file.isDirectory() || !file.getName().endsWith(".animl")) {
			return null;
		}
		IVendorChromatogram chromatogram = null;
		try {
			AnIMLType animl = XmlReader.getAnIML(file);
			chromatogram = new VendorChromatogram();
			chromatogram = readSample(animl, chromatogram);
			List<Integer> retentionTimes = new ArrayList<>();
			List<Float> signals = new ArrayList<>();
			double[] mzs = null;
			float[] intensities = null;
			for(ExperimentStepType experimentStep : animl.getExperimentStepSet().getExperimentStep()) {
				if(experimentStep.getTechnique().getName().equals("Mass Spectrum Time Trace")) {
					MethodType method = experimentStep.getMethod();
					if(method != null) {
						AuthorType author = method.getAuthor();
						chromatogram.setOperator(author.getName());
					}
					for(ResultType result : experimentStep.getResult()) {
						SeriesSetType seriesSet = result.getSeriesSet();
						if(seriesSet.getName().equals("Mass Chromatogram")) {
							for(SeriesType series : seriesSet.getSeries()) {
								UnitType unit = series.getUnit();
								if(unit.getLabel().equals("Time")) {
									int multiplicator = XmlReader.getTimeMultiplicator(unit);
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
				int spectra = 0;
				if(experimentStep.getTechnique().getName().equals("Mass Spectrometry")) {
					for(ResultType result : experimentStep.getResult()) {
						SeriesSetType seriesSet = result.getSeriesSet();
						if(seriesSet.getName().equals("Spectrum")) {
							spectra++;
							int length = seriesSet.getLength();
							mzs = new double[length];
							intensities = new float[length];
							if(seriesSet.getName().equals("Spectrum")) {
								for(SeriesType series : seriesSet.getSeries()) {
									if(series.getName().equals("Mass/Charge")) {
										for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
											List<Double> doubles = individualValueSet.getD();
											for(int i = 0; i < doubles.size(); i++) {
												mzs[i] = doubles.get(i);
											}
										}
										for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
											mzs = BinaryReader.decodeDoubleArray(encodedValueSet.getValue());
										}
									}
									if(series.getName().equals("Intensity")) {
										for(IndividualValueSetType individualValueSet : series.getIndividualValueSet()) {
											List<Float> floats = individualValueSet.getF();
											for(int i = 0; i < floats.size(); i++) {
												intensities[i] = floats.get(i);
											}
										}
										for(EncodedValueSetType encodedValueSet : series.getEncodedValueSet()) {
											intensities = BinaryReader.decodeFloatArray(encodedValueSet.getValue());
										}
									}
								}
							}
						}
						try {
							IScanMSD scan = (IScanMSD)chromatogram.getScan(spectra);
							scan.removeAllIons(); // we don't need the TIC anymore
							for(int i = 0; i < seriesSet.getLength(); i++) {
								double intensity = intensities[i];
								if(intensity >= VendorIon.MIN_ABUNDANCE && intensity <= VendorIon.MAX_ABUNDANCE) {
									double mz = AbstractIon.getIon(mzs[i]);
									IVendorIon ion = new VendorIon(mz, (float)intensity);
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
									IChromatogramPeakMSD chromatogramPeak = PeakBuilderMSD.createPeak(chromatogram, scanRange, true);
									ILibraryInformation libraryInformation = new LibraryInformation();
									libraryInformation.setName(peakNames.get(p));
									IComparisonResult comparisonResult = ComparisonResult.createBestMatchComparisonResult();
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
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

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
		chromatogram.setDataName(sample.getName());
		chromatogram.setBarcode(sample.getBarcode());
		chromatogram.setDetailedInfo(sample.getSampleID());
		chromatogram.setMiscInfo(sample.getComment());
		return chromatogram;
	}
}
