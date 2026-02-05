/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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
package net.openchrom.nmr.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.chemclipse.converter.exceptions.UnknownVersionException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.nmr.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.nmr.converter.core.IScanImportConverter;
import org.eclipse.chemclipse.nmr.model.core.ISpectrumNMR;
import org.eclipse.chemclipse.nmr.model.core.SpectrumNMR;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader100;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanImportConverter extends AbstractScanImportConverter implements IScanImportConverter {

	private static final Logger logger = Logger.getLogger(ScanImportConverter.class);

	public ScanImportConverter() {

		super();
	}

	@Override
	public IProcessingInfo<ISpectrumNMR> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<ISpectrumNMR> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();

			Collection<IComplexSignalMeasurement<?>> complexSignalMeasurement = null;
			final String header = new String(charBuffer);
			if(header.contains(Reader100.VERSION)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				complexSignalMeasurement = scanReader.read(file, monitor);
			} else if(header.contains(Reader110.VERSION)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				complexSignalMeasurement = scanReader.read(file, monitor);
			} else if(header.contains(Reader120.VERSION)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				complexSignalMeasurement = scanReader.read(file, monitor);
			} else {
				throw new UnknownVersionException();
			}
			ISpectrumNMR spectrumNMR = new SpectrumNMR();
			spectrumNMR.setComplexSignalMeasurements(complexSignalMeasurement);
			processingInfo.setProcessingResult(spectrumNMR);
		} catch(IOException e) {
			processingInfo.addErrorMessage("GAML NMR", "There was a problem during file import.");
			logger.error(e);
		}
		return processingInfo;
	}
}
