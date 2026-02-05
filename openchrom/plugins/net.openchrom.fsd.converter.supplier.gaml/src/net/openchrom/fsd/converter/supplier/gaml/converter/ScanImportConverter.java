/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.fsd.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.UnknownVersionException;
import org.eclipse.chemclipse.fsd.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.fsd.converter.core.IScanImportConverter;
import org.eclipse.chemclipse.fsd.model.core.ISpectrumFSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.fsd.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.fsd.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.fsd.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.fsd.converter.supplier.gaml.model.IVendorSpectrumFSD;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader100;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanImportConverter extends AbstractScanImportConverter implements IScanImportConverter {

	private static final Logger logger = Logger.getLogger(ScanImportConverter.class);

	@Override
	public IProcessingInfo<ISpectrumFSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<ISpectrumFSD> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();

			final String header = new String(charBuffer);
			if(header.contains(Reader100.VERSION)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				IVendorSpectrumFSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader110.VERSION)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				IVendorSpectrumFSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader120.VERSION)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				IVendorSpectrumFSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else {
				throw new UnknownVersionException();
			}
		} catch(IOException e) {
			processingInfo.addErrorMessage("GAML Spectroscopy", "Could not import file.");
			logger.error(e);
		}
		return processingInfo;
	}
}
