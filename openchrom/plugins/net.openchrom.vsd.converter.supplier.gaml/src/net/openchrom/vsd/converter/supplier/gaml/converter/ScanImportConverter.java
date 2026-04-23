/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.vsd.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.UnknownVersionException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.vsd.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.vsd.model.core.ISpectrumVSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.vsd.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.vsd.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.vsd.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.vsd.converter.supplier.gaml.model.IVendorSpectrumVSD;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader100;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanImportConverter extends AbstractScanImportConverter {

	private static final Logger logger = Logger.getLogger(ScanImportConverter.class);

	@Override
	public IProcessingInfo<ISpectrumVSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<ISpectrumVSD> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();

			final String header = new String(charBuffer);
			if(header.contains(Reader100.VERSION)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				IVendorSpectrumVSD vendorScan = scanReader.read(file);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader110.VERSION)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				IVendorSpectrumVSD vendorScan = scanReader.read(file);
				processingInfo.setProcessingResult(vendorScan);
			} else if(header.contains(Reader120.VERSION)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				IVendorSpectrumVSD vendorScan = scanReader.read(file);
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
