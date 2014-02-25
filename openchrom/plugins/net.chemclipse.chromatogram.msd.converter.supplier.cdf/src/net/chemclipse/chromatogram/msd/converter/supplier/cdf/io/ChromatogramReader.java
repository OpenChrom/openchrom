/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;

import ucar.nc2.NetcdfFile;

import net.chemclipse.chromatogram.converter.exceptions.FileIsEmptyException;
import net.chemclipse.chromatogram.converter.exceptions.FileIsNotReadableException;
import net.chemclipse.chromatogram.model.core.IChromatogramOverview;
import net.chemclipse.chromatogram.model.exceptions.AbundanceLimitExceededException;
import net.chemclipse.chromatogram.msd.converter.io.AbstractChromatogramMSDReader;
import net.chemclipse.chromatogram.msd.converter.io.IChromatogramMSDReader;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.internal.support.IConstants;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.io.support.CDFChromatogramOverviewArrayReader;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.io.support.CDFChromtogramArrayReader;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.io.support.DateSupport;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.io.support.IAbstractCDFChromatogramArrayReader;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.model.CDFChromatogram;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.model.CDFIon;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.model.CDFMassSpectrum;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.preferences.ConverterPreferences;
import net.chemclipse.chromatogram.msd.model.core.IChromatogramMSD;
import net.chemclipse.chromatogram.msd.model.core.IIon;
import net.chemclipse.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.chemclipse.logging.core.Logger;

public class ChromatogramReader extends AbstractChromatogramMSDReader implements IChromatogramMSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramMSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		CDFChromatogram chromatogram;
		if(!isValidFileFormat(file)) {
			return null;
		}
		// If it is a valid file, try to read it.
		try {
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM);
			chromatogram = readChromatogram(file, monitor);
		} catch(Exception e) {
			logger.warn(e);
			return null;
		}
		return chromatogram;
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		CDFChromatogram chromatogram;
		if(!isValidFileFormat(file)) {
			return null;
		}
		// If it is a valid file, try to read it.
		try {
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM_OVERVIEW);
			chromatogram = readChromatogramOverview(file, monitor);
		} catch(Exception e) {
			logger.warn(e);
			return null;
		}
		return chromatogram;
	}

	/**
	 * Check whether the chromatogram file can be parsed or not.<br/>
	 * Returns true if the chromatogram can be parsed and false if not.
	 * 
	 * @return boolean
	 */
	private boolean isValidFileFormat(File file) {

		assert file != null : getClass().getName() + " The chromatogram file " + file + " must not be null.";
		String check = "CDF";
		if(file == null) {
			return false;
		} else {
			FileInputStream is = null;
			byte[] data = new byte[3];
			try {
				is = new FileInputStream(file);
				is.read(data);
			} catch(FileNotFoundException e) {
				logger.warn(e);
			} catch(IOException e) {
				logger.warn(e);
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch(IOException e) {
						logger.warn(e);
					}
				}
			}
			String test = new String(data).trim();
			if(test.equals(check)) {
				return true;
			}
			return false;
		}
	}

	private CDFChromatogram readChromatogram(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		CDFChromatogram chromatogram;
		CDFMassSpectrum massSpectrum;
		@SuppressWarnings("deprecation")
		NetcdfFile cdfChromatogram = new NetcdfFile(file.getAbsolutePath());
		CDFChromtogramArrayReader in = new CDFChromtogramArrayReader(cdfChromatogram);
		chromatogram = new CDFChromatogram();
		setChromatogramEntries(chromatogram, in, file);
		//
		int precision = ConverterPreferences.getPrecision();
		//
		monitor.subTask(IConstants.PARSE_SCANS);
		for(int i = 1; i <= in.getNumberOfScans(); i++) {
			try {
				monitor.subTask(IConstants.SCAN + " " + i);
				massSpectrum = in.getMassSpectrum(i, precision);
				chromatogram.addScan(massSpectrum);
			} catch(NoSuchScanStored e) {
				logger.warn(e);
			}
		}
		// Close the cdf chromatogram.
		cdfChromatogram.close();
		return chromatogram;
	}

	/**
	 * Reads the overview of a chromatogram.
	 * 
	 * @param file
	 * @return CDFChromatogram
	 * @throws IOException
	 * @throws NoCDFVariableDataFound
	 * @throws NotEnoughScanDataStored
	 * @throws IonLimitExceededException
	 * @throws AbundanceLimitExceededException
	 */
	private CDFChromatogram readChromatogramOverview(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored, AbundanceLimitExceededException, IonLimitExceededException {

		CDFChromatogram chromatogram;
		CDFMassSpectrum massSpectrum;
		CDFIon ion;
		@SuppressWarnings("deprecation")
		NetcdfFile cdfChromatogram = new NetcdfFile(file.getAbsolutePath());
		CDFChromatogramOverviewArrayReader in = new CDFChromatogramOverviewArrayReader(cdfChromatogram);
		chromatogram = new CDFChromatogram();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		for(int i = 1; i <= in.getNumberOfScans(); i++) {
			monitor.subTask(IConstants.SCAN + " " + i);
			massSpectrum = new CDFMassSpectrum();
			massSpectrum.setRetentionTime(in.getScanAcquisitionTime(i));
			ion = new CDFIon(IIon.TIC_ION, true);
			ion.setAbundance(in.getTotalSignal(i));
			massSpectrum.addIon(ion);
			chromatogram.addScan(massSpectrum);
		}
		// Close the cdf chromatogram.
		cdfChromatogram.close();
		return chromatogram;
	}

	/**
	 * Sets entries like operator, miscellaneous info, scan delay ... to the
	 * chromatogram.
	 * 
	 * @param chromatogram
	 * @param in
	 * @param file
	 */
	private void setChromatogramEntries(CDFChromatogram chromatogram, IAbstractCDFChromatogramArrayReader in, File file) {

		assert chromatogram != null : getClass().getName() + " The chromatogram must not be null";
		/*
		 * Scan delay must be not negative.
		 */
		int scanDelay = in.getScanDelay();
		scanDelay = (scanDelay < 0) ? 0 : scanDelay;
		chromatogram.setScanDelay(scanDelay);
		chromatogram.setScanInterval(in.getScanInterval());
		/*
		 * Extension
		 */
		chromatogram.setConverterId("net.chemclipse.chromatogram.msd.converter.supplier.cdf");
		String miscInfo = "";
		String operator = "";
		String date = "";
		String dateOfExperiment = "";
		try {
			miscInfo = in.getMiscInfo();
			operator = in.getOperator();
			date = in.getDate();
			dateOfExperiment = in.getDateOfExperiment();
		} catch(NoCDFAttributeDataFound e1) {
			logger.warn(e1);
		}
		/*
		 * Set the file name to the chromatogram.
		 */
		chromatogram.setFile(file);
		/*
		 * File creation date.
		 */
		Date creationDate;
		try {
			creationDate = DateSupport.getDate(date);
		} catch(ParseException e) {
			creationDate = new Date();
		}
		chromatogram.setDate(creationDate);
		/*
		 * Experiment date.
		 */
		Date experimentDate;
		try {
			experimentDate = DateSupport.getDate(dateOfExperiment);
		} catch(ParseException e) {
			experimentDate = new Date();
		}
		chromatogram.setDateOfExperiment(experimentDate);
		chromatogram.setMiscInfo(miscInfo);
		chromatogram.setOperator(operator);
	}
}
