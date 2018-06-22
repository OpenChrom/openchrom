/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.exceptions.NotCalibratedVendorMassSpectrumException;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class MassSpectrumWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	public static final String CRLF = "\r\n";
	public static final float NORMALIZATION_BASE = 1000.0f;
	//
	private static final Logger logger = Logger.getLogger(MassSpectrumWriter.class);
	//
	// private static final String RT = "RT: ";
	// private static final String RRT = "RRT: ";
	// private static final String RI = "RI: ";
	private static final String NAME = "NAME: ";
	private static final String SCAN = "SCAN: ";
	private static final String CASNO = "CASNO: ";
	// private static final String SMILES = "SMILES: ";
	private static final String SYNONYM = "SYNONYM: ";
	private static final String COMMENT = "COMMENT: ";
	private static final String NUM_PEAKS = "NUM PEAKS: ";
	private static final String FORMULA = "FORMULA: ";
	private static final String MW = "MW: ";
	// private static final String DB = "DB: ";
	// private static final String REFID = "REFID: ";
	//
	private static final String SOURCEP = "SOURCEP: ";
	private static final String SPUNITS = "SPUNITS: ";
	private static final String SIGUNITS = "SIGUNITS: ";
	private static final String TSTAMP = "TSTAMP: ";
	private static final String ETIMES = "ETIMES: ";
	private static final String EENERGYV = "EENERGYV: ";
	private static final String IENERGYV = "IENERGYV: ";
	private static final String INAME = "INAME: ";

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectrum(fileWriter, massSpectrum, monitor);
		fileWriter.close();
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		FileWriter fileWriter = new FileWriter(file, append);
		writeMassSpectra(fileWriter, massSpectra, monitor);
		fileWriter.close();
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

		try {
			writeCMS(fileWriter, massSpectrum, monitor);
		} catch(NotCalibratedVendorMassSpectrumException e) {
			logger.warn(e);
		}
	}

	private void writeCMS(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException, NotCalibratedVendorMassSpectrumException {

		ICalibratedVendorLibraryMassSpectrum cvmSpectrum = null;
		String line;
		if(massSpectrum instanceof ICalibratedVendorMassSpectrum) {
			cvmSpectrum = (ICalibratedVendorMassSpectrum)massSpectrum;
		} else if(massSpectrum instanceof ICalibratedVendorLibraryMassSpectrum) {
			cvmSpectrum = (ICalibratedVendorLibraryMassSpectrum)massSpectrum;
		} else {
			throw new NotCalibratedVendorMassSpectrumException();
		}
		if(massSpectrum instanceof ICalibratedVendorMassSpectrum) {
			line = SCAN + ((ICalibratedVendorMassSpectrum)cvmSpectrum).getScanName() + CRLF;
		} else {
			line = NAME + cvmSpectrum.getLibraryInformation().getName() + CRLF;
		}
		fileWriter.write(line);
		/*
		 * Write the fields
		 * If the field value does not exist, write nothing
		 */
		if(!(massSpectrum instanceof ICalibratedVendorMassSpectrum)) {
			for(String synonym : cvmSpectrum.getLibraryInformation().getSynonyms()) {
				line = SYNONYM + synonym + CRLF;
				fileWriter.write(line);
			}
			line = getFormulaField(cvmSpectrum);
			if("" != line) {
				fileWriter.write(line);
			}
			line = getMWField(cvmSpectrum);
			if("" != line) {
				fileWriter.write(line);
			}
			line = getCasNumberField(cvmSpectrum);
			if("" != line) {
				fileWriter.write(line);
			}
		}
		for(String comment : cvmSpectrum.getComments()) {
			fileWriter.write(COMMENT + comment + CRLF);
		}
		line = getSourcePressureField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getSourcePressureUnitsField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getSignalUnitsField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getTimeStampField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getEtimesField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getEenergyField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getIenergyField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		line = getInstrumentNameField(cvmSpectrum);
		if("" != line) {
			fileWriter.write(line);
		}
		//
		fileWriter.write(getNumberOfPeaksField(cvmSpectrum));
		if(!(massSpectrum instanceof ICalibratedVendorMassSpectrum)) {
			fileWriter.write(getIons(cvmSpectrum));
		} else {
			fileWriter.write(getMeasurements((ICalibratedVendorMassSpectrum)cvmSpectrum));
		}
		/*
		 * To separate the mass spectra correctly.
		 */
		fileWriter.write(CRLF);
		fileWriter.flush();
	}

	/**
	 * Returns the instrument name from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getInstrumentNameField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		String iname = cvmSpectrum.getInstrumentName();
		//
		if((null != iname) && ("" != iname)) {
			field = INAME + iname + CRLF;
		}
		return field;
	}

	/**
	 * Returns the ion energy in Volts from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getIenergyField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		double ienergy = cvmSpectrum.getIenergy();
		//
		if(0d < ienergy) {
			field = IENERGYV + ienergy + CRLF;
		}
		return field;
	}

	/**
	 * Returns the electron energy in Volts from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getEenergyField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		double eenergy = cvmSpectrum.getEenergy();
		//
		if(0d < eenergy) {
			field = EENERGYV + eenergy + CRLF;
		}
		return field;
	}

	/**
	 * Returns the elapsed time in seconds from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getEtimesField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		double etimes = cvmSpectrum.getEtimes();
		//
		if(0d <= etimes) {
			field = ETIMES + etimes + CRLF;
		}
		return field;
	}

	/**
	 * Returns the timestamp from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getTimeStampField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		String tstamp = cvmSpectrum.getTimeStamp();
		//
		if((null != tstamp) && ("" != tstamp)) {
			field = TSTAMP + tstamp + CRLF;
		}
		return field;
	}

	/**
	 * Returns the signal units from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getSignalUnitsField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		String units = cvmSpectrum.getSignalUnits();
		//
		if((null != units) && ("" != units)) {
			field = SIGUNITS + units + CRLF;
		}
		return field;
	}

	/**
	 * Returns the source pressure units from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getSourcePressureUnitsField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		String units = cvmSpectrum.getSourcePressureUnits();
		//
		if((null != units) && ("" != units)) {
			field = SPUNITS + units + CRLF;
		}
		return field;
	}

	/**
	 * Returns the source pressure from the mass spectrum.
	 *
	 * @param cvmSpectrum
	 * @return String
	 */
	private String getSourcePressureField(ICalibratedVendorLibraryMassSpectrum cvmSpectrum) {

		String field = "";
		double pressure = cvmSpectrum.getSourcePressure();
		//
		if(0d < pressure) {
			field = SOURCEP + pressure + CRLF;
		}
		return field;
	}

	/**
	 * Returns the ions in the convenient AMDIS format.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	private String getIons(ICalibratedVendorLibraryMassSpectrum massSpectrum) {

		StringBuilder builder = new StringBuilder();
		StringBuilder line = new StringBuilder(80);
		String lineStr;
		//
		List<IIon> ions = massSpectrum.getIons();
		for(IIon ion : ions) {
			/*
			 * Add each peak measurement.
			 */
			if(0 == (ion.getIon() % 1)) {
				lineStr = (int)ion.getIon() + " " + ion.getAbundance() + "; ";
			} else {
				lineStr = ion.getIon() + " " + ion.getAbundance() + "; ";
			}
			if(line.length() + lineStr.length() > 78) {
				builder.append(line);
				builder.append(CRLF);
				line = new StringBuilder(80);
			}
			line.append(lineStr);
		}
		builder.append(line);
		builder.append(CRLF);
		return builder.toString();
	}

	/**
	 * Returns the measurements in the convenient AMDIS format.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	private String getMeasurements(ICalibratedVendorMassSpectrum massSpectrum) {

		StringBuilder builder = new StringBuilder();
		StringBuilder line = new StringBuilder(80);
		String lineStr;
		//
		List<IIonMeasurement> peaks = massSpectrum.getIonMeasurements();
		float signalOffset = massSpectrum.getSignalOffset();
		for(IIonMeasurement peak : peaks) {
			/*
			 * Add each peak measurement.
			 */
			if(0 == (peak.getMZ() % 1)) {
				lineStr = (int)peak.getMZ() + " " + (peak.getSignal() + signalOffset) + "; ";
			} else {
				lineStr = peak.getMZ() + " " + (peak.getSignal() + signalOffset) + "; ";
			}
			if(line.length() + lineStr.length() > 78) {
				builder.append(line);
				builder.append(CRLF);
				line = new StringBuilder(80);
			}
			line.append(lineStr);
		}
		builder.append(line);
		builder.append(CRLF);
		return builder.toString();
	}

	/**
	 * Returns the name information from the mass spectrum.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	protected String getNameField(IScanMSD massSpectrum, IIdentificationTarget identificationTarget) {

		/*
		 * The identifier is very important when tagging files
		 * for identification processes. Hence, if an identifier
		 * is available, set it.
		 */
		String field = NAME;
		String identification = "";
		String identifier = massSpectrum.getIdentifier();
		//
		if(identifier != null && !identifier.equals("")) {
			identification = identifier;
		} else if(identificationTarget != null) {
			identification = identificationTarget.getLibraryInformation().getName();
		}
		//
		if(identification.equals("")) {
			identification = "NO IDENTIFIER AVAILABLE";
		}
		//
		return field + identification;
	}

	/**
	 * Returns the CAS number information from the mass spectrum.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	protected String getCasNumberField(ICalibratedVendorLibraryMassSpectrum massSpectrum) {

		String field = "";
		String cas = massSpectrum.getLibraryInformation().getCasNumber();
		//
		if((null != cas) && ("" != cas)) {
			field = CASNO + cas + CRLF;
		}
		return field;
	}

	/**
	 * Returns the number of peaks information from the mass spectrum.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	protected String getNumberOfPeaksField(ICalibratedVendorLibraryMassSpectrum massSpectrum) {

		return NUM_PEAKS + massSpectrum.getNumberOfIons() + CRLF;
	}

	/**
	 * Returns the formula information from the mass spectrum.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	protected String getFormulaField(ICalibratedVendorLibraryMassSpectrum massSpectrum) {

		String field = "";
		String form = massSpectrum.getLibraryInformation().getFormula();
		//
		if((null != form) && ("" != form)) {
			field = FORMULA + form + CRLF;
		}
		return field;
	}

	/**
	 * Returns the MW information from the mass spectrum.
	 *
	 * @param massSpectrum
	 * @return String
	 */
	protected String getMWField(ICalibratedVendorLibraryMassSpectrum massSpectrum) {

		String field = "";
		double mw = massSpectrum.getLibraryInformation().getMolWeight();
		//
		if(0d < mw) {
			field = MW + mw + CRLF;
		}
		return field;
	}

	/**
	 * Writes the mass spectra with the given file writer.
	 *
	 * @throws IOException
	 */
	private void writeMassSpectra(FileWriter fileWriter, IMassSpectra massSpectra, IProgressMonitor monitor) throws IOException {

		/*
		 * Get all mass spectra, test to null and append them with the given
		 * file writer.
		 */
		for(int i = 1; i <= massSpectra.size(); i++) {
			IScanMSD massSpectrum = massSpectra.getMassSpectrum(i);
			/*
			 * There must be at least one ion.
			 */
			if(massSpectrum != null && massSpectrum.getNumberOfIons() > 0) {
				writeMassSpectrum(fileWriter, massSpectrum, monitor);
			}
		}
	}
}
