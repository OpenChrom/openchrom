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
package net.openchrom.wsd.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.UnknownVersionException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.wsd.converter.core.IScanImportConverter;
import org.eclipse.chemclipse.wsd.model.core.ISpectrumWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.wsd.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.wsd.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.wsd.converter.supplier.gaml.model.IVendorSpectrumWSD;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader100;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanImportConverter extends AbstractScanImportConverter implements IScanImportConverter {

	private static final Logger logger = Logger.getLogger(ScanImportConverter.class);

	@Override
	public IProcessingInfo<ISpectrumWSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<ISpectrumWSD> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();

			final String header = new String(charBuffer);
			if(header.contains(Reader100.VERSION)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				IVendorSpectrumWSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader110.VERSION)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				IVendorSpectrumWSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader120.VERSION)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				IVendorSpectrumWSD vendorScan = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(vendorScan);
			} else {
				throw new UnknownVersionException();
			}
		} catch(IOException e) {
			processingInfo.addErrorMessage("GAML Spectroscopy", "Could not import file.", e);
			logger.warn(e);
		}
		return processingInfo;
	}
}
