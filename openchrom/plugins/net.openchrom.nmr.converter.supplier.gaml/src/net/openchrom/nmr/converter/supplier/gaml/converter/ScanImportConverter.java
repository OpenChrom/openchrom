/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.gaml.converter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.nmr.converter.core.AbstractScanImportConverter;
import org.eclipse.chemclipse.nmr.converter.core.IScanImportConverter;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.chemclipse.converter.exceptions.UnknownVersionException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion100;
import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion110;
import net.openchrom.nmr.converter.supplier.gaml.io.ScanReaderVersion120;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader100;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader110;
import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ScanImportConverter extends AbstractScanImportConverter<Collection<IComplexSignalMeasurement<?>>> implements IScanImportConverter<Collection<IComplexSignalMeasurement<?>>> {

	public ScanImportConverter() {

		super();
	}

	@Override
	public IProcessingInfo<Collection<IComplexSignalMeasurement<?>>> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<Collection<IComplexSignalMeasurement<?>>> processingInfo = new ProcessingInfo<>();
		try {
			final FileReader fileReader = new FileReader(file);
			final char[] charBuffer = new char[100];
			fileReader.read(charBuffer);
			fileReader.close();
			//
			final String header = new String(charBuffer);
			if(header.contains(Reader100.VERSION)) {
				ScanReaderVersion100 scanReader = new ScanReaderVersion100();
				Collection<IComplexSignalMeasurement<?>> result = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(result);
			} else if(header.contains(Reader110.VERSION)) {
				ScanReaderVersion110 scanReader = new ScanReaderVersion110();
				Collection<IComplexSignalMeasurement<?>> result = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(result);
			} else if(header.contains(Reader120.VERSION)) {
				ScanReaderVersion120 scanReader = new ScanReaderVersion120();
				Collection<IComplexSignalMeasurement<?>> result = scanReader.read(file, monitor);
				processingInfo.setProcessingResult(result);
			} else {
				throw new UnknownVersionException();
			}
		} catch(IOException e) {
			processingInfo.addErrorMessage("GAML NMR", "There was a problem during file import.", e);
		}
		return processingInfo;
	}
}
