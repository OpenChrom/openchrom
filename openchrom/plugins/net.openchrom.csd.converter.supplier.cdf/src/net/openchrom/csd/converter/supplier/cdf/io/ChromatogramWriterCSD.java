/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
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
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.converter.io.AbstractChromatogramWriter;
import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDWriter;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.csd.converter.supplier.cdf.io.support.AttributeSupport;
import net.openchrom.csd.converter.supplier.cdf.io.support.CDFConstants;
import net.openchrom.csd.converter.supplier.cdf.io.support.DimensionSupport;
import net.openchrom.csd.converter.supplier.cdf.io.support.IDataEntry;
import net.openchrom.csd.converter.supplier.cdf.io.support.VariableSupport;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.write.NetcdfFormatWriter;
import ucar.nc2.write.NetcdfFormatWriter.Builder;

public class ChromatogramWriterCSD extends AbstractChromatogramWriter implements IChromatogramCSDWriter {

	private static final Logger logger = Logger.getLogger(ChromatogramWriterCSD.class);

	@Override
	public void writeChromatogram(File file, IChromatogramCSD chromatogram, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		monitor.subTask(ConverterMessages.exportChromatogram);
		writeCDFChromatogram(file, chromatogram, monitor);
	}

	private void writeCDFChromatogram(File file, IChromatogramCSD chromatogram, IProgressMonitor monitor) throws IOException {

		Builder builder = NetcdfFormatWriter.createNewNetcdf3(file.getAbsolutePath());
		//
		DimensionSupport dimensionSupport = new DimensionSupport(builder, chromatogram);
		dimensionSupport.addVariableOrdinateValues();
		//
		AttributeSupport.setAttributes(builder, chromatogram);
		//
		VariableSupport.defineFloatVariable(builder, CDFConstants.VARIABLE_ACTUAL_DELAY_TIME);
		VariableSupport.defineFloatVariable(builder, CDFConstants.VARIABLE_ACTUAL_SAMPLING_INTERVAL);
		//
		try (NetcdfFormatWriter writer = builder.build()) {
			//
			float scanDelay = (float)(chromatogram.getScanDelay() / IChromatogramOverview.SECOND_CORRELATION_FACTOR);
			VariableSupport.writeScalarFloat(writer, CDFConstants.VARIABLE_ACTUAL_DELAY_TIME, scanDelay);
			float scanInterval = (float)(chromatogram.getScanInterval() / IChromatogramOverview.SECOND_CORRELATION_FACTOR);
			VariableSupport.writeScalarFloat(writer, CDFConstants.VARIABLE_ACTUAL_SAMPLING_INTERVAL, scanInterval);
			//
			for(IDataEntry entry : dimensionSupport.getDataEntries()) {
				monitor.subTask(entry.getVarName());
				writer.write(entry.getVarName(), entry.getValues());
			}
		} catch(IOException e) {
			logger.warn(e);
		} catch(InvalidRangeException e) {
			logger.warn(e);
		}
	}
}
