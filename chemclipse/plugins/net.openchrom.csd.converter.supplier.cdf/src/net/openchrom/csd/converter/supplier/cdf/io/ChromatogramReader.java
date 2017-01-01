/*******************************************************************************
 * Copyright (c) 2014, 2017 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;

import ucar.nc2.NetcdfFile;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.csd.converter.io.AbstractChromatogramCSDReader;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDReader;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.csd.converter.supplier.cdf.internal.converter.IConstants;
import net.openchrom.csd.converter.supplier.cdf.io.support.CDFChromtogramArrayReader;
import net.openchrom.csd.converter.supplier.cdf.io.support.DateSupport;
import net.openchrom.csd.converter.supplier.cdf.io.support.IAbstractCDFChromatogramArrayReader;
import net.openchrom.csd.converter.supplier.cdf.model.VendorChromatogram;
import net.openchrom.csd.converter.supplier.cdf.model.VendorScan;

public class ChromatogramReader extends AbstractChromatogramCSDReader implements IChromatogramCSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReader.class);

	@Override
	public IChromatogramCSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readFile(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readFile(file, monitor);
	}

	private IChromatogramCSD readFile(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		VendorChromatogram chromatogram;
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
	private VendorChromatogram readChromatogram(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored, AbundanceLimitExceededException {

		@SuppressWarnings("deprecation")
		NetcdfFile cdfChromatogram = new NetcdfFile(file.getAbsolutePath());
		CDFChromtogramArrayReader in = new CDFChromtogramArrayReader(cdfChromatogram);
		VendorChromatogram chromatogram = new VendorChromatogram();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		int retentionTime = in.getScanDelay();
		int scans = in.getNumberOfScans();
		for(int i = 0; i < scans; i++) {
			monitor.subTask(IConstants.SCAN + " " + i);
			VendorScan scan = new VendorScan(in.getIntensity(i));
			scan.setRetentionTime(retentionTime);
			retentionTime += in.getScanInterval();
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
	private void setChromatogramEntries(VendorChromatogram chromatogram, IAbstractCDFChromatogramArrayReader in, File file) {

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
		chromatogram.setConverterId("net.openchrom.csd.converter.supplier.cdf");
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
