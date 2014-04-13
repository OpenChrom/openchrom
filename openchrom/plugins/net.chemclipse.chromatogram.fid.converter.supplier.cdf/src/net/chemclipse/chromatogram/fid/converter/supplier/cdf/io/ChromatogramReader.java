/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.fid.converter.supplier.cdf.io;

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
import net.chemclipse.chromatogram.fid.converter.io.AbstractChromatogramFIDReader;
import net.chemclipse.chromatogram.fid.converter.io.IChromatogramFIDReader;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.internal.converter.IConstants;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.io.support.CDFChromtogramArrayReader;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.io.support.DateSupport;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.io.support.IAbstractCDFChromatogramArrayReader;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.model.CDFChromatogramFID;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.model.CDFSupplierScan;
import net.chemclipse.chromatogram.fid.model.core.IChromatogramFID;
import net.chemclipse.chromatogram.model.core.IChromatogramOverview;
import net.chemclipse.chromatogram.model.exceptions.AbundanceLimitExceededException;
import net.chemclipse.logging.core.Logger;

public class ChromatogramReader extends AbstractChromatogramFIDReader implements IChromatogramFIDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramFID read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readFile(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readFile(file, monitor);
	}

	private IChromatogramFID readFile(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		CDFChromatogramFID chromatogram;
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

	/**
	 * Reads the chromatogram.
	 * 
	 * @param file
	 * @return CDFChromatogram
	 * @throws IOException
	 * @throws NoCDFVariableDataFound
	 * @throws NotEnoughScanDataStored
	 * @throws IonLimitExceededException
	 * @throws AbundanceLimitExceededException
	 */
	private CDFChromatogramFID readChromatogram(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored, AbundanceLimitExceededException {

		@SuppressWarnings("deprecation")
		NetcdfFile cdfChromatogram = new NetcdfFile(file.getAbsolutePath());
		CDFChromtogramArrayReader in = new CDFChromtogramArrayReader(cdfChromatogram);
		CDFChromatogramFID chromatogram = new CDFChromatogramFID();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		int retentionTime = 0;
		for(int i = 1; i < in.getNumberOfScans(); i++) {
			monitor.subTask(IConstants.SCAN + " " + i);
			CDFSupplierScan scan = new CDFSupplierScan(in.getIntensity(i));
			scan.setRetentionTime(retentionTime);
			retentionTime += 100;
			chromatogram.addScan(scan);
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
	private void setChromatogramEntries(CDFChromatogramFID chromatogram, IAbstractCDFChromatogramArrayReader in, File file) {

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
		chromatogram.setConverterId("net.chemclipse.chromatogram.fid.converter.supplier.cdf");
		String operator = "";
		String date = "";
		try {
			operator = in.getOperator();
			date = in.getDate();
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
		chromatogram.setOperator(operator);
	}
}
