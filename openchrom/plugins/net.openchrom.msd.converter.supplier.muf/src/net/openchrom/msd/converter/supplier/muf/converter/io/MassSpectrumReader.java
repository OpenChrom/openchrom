/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.muf.converter.io;

import java.io.File;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IMassSpectrumPeak;
import org.eclipse.chemclipse.model.core.MassSpectrumPeak;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IStandaloneMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.MassSpectrumType;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.StandaloneMassSpectrum;
import org.eclipse.chemclipse.support.history.EditInformation;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.muf.converter.model.ISpectraMultiFileMassSpectra;
import net.openchrom.msd.converter.supplier.muf.converter.model.SpectraMultiFileMassSpectra;

import ch.systemsx.cisd.base.mdarray.MDFloatArray;
import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.HDF5MDDataBlock;
import ch.systemsx.cisd.hdf5.IHDF5Reader;

// https://wiki-ms.microbe-ms.com/index.php?title=Format_of_Spectral_Multifiles
public class MassSpectrumReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MassSpectrumReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		ISpectraMultiFileMassSpectra massSpectra = new SpectraMultiFileMassSpectra();

		try (IHDF5Reader reader = HDF5Factory.openForReading(file)) {

			float lcms = reader.float32().read(reader.reference().read("/spec/lms"));
			if(lcms != 0) {
				throw new UnsupportedOperationException("Only MALDI-TOF MS is currently supported."); // TODO: read as chromatogram
			}

			IStandaloneMassSpectrum originalMassSpectrum = readSpectrum(reader, reader.reference().read("/spec/org"));
			originalMassSpectrum.setMassSpectrumType(MassSpectrumType.PROFILE);
			originalMassSpectrum.setIdentifier("original");
			originalMassSpectrum.setFile(file);
			setMeasurementDate(reader, originalMassSpectrum);
			readOriginalDataHistory(reader, originalMassSpectrum);
			massSpectra.addMassSpectrum(originalMassSpectrum);

			IStandaloneMassSpectrum processedMassSpectrum = readSpectrum(reader, reader.reference().read("/spec/pre"));
			processedMassSpectrum.setMassSpectrumType(MassSpectrumType.PROFILE);
			processedMassSpectrum.setIdentifier("processed");
			processedMassSpectrum.setFile(file);
			massSpectra.addMassSpectrum(processedMassSpectrum);
			readEditHistory(reader, processedMassSpectrum);
			readPeaks(reader, processedMassSpectrum);

			readMetaData(reader, massSpectra);
		}
		return massSpectra;
	}

	private IStandaloneMassSpectrum readSpectrum(IHDF5Reader reader, String field) {

		IStandaloneMassSpectrum massSpectrum = new StandaloneMassSpectrum();
		Iterator<HDF5MDDataBlock<MDFloatArray>> dataBlocks = reader.float32().getMDArrayNaturalBlocks(field).iterator();
		while(dataBlocks.hasNext()) {
			MDFloatArray data = dataBlocks.next().getData();
			float[][] matrix = data.toMatrix();
			for(int i = 0; i < data.size(0); i++) {
				float mz = matrix[i][0];
				float intensity = matrix[i][1];
				IIon ion = new Ion(mz, intensity);
				massSpectrum.addIon(ion, false);
			}
		}
		return massSpectrum;
	}

	private void readMetaData(IHDF5Reader reader, ISpectraMultiFileMassSpectra massSpectra) {

		massSpectra.setName(readMatlabCharArray(reader, reader.reference().read("/spec/nam")));

		massSpectra.setGenus(readMatlabCharArray(reader, reader.reference().read("/spec/gen")));
		massSpectra.setSpecies(readMatlabCharArray(reader, reader.reference().read("/spec/spe")));
		massSpectra.setStrain(readMatlabCharArray(reader, reader.reference().read("/spec/str")));

		massSpectra.setType(readMatlabCharArray(reader, reader.reference().read("/spec/typ")));

		try {
			String uid = readMatlabCharArray(reader, reader.reference().read("/spec/uid"));
			if(!uid.isBlank()) {
				massSpectra.setTaxonmicIdentifierNCBI(Integer.parseInt(uid));
			}

			String uie = readMatlabCharArray(reader, reader.reference().read("/spec/uie"));
			if(!uie.isBlank()) {
				massSpectra.setUnmodifiedTaxonmicIdentifierNCBI(Integer.parseInt(uie));
			}
		} catch(NumberFormatException e) {
			logger.warn(e.getMessage());
		}

		massSpectra.setGrowthTime(readMatlabCharArray(reader, reader.reference().read("/spec/gti")));
		massSpectra.setTemperature(readMatlabCharArray(reader, reader.reference().read("/spec/tem")));
		massSpectra.setAtmosphere(readMatlabCharArray(reader, reader.reference().read("/spec/air")));
		massSpectra.setMedium(readMatlabCharArray(reader, reader.reference().read("/spec/med")));

		String sporeFormer = readMatlabCharArray(reader, reader.reference().read("/spec/spo"));
		if(sporeFormer.equals("Yes")) {
			massSpectra.setSporeFormer(true);
		} else if(sporeFormer.equals("No")) {
			massSpectra.setSporeFormer(false);
		}

		massSpectra.setSampleConcentration(readMatlabCharArray(reader, reader.reference().read("/spec/con")));
		massSpectra.setSampleTreatment(readMatlabCharArray(reader, reader.reference().read("/spec/trt")));
		massSpectra.setExtraInformation(readMatlabCharArray(reader, reader.reference().read("/spec/ext")));
		massSpectra.setLaserParameters(readMatlabCharArray(reader, reader.reference().read("/spec/las")));
		massSpectra.setCalibrationInformation(readMatlabCharArray(reader, reader.reference().read("/spec/cal")));
		massSpectra.setMeasurementMethod(readMatlabCharArray(reader, reader.reference().read("/spec/met")));
		massSpectra.setCustomerInformation(readMatlabCharArray(reader, reader.reference().read("/spec/cus")));

		massSpectra.setSpectrumPath(readMatlabCharArray(reader, reader.reference().read("/spec/pth")));
		massSpectra.setClassAssignment((int)reader.float32().read(reader.reference().read("/spec/cls")));
		massSpectra.setPeakTableInformation(readMatlabCharArray(reader, reader.reference().read("/spec/lst")));

	}

	private void setMeasurementDate(IHDF5Reader reader, IStandaloneMassSpectrum originalMassSpectrum) {

		try {
			String time = readMatlabCharArray(reader, reader.reference().read("/spec/tim"));
			OffsetDateTime offsetDateTime = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			originalMassSpectrum.setDate(Date.from(offsetDateTime.toInstant()));
		} catch(DateTimeParseException e) {
			logger.warn(e.getMessage());
		}
	}

	private void readOriginalDataHistory(IHDF5Reader reader, IStandaloneMassSpectrum originalMassSpectrum) {

		float modified = reader.float32().read(reader.reference().read("/spec/mod"));
		if(modified == 1) {
			Date date = originalMassSpectrum.getDate();
			originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Original data was modified."));

			String reduction = readMatlabCharArray(reader, reader.reference().read("/spec/red"));
			if(!reduction.isBlank()) {
				originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Data reduction factor: " + reduction));
			}

			String cut = readMatlabCharArray(reader, reader.reference().read("/spec/cut"));
			if(!cut.isBlank()) {
				originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Cut: " + cut));
			}
		}
	}

	private void readEditHistory(IHDF5Reader reader, IStandaloneMassSpectrum processedMassSpectrum) {

		Date date = new Date(processedMassSpectrum.getFile().lastModified());

		String preprocessingSteps = readMatlabCharArray(reader, reader.reference().read("/spec/seq"));

		if(preprocessingSteps.contains("SMO")) {
			String points = readMatlabCharArray(reader, reader.reference().read("/spec/smo"));
			processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Smoothing points: " + points));
		}

		if(preprocessingSteps.contains("BAS")) {
			int intervals = ((int)reader.float32().read(reader.reference().read("/spec/bas")));
			processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Baseline correction with intervals " + intervals));
		}

		if(preprocessingSteps.contains("NRM")) {
			float normalisation = reader.float32().read(reader.reference().read("/spec/nrm"));
			if(normalisation == 1) {
				processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Normalisation"));
			}
		}
	}

	private static String readMatlabCharArray(IHDF5Reader reader, String reference) {

		short[] uint16 = reader.uint16().readArray(reference);

		char[] chars = new char[uint16.length];
		for(int i = 0; i < uint16.length; i++) {
			chars[i] = (char)(uint16[i]);
		}

		return new String(chars);
	}

	private void readPeaks(IHDF5Reader reader, IStandaloneMassSpectrum processedMassSpectrum) {

		Iterator<HDF5MDDataBlock<MDFloatArray>> dataBlocks = reader.float32().getMDArrayNaturalBlocks(reader.reference().read("/spec/pik")).iterator();
		while(dataBlocks.hasNext()) {
			MDFloatArray data = dataBlocks.next().getData();
			float[][] matrix = data.toMatrix();
			for(int i = 0; i < data.size(0); i++) {
				IMassSpectrumPeak peak = new MassSpectrumPeak();
				peak.setIon(matrix[i][0]);
				peak.setAbundance(matrix[i][1]);
				processedMassSpectrum.getPeaks().add(peak);
			}
		}
	}
}
