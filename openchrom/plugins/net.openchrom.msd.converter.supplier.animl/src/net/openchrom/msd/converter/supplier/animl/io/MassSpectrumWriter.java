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
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.IVendorMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.converter.supplier.animl.internal.converter.BinaryReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.Common;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AnIMLType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntrySetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.AuditTrailEntryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.CategoryType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.DependencyType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.EncodedValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.ExperimentStepType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.IndividualValueSetType;
import net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core.MethodType;
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

public class MassSpectrumWriter implements IMassSpectraWriter {

	private static final Logger logger = Logger.getLogger(MassSpectrumWriter.class);

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectrum(fileWriter, massSpectrum, monitor);
		fileWriter.close();
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectra(fileWriter, massSpectra, monitor);
		fileWriter.close();
	}

	private void writeMassSpectra(FileWriter fileWriter, IMassSpectra massSpectra, IProgressMonitor monitor) throws IOException {

		for(int i = 1; i <= massSpectra.size(); i++) {
			IScanMSD massSpectrum = massSpectra.getMassSpectrum(i);
			if(massSpectrum != null && massSpectrum.getNumberOfIons() > 0) {
				writeMassSpectrum(fileWriter, massSpectrum, monitor);
			}
		}
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

		try {
			writeAnIML(fileWriter, (IVendorMassSpectrum)massSpectrum);
		} catch(JAXBException e) {
			logger.warn(e);
		}
	}

	private void writeAnIML(FileWriter fileWriter, IVendorMassSpectrum massSpectrum) throws JAXBException {

		JAXBContext jaxbContext = JAXBContext.newInstance(AnIMLType.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		//
		AnIMLType anIML = new AnIMLType();
		anIML.setVersion("0.90");
		anIML.setSampleSet(createSampleSet(massSpectrum));
		anIML.setExperimentStepSet(createExperimentStep(massSpectrum));
		anIML.setAuditTrailEntrySet(createAuditTrail());
		marshaller.marshal(anIML, fileWriter);
	}

	private SampleSetType createSampleSet(IVendorMassSpectrum massSpectrum) {

		SampleSetType sampleSet = new SampleSetType();
		SampleType sample = new SampleType();
		sample.setId("OPENCHROM_MASS_SPECTRUM_EXPORT");
		sample.setName(massSpectrum.getName());
		sample.setComment(massSpectrum.getIdentifier());
		sample.setSampleID(FilenameUtils.removeExtension(massSpectrum.getFile().getName()));
		sample.setSourceDataLocation(massSpectrum.getFile().getAbsolutePath());
		sampleSet.getSample().add(sample);
		return sampleSet;
	}

	private ExperimentStepSetType createExperimentStep(IVendorMassSpectrum massSpectrum) {

		ExperimentStepSetType experimentStepSet = new ExperimentStepSetType();
		ExperimentStepType massSpecStep = new ExperimentStepType();
		massSpecStep.setName("Mass Spectrometry");
		massSpecStep.setId("MASS_SPECTROMETRY");
		massSpecStep.setTechnique(createMassSpecTechnique());
		massSpecStep.getResult().add(createResult(massSpectrum));
		massSpecStep.setMethod(createMethod());
		experimentStepSet.getExperimentStep().add(massSpecStep);
		return experimentStepSet;
	}

	private TechniqueType createMassSpecTechnique() {

		TechniqueType technique = new TechniqueType();
		technique.setName("Mass Spectrometry");
		technique.setUri("https://github.com/AnIML/techniques/blob/db3fcd831c4fce00b647b7e6d8c21f753b76f361/mass-spec.atdd");
		return technique;
	}

	private ResultType createResult(IVendorMassSpectrum massSpectrum) {

		SeriesSetType seriesSet = new SeriesSetType();
		seriesSet.setName("Spectrum");
		seriesSet.setLength(massSpectrum.getNumberOfIons());
		//
		SeriesType massChargeSeries = new SeriesType();
		massChargeSeries.setName("Mass/Charge");
		massChargeSeries.setSeriesID("MASS_CHARGE");
		UnitType massChargeUnit = new UnitType();
		massChargeUnit.setLabel("Mass/Charge Ratio");
		massChargeUnit.setQuantity("mz");
		massChargeSeries.setUnit(massChargeUnit);
		massChargeSeries.setDependency(DependencyType.INDEPENDENT);
		massChargeSeries.setSeriesType(ParameterTypeType.FLOAT_64);
		massChargeSeries.setPlotScale(PlotScaleType.LINEAR);
		//
		SeriesType intensitySeries = new SeriesType();
		intensitySeries.setName("Intensity");
		UnitType intensityUnit = new UnitType();
		intensityUnit.setLabel("Abundance");
		intensityUnit.setQuantity("arbitrary");
		intensitySeries.setUnit(intensityUnit);
		intensitySeries.setDependency(DependencyType.DEPENDENT);
		intensitySeries.setSeriesType(ParameterTypeType.FLOAT_32);
		intensitySeries.setPlotScale(PlotScaleType.LINEAR);
		//
		if(PreferenceSupplier.getMassSpectrumSaveEncoded()) {
			int scans = massSpectrum.getNumberOfIons();
			double[] ions = new double[scans];
			float[] intensities = new float[scans];
			int i = 0;
			for(IIon ion : massSpectrum.getIons()) {
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
			for(IIon ion : massSpectrum.getIons()) {
				ions.getD().add(ion.getIon());
				intensities.getF().add(ion.getAbundance());
			}
			massChargeSeries.getIndividualValueSet().add(ions);
			intensitySeries.getIndividualValueSet().add(intensities);
		}
		seriesSet.getSeries().add(massChargeSeries);
		seriesSet.getSeries().add(intensitySeries);
		ResultType result = new ResultType();
		CategoryType category = new CategoryType();
		ParameterType parameter = new ParameterType();
		parameter.setName("Type");
		if(massSpectrum.getMassSpectrumType() == 0) {
			parameter.getS().add("Centroided");
		} else {
			parameter.getS().add("Continuous");
		}
		category.getParameter().add(parameter);
		result.getCategory().add(category);
		result.setSeriesSet(seriesSet);
		return result;
	}

	private MethodType createMethod() {

		MethodType method = new MethodType();
		method.setSoftware(Common.createSoftware());
		return method;
	}

	private AuditTrailEntrySetType createAuditTrail() {

		AuditTrailEntrySetType auditTrailEntrySet = new AuditTrailEntrySetType();
		AuditTrailEntryType auditTrail = new AuditTrailEntryType();
		auditTrail.setSoftware(Common.createSoftware());
		return auditTrailEntrySet;
	}
}
