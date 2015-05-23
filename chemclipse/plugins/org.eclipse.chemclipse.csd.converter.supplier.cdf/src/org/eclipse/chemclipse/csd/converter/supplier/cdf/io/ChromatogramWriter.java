/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.csd.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriteable;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDWriter;
import org.eclipse.chemclipse.csd.converter.supplier.cdf.internal.converter.IConstants;
import org.eclipse.chemclipse.csd.converter.supplier.cdf.io.support.AttributeSupport;
import org.eclipse.chemclipse.csd.converter.supplier.cdf.io.support.CDFConstants;
import org.eclipse.chemclipse.csd.converter.supplier.cdf.io.support.DimensionSupport;
import org.eclipse.chemclipse.csd.converter.supplier.cdf.io.support.IDataEntry;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;

@SuppressWarnings("deprecation")
public class ChromatogramWriter implements IChromatogramCSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriter.class);

	@Override
	public void writeChromatogram(File file, IChromatogramCSD chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		monitor.subTask(IConstants.EXPORT_CDF_CHROMATOGRAM);
		writeCDFChromatogram(file, chromatogram, monitor);
	}

	private void writeCDFChromatogram(File file, IChromatogramCSD chromatogram, IProgressMonitor monitor) throws IOException {

		NetcdfFileWriteable cdfChromatogram = NetcdfFileWriteable.createNew(file.getAbsolutePath());
		DimensionSupport dimensionSupport = new DimensionSupport(cdfChromatogram, chromatogram);
		AttributeSupport.setAttributes(cdfChromatogram, chromatogram);
		Dimension numberOfScans = dimensionSupport.getNumberOfScans();
		//
		dimensionSupport.addVariableDoubleD1(CDFConstants.VARIABLE_ORDINATE_VALUES, numberOfScans, dimensionSupport.NULL_VALUE_DOUBLE);
		dimensionSupport.addVariableOrdinateValues();
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
}
