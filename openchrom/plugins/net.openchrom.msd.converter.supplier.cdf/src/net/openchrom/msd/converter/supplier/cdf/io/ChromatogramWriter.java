/*******************************************************************************
 * Copyright (c) 2013, 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cdf.io.support.AttributeSupport;
import net.openchrom.msd.converter.supplier.cdf.io.support.CDFConstants;
import net.openchrom.msd.converter.supplier.cdf.io.support.DimensionSupport;
import net.openchrom.msd.converter.supplier.cdf.io.support.IDataEntry;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriteable;

@SuppressWarnings("deprecation")
public class ChromatogramWriter extends AbstractChromatogramWriter implements IChromatogramMSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		// Do not distinguish between CDFChromatogram and others.
		writeCDFChromatogram(file, chromatogram, monitor);
	}

	private void writeCDFChromatogram(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) throws IOException {

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
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_MASS_RANGE_MIN, numberOfScans, 0);
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
			cdfChromatogram.create();
			ArrayList<IDataEntry> dataEntries = dimensionSupport.getDataEntries();
			for(IDataEntry entry : dataEntries) {
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
}
