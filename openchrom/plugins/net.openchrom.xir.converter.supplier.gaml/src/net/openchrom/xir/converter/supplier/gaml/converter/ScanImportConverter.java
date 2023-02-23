/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xir.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.xir.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.xir.converter.core.IScanImportConverter;
import org.eclipse.chemclipse.xxd.converter.supplier.io.exception.UnknownVersionException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xir.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.xir.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.xir.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.xir.converter.supplier.gaml.model.IVendorSpectrumXIR;
import net.openchrom.xxd.converter.supplier.gaml.internal.io.IConstants;

@SuppressWarnings("rawtypes")
public class ScanImportConverter extends AbstractScanImportConverter implements IScanImportConverter {

	private static final Logger logger = Logger.getLogger(ScanImportConverter.class);

	@Override
	public IProcessingInfo<IVendorSpectrumXIR> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IVendorSpectrumXIR> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();
			//
			final String header = new String(charBuffer);
			if(header.contains(IConstants.GAML_V_100)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				IVendorSpectrumXIR vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(IConstants.GAML_V_110)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				IVendorSpectrumXIR vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(IConstants.GAML_V_120)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				IVendorSpectrumXIR vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else {
				throw new UnknownVersionException();
			}
		} catch(IOException e) {
			processingInfo.addErrorMessage("GAML Spectroscopy", "There was a problem to import the file.");
			logger.warn(e);
		}
		return processingInfo;
	}
}
