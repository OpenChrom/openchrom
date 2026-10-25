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
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IMassSpectrumPeak;
import org.eclipse.chemclipse.model.core.MassSpectrumPeak;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
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

import hdf.hdf5lib.H5;
import hdf.hdf5lib.HDF5Constants;
import hdf.hdf5lib.exceptions.HDF5Exception;

// https://wiki-ms.microbe-ms.com/index.php?title=Format_of_Spectral_Multifiles
public class MassSpectrumReader extends AbstractMassSpectraReader {

	private static final Logger logger = Logger.getLogger(MassSpectrumReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		ISpectraMultiFileMassSpectra massSpectra = new SpectraMultiFileMassSpectra();

		try {
			long reader = H5.H5Fopen(file.getAbsolutePath(), HDF5Constants.H5F_ACC_RDONLY, HDF5Constants.H5P_DEFAULT);
			try {
				float lcms = readFloat(reader, "/spec/lms");
				if(lcms != 0) {
					throw new UnsupportedOperationException("Only MALDI-TOF MS is currently supported."); // TODO: read as chromatogram
				}

				IStandaloneMassSpectrum originalMassSpectrum = readSpectrum(reader, "/spec/org");
				originalMassSpectrum.setMassSpectrumType(MassSpectrumType.PROFILE);
				originalMassSpectrum.setIdentifier("original");
				originalMassSpectrum.setFile(file);
				setMeasurementDate(reader, originalMassSpectrum);
				readOriginalDataHistory(reader, originalMassSpectrum);
				massSpectra.addMassSpectrum(originalMassSpectrum);

				IStandaloneMassSpectrum processedMassSpectrum = readSpectrum(reader, "/spec/pre");
				processedMassSpectrum.setMassSpectrumType(MassSpectrumType.PROFILE);
				processedMassSpectrum.setIdentifier("processed");
				processedMassSpectrum.setFile(file);
				massSpectra.addMassSpectrum(processedMassSpectrum);
				readEditHistory(reader, processedMassSpectrum);
				readPeaks(reader, processedMassSpectrum);

				readMetaData(reader, massSpectra);
			} finally {
				H5.H5Fclose(reader);
			}
		} catch(HDF5Exception e) {
			logger.error(e);
		}
		return massSpectra;
	}

	private IStandaloneMassSpectrum readSpectrum(long reader, String field) {

		IStandaloneMassSpectrum massSpectrum = new StandaloneMassSpectrum();
		float[][] matrix = readFloatMatrix(reader, field);
		for(int i = 0; i < matrix.length; i++) {
			float mz = matrix[i][0];
			float intensity = matrix[i][1];
			IIon ion = new Ion(mz, intensity);
			massSpectrum.addIon(ion, false);
		}
		return massSpectrum;
	}

	private void readMetaData(long reader, ISpectraMultiFileMassSpectra massSpectra) {

		massSpectra.setName(readMatlabCharArray(reader, "/spec/nam"));

		massSpectra.setGenus(readMatlabCharArray(reader, "/spec/gen"));
		massSpectra.setSpecies(readMatlabCharArray(reader, "/spec/spe"));
		massSpectra.setStrain(readMatlabCharArray(reader, "/spec/str"));

		massSpectra.setType(readMatlabCharArray(reader, "/spec/typ"));

		try {
			String uid = readMatlabCharArray(reader, "/spec/uid");
			if(!uid.isBlank()) {
				massSpectra.setTaxonmicIdentifierNCBI(Integer.parseInt(uid));
			}

			String uie = readMatlabCharArray(reader, "/spec/uie");
			if(!uie.isBlank()) {
				massSpectra.setUnmodifiedTaxonmicIdentifierNCBI(Integer.parseInt(uie));
			}
		} catch(NumberFormatException e) {
			logger.warn(e.getMessage());
		}

		massSpectra.setGrowthTime(readMatlabCharArray(reader, "/spec/gti"));
		massSpectra.setTemperature(readMatlabCharArray(reader, "/spec/tem"));
		massSpectra.setAtmosphere(readMatlabCharArray(reader, "/spec/air"));
		massSpectra.setMedium(readMatlabCharArray(reader, "/spec/med"));

		String sporeFormer = readMatlabCharArray(reader, "/spec/spo");
		if(sporeFormer.equals("Yes")) {
			massSpectra.setSporeFormer(true);
		} else if(sporeFormer.equals("No")) {
			massSpectra.setSporeFormer(false);
		}

		massSpectra.setSampleConcentration(readMatlabCharArray(reader, "/spec/con"));
		massSpectra.setSampleTreatment(readMatlabCharArray(reader, "/spec/trt"));
		massSpectra.setExtraInformation(readMatlabCharArray(reader, "/spec/ext"));
		massSpectra.setLaserParameters(readMatlabCharArray(reader, "/spec/las"));
		massSpectra.setCalibrationInformation(readMatlabCharArray(reader, "/spec/cal"));
		massSpectra.setMeasurementMethod(readMatlabCharArray(reader, "/spec/met"));
		massSpectra.setCustomerInformation(readMatlabCharArray(reader, "/spec/cus"));

		massSpectra.setSpectrumPath(readMatlabCharArray(reader, "/spec/pth"));
		massSpectra.setClassAssignment((int)readFloat(reader, "/spec/cls"));
		massSpectra.setPeakTableInformation(readMatlabCharArray(reader, "/spec/lst"));

	}

