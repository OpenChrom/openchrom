/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.chemclipse.csd.converter.io.AbstractChromatogramCSDReader;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDReader;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.csd.converter.supplier.cdf.internal.converter.IConstants;
import net.openchrom.csd.converter.supplier.cdf.io.support.CDFChromtogramArrayReader;
import net.openchrom.csd.converter.supplier.cdf.io.support.DateSupport;
import net.openchrom.csd.converter.supplier.cdf.io.support.IAbstractCDFChromatogramArrayReader;
import net.openchrom.csd.converter.supplier.cdf.model.VendorChromatogramCSD;
import net.openchrom.csd.converter.supplier.cdf.model.VendorScan;

import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;

public class ChromatogramReaderCSD extends AbstractChromatogramCSDReader implements IChromatogramCSDReader {

	private static final Logger logger = Logger.getLogger(ChromatogramReaderCSD.class);

	@Override
	public IChromatogramCSD read(File file, IProgressMonitor monitor) throws IOException {

		return readFile(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws IOException {

		return readFile(file, monitor);
	}

	private IChromatogramCSD readFile(File file, IProgressMonitor monitor) throws IOException {

		VendorChromatogramCSD chromatogram;
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

		if(file == null) {
			return false;
		}
		String check = "CDF";
		byte[] data = new byte[3];
		try (FileInputStream is = new FileInputStream(file)) {
			is.read(data);
		} catch(FileNotFoundException e) {
			logger.warn(e);
		} catch(IOException e) {
			logger.warn(e);
		}
		String test = new String(data).trim();
		return test.equals(check);
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
	private VendorChromatogramCSD readChromatogram(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		monitor.subTask("Import");
		NetcdfFile cdfChromatogram = NetcdfFiles.open(file.getAbsolutePath());
		CDFChromtogramArrayReader in = new CDFChromtogramArrayReader(cdfChromatogram);
		VendorChromatogramCSD chromatogram = new VendorChromatogramCSD();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		int retentionTime = in.getScanDelay();
		int scans = in.getNumberOfScans();
		for(int i = 0; i < scans; i++) {
			VendorScan scan = new VendorScan(in.getIntensity(i));
			scan.setRetentionTime(retentionTime);
			retentionTime += in.getScanInterval();
			chromatogram.addScan(scan);
		}
		/*
		 * Peak Table
		 */
		in.readPeakTable(chromatogram);
		cdfChromatogram.close();
		//
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
	private void setChromatogramEntries(VendorChromatogramCSD chromatogram, IAbstractCDFChromatogramArrayReader in, File file) {

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
		} catch(NoCDFAttributeDataFound e) {
			logger.warn(e);
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
