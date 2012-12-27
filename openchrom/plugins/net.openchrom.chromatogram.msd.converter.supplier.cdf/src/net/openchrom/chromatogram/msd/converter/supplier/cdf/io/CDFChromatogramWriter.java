/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriteable;

import net.openchrom.chromatogram.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.converter.io.IChromatogramWriter;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.AttributeSupport;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.CDFConstants;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.DimensionSupport;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support.IDataEntry;
import net.openchrom.chromatogram.msd.model.core.IChromatogramMSD;
import net.openchrom.logging.core.Logger;

/**
 * Use this class if you want to write a valid cdf chromatogram file.<br/>
 * It will write all kinds of IChromatogram.
 * 
 * @author eselmeister
 */
public class CDFChromatogramWriter implements IChromatogramWriter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		// Do not distinguish between CDFChromatogram and others.
		monitor.subTask(IConstants.EXPORT_CDF_CHROMATOGRAM);
		writeCDFChromatogram(file, chromatogram, monitor);
	}

	// ------------------------------------------------------------private
	// methods
	private void writeCDFChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) {

		NetcdfFileWriteable cdfChromatogram = NetcdfFileWriteable.createNew(file.getAbsolutePath());
		DimensionSupport dimensionSupport = new DimensionSupport(cdfChromatogram, chromatogram);
		AttributeSupport.setAttributes(cdfChromatogram, chromatogram);
		Dimension errorNumber = dimensionSupport.getErrorNumber();
		Dimension byteString64 = dimensionSupport.getByteString64();
		Dimension byteString32 = dimensionSupport.getByteString32();
		Dimension numberOfScans = dimensionSupport.getNumberOfScans();
		Dimension instrumentNumber = dimensionSupport.getInstrumentNumber();
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_ERROR_LOG, errorNumber, byteString64, "");
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_A_D_SAMPLING_RATE, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableShortD1(CDFConstants.VARIABLE_A_D_COADDITION_FACTOR, numberOfScans, dimensionSupport.NULL_VALUE_SHORT);
		dimensionSupport.addVariableScanAcquisitionTime();
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_SCAN_DURATION, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_INTER_SCAN_TIME, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_RESOLUTION, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableActualScanNumber();
		dimensionSupport.addVariableTotalIntensity();
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_MASS_RANGE_MIN, numberOfScans, 0); // TODO
																										// da
																										// funktioniert
																										// was
																										// mit
																										// den
																										// ion
																										// werten
																										// nicht
		// dimensionSupport.addVariableMassRangeMin();
		dimensionSupport.addVariableMassRangeMax();
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_TIME_RANGE_MIN, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_TIME_RANGE_MAX, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableScanIndex();
		dimensionSupport.addVariablePointCount();
		dimensionSupport.addVariableIntD1(CDFConstants.VARIABLE_FLAG_COUNT, numberOfScans, 0);
		/*
		 * mass values, time values, intensity values
		 */
		dimensionSupport.addVariableScanValues();
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_NAME, instrumentNumber, byteString32, "Gas Chromatograph");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_ID, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_MFR, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_MODEL, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_SERIAL_NO, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_SW_VERSION, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_FW_VERSION, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_OS_VERSION, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_APP_VERSION, instrumentNumber, byteString32, "");
		dimensionSupport.addVariableCharD2(CDFConstants.VARIABLE_INSTRUMENT_COMMENTS, instrumentNumber, byteString32, "");
		try {
			monitor.subTask(IConstants.EXPORT_DATA_ENTRIES);
			cdfChromatogram.create();
			ArrayList<IDataEntry> dataEntries = dimensionSupport.getDataEntries();
			for(IDataEntry entry : dataEntries) {
				monitor.subTask(entry.getVarName());
				cdfChromatogram.write(entry.getVarName(), entry.getValues());
			}
			cdfChromatogram.close();
			cdfChromatogram = null;
		} catch(IOException e) {
			logger.warn(e);
		} catch(InvalidRangeException e) {
			logger.warn(e);
		} finally {
			if(cdfChromatogram != null) {
				try {
					cdfChromatogram.close();
				} catch(IOException e) {
					logger.warn(e);
				}
			}
		}
	}
	// ------------------------------------------------------------private
	// methods
}