	private void setMeasurementDate(long reader, IStandaloneMassSpectrum originalMassSpectrum) {

		try {
			String time = readMatlabCharArray(reader, "/spec/tim");
			OffsetDateTime offsetDateTime = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
			originalMassSpectrum.setDate(Date.from(offsetDateTime.toInstant()));
		} catch(DateTimeParseException e) {
			logger.warn(e.getMessage());
		}
	}

	private void readOriginalDataHistory(long reader, IStandaloneMassSpectrum originalMassSpectrum) {

		float modified = readFloat(reader, "/spec/mod");
		if(modified == 1) {
			Date date = originalMassSpectrum.getDate();
			originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Original data was modified."));

			String reduction = readMatlabCharArray(reader, "/spec/red");
			if(!reduction.isBlank()) {
				originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Data reduction factor: " + reduction));
			}

			String cut = readMatlabCharArray(reader, "/spec/cut");
			if(!cut.isBlank()) {
				originalMassSpectrum.getEditHistory().add(new EditInformation(date, "Cut: " + cut));
			}
		}
	}

	private void readEditHistory(long reader, IStandaloneMassSpectrum processedMassSpectrum) {

		Date date = new Date(processedMassSpectrum.getFile().lastModified());

		String preprocessingSteps = readMatlabCharArray(reader, "/spec/seq");

		if(preprocessingSteps.contains("SMO")) {
			String points = readMatlabValue(reader, "/spec/smo");
			processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Smoothing points: " + points));
		}

		if(preprocessingSteps.contains("BAS")) {
			String baseline = readMatlabValue(reader, "/spec/bas");
			processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Baseline correction: " + baseline));
		}

		if(preprocessingSteps.contains("NRM")) {
			String normalisation = readMatlabValue(reader, "/spec/nrm");
			if(normalisation.equals("1")) {
				processedMassSpectrum.getEditHistory().add(new EditInformation(date, "Normalisation"));
			}
		}
	}

