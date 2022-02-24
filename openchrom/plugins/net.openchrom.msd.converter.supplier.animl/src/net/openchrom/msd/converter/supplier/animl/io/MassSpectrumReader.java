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
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IVendorStandaloneMassSpectrum;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.VendorMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.SAXException;

import net.openchrom.msd.converter.supplier.animl.model.IVendorIon;
import net.openchrom.msd.converter.supplier.animl.model.IVendorMassSpectra;
import net.openchrom.msd.converter.supplier.animl.model.VendorIon;
import net.openchrom.msd.converter.supplier.animl.model.VendorMassSpectra;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.XmlReader;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ParameterType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ResultType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SampleType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.SeriesType;

public class MassSpectrumReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MassSpectrumReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		if(file.isDirectory() || !file.getName().endsWith(".animl")) {
			return null;
		}
		IVendorStandaloneMassSpectrum massSpectrum = null;
		//
		try {
			AnIMLType animl = XmlReader.getAnIML(file);
			//
			massSpectrum = new VendorMassSpectrum();
			massSpectrum = readSample(animl, massSpectrum);
			massSpectrum.setFile(file);
			massSpectrum.setIdentifier(file.getName());
			//
			double[] mzs = null;
			float[] intensities = null;
			int length = 0;
			//
			for(ExperimentStepType experimentStep : animl.getExperimentStepSet().getExperimentStep()) {
				if(experimentStep.getTechnique().getName().equals("Mass Spectrometry")) {
					for(ResultType result : experimentStep.getResult()) {
						for(CategoryType categeory : result.getCategory()) {
							for(ParameterType parameter : categeory.getParameter()) {
								if(parameter.getName().equals("Type")) {
									if(parameter.getS().contains("Centroided")) {
										massSpectrum.setMassSpectrumType((short)0);
									} else if(parameter.getS().contains("Continuous")) {
										massSpectrum.setMassSpectrumType((short)1);
									}
								}
							}
						}
						SeriesSetType seriesSet = result.getSeriesSet();
						length = seriesSet.getLength();
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
				}
			}
			for(int i = 0; i < length; i++) {
				try {
					double intensity = intensities[i];
					if(intensity >= VendorIon.MIN_ABUNDANCE && intensity <= VendorIon.MAX_ABUNDANCE) {
						double mz = AbstractIon.getIon(mzs[i]);
						IVendorIon ion = new VendorIon(mz, (float)intensity);
						massSpectrum.addIon(ion);
					}
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		} catch(SAXException e) {
			logger.warn(e);
		} catch(JAXBException e) {
			logger.warn(e);
		} catch(ParserConfigurationException e) {
			logger.warn(e);
		}
		//
		IVendorMassSpectra massSpectra = new VendorMassSpectra();
		massSpectra.setName(file.getName());
		massSpectra.addMassSpectrum(massSpectrum);
		return massSpectra;
	}

	private IVendorStandaloneMassSpectrum readSample(AnIMLType animl, IVendorStandaloneMassSpectrum massSpectrum) {

		SampleType sample = animl.getSampleSet().getSample().get(0);
		massSpectrum.setIdentifier(sample.getSampleID());
		return massSpectrum;
	}
}