/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.core.runtime.IProgressMonitor;

import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileCache;

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsEmptyException;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotReadableException;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.CDFChromatogramOverviewArrayReader;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.CDFChromtogramArrayReader;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.DateSupport;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.IAbstractCDFChromatogramArrayReader;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.model.CDFChromatogram;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.model.CDFMassFragment;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.model.CDFMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;
import net.openchrom.chromatogram.msd.model.core.IMassFragment;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.MZLimitExceededException;
import net.openchrom.logging.core.Logger;

/**
 * This class reads a valid cdf file.<br/>
 * It is responsible to parse the data structure and it will construct a valid
 * CDFChromatogram.
 * 
 * @author eselmeister
 */
public class CDFChromatogramReader implements ICDFChromatogramReader {

	private static final Logger logger = Logger.getLogger(CDFChromatogramReader.class);

	@Override
	public IChromatogram read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

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
		NetcdfFile cdfChromatogram = NetcdfFileCache.acquire(file.getAbsolutePath(), null);
		CDFChromtogramArrayReader in = new CDFChromtogramArrayReader(cdfChromatogram);
		chromatogram = new CDFChromatogram();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		for(int i = 1; i <= in.getNumberOfScans(); i++) {
			try {
				monitor.subTask(IConstants.SCAN + " " + i);
				massSpectrum = in.getMassSpectrum(i);
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
	 * @throws MZLimitExceededException
	 * @throws AbundanceLimitExceededException
	 */
	private CDFChromatogram readChromatogramOverview(File file, IProgressMonitor monitor) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored, AbundanceLimitExceededException, MZLimitExceededException {

		CDFChromatogram chromatogram;
		CDFMassSpectrum massSpectrum;
		CDFMassFragment massFragment;
		NetcdfFile cdfChromatogram = NetcdfFileCache.acquire(file.getAbsolutePath(), null);
		CDFChromatogramOverviewArrayReader in = new CDFChromatogramOverviewArrayReader(cdfChromatogram);
		chromatogram = new CDFChromatogram();
		setChromatogramEntries(chromatogram, in, file);
		monitor.subTask(IConstants.PARSE_SCANS);
		for(int i = 1; i <= in.getNumberOfScans(); i++) {
			monitor.subTask(IConstants.SCAN + " " + i);
			massSpectrum = new CDFMassSpectrum();
			massSpectrum.setRetentionTime(in.getScanAcquisitionTime(i));
			massFragment = new CDFMassFragment(IMassFragment.TIC_MZ, true);
			massFragment.setAbundance(in.getTotalSignal(i));
			massSpectrum.addMassFragment(massFragment);
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
		chromatogram.setScanDelay(in.getScanDelay());
		chromatogram.setScanInterval(in.getScanInterval());
		/*
		 * Extension
		 */
		chromatogram.setConverterId("net.openchrom.chromatogram.msd.converter.supplier.cdf");
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