	private static String readMatlabCharArray(long reader, String field) {

		long dataset = openReferencedDataset(reader, field);
		try {
			return readMatlabCharArray(dataset);
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	/**
	 * The specification declares some parameters as numbers,
	 * but MicrobeMS also writes them as char arrays.
	 */
	private static String readMatlabValue(long reader, String field) {

		long dataset = openReferencedDataset(reader, field);
		try {
			if(readMatlabClass(dataset).equals("char")) {
				return readMatlabCharArray(dataset).trim();
			}
			double[] data = new double[(int)getNumberOfElements(dataset)];
			if(data.length == 0) {
				return "";
			}
			H5.H5Dread_double(dataset, HDF5Constants.H5T_NATIVE_DOUBLE, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);
			double value = data[0];
			if(value == Math.rint(value)) {
				return Long.toString((long)value);
			}
			return Double.toString(value);
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	private static String readMatlabCharArray(long dataset) {

		if(H5.H5Aexists(dataset, "MATLAB_empty")) {
			return "";
		}
		short[] uint16 = new short[(int)getNumberOfElements(dataset)];
		H5.H5Dread_short(dataset, HDF5Constants.H5T_NATIVE_USHORT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, uint16);

		char[] chars = new char[uint16.length];
		for(int i = 0; i < uint16.length; i++) {
			chars[i] = (char)(uint16[i]);
		}

		return new String(chars);
	}

	private static String readMatlabClass(long dataset) {

		if(!H5.H5Aexists(dataset, "MATLAB_class")) {
			return "";
		}
		long attribute = H5.H5Aopen(dataset, "MATLAB_class", HDF5Constants.H5P_DEFAULT);
		try {
			long type = H5.H5Aget_type(attribute);
			try {
				byte[] buffer = new byte[(int)H5.H5Tget_size(type)];
				H5.H5Aread(attribute, type, buffer);
				return new String(buffer, StandardCharsets.US_ASCII).trim();
			} finally {
				H5.H5Tclose(type);
			}
		} finally {
			H5.H5Aclose(attribute);
		}
	}

	private static float readFloat(long reader, String field) {

		return readFloatMatrix(reader, field)[0][0];
	}

	private static float[][] readFloatMatrix(long reader, String field) {

		long dataset = openReferencedDataset(reader, field);
		try {
			long[] dimensions = getDimensions(dataset);
			int rows = (int)dimensions[0];
			int columns = dimensions.length > 1 ? (int)dimensions[1] : 1;
			float[] data = new float[rows * columns];
			H5.H5Dread_float(dataset, HDF5Constants.H5T_NATIVE_FLOAT, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, data);

			float[][] matrix = new float[rows][];
			for(int i = 0; i < rows; i++) {
				matrix[i] = Arrays.copyOfRange(data, i * columns, (i + 1) * columns);
			}
			return matrix;
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	/**
	 * MATLAB stores struct fields as arrays of object references. Like the
	 * original reader, this only follows the reference of the first entry.
	 */
	private static long openReferencedDataset(long reader, String field) {

		long dataset = H5.H5Dopen(reader, field, HDF5Constants.H5P_DEFAULT);
		try {
			int size = HDF5Constants.H5R_REF_BUF_SIZE;
			int count = (int)getNumberOfElements(dataset);
			byte[] buffer = new byte[count * size];
			H5.H5Dread(dataset, HDF5Constants.H5T_STD_REF, HDF5Constants.H5S_ALL, HDF5Constants.H5S_ALL, HDF5Constants.H5P_DEFAULT, buffer);
			byte[][] references = new byte[count][];
			for(int i = 0; i < count; i++) {
				references[i] = Arrays.copyOfRange(buffer, i * size, (i + 1) * size);
			}
			try {
				return H5.H5Ropen_object(references[0], HDF5Constants.H5P_DEFAULT, HDF5Constants.H5P_DEFAULT);
			} finally {
				for(byte[] reference : references) {
					H5.H5Rdestroy(reference);
				}
			}
		} finally {
			H5.H5Dclose(dataset);
		}
	}

	private static long[] getDimensions(long dataset) {

		long dataspace = H5.H5Dget_space(dataset);
		try {
			long[] dimensions = new long[H5.H5Sget_simple_extent_ndims(dataspace)];
			H5.H5Sget_simple_extent_dims(dataspace, dimensions, null);
			return dimensions;
		} finally {
			H5.H5Sclose(dataspace);
		}
	}

	private static long getNumberOfElements(long dataset) {

		long dataspace = H5.H5Dget_space(dataset);
		try {
			return H5.H5Sget_simple_extent_npoints(dataspace);
		} finally {
			H5.H5Sclose(dataspace);
		}
	}

	private void readPeaks(long reader, IStandaloneMassSpectrum processedMassSpectrum) {

		float[][] matrix = readFloatMatrix(reader, "/spec/pik");
		for(int i = 0; i < matrix.length; i++) {
			IMassSpectrumPeak peak = new MassSpectrumPeak();
			peak.setIon(matrix[i][0]);
			peak.setAbundance(matrix[i][1]);
			processedMassSpectrum.getPeaks().add(peak);
		}
	}
}
