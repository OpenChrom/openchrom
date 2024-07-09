/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.gaml.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.converter.io.IChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.xxd.converter.supplier.io.exception.UnknownVersionException;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.converter.supplier.gaml.io.Reader120;

public class ChromatogramReader extends AbstractChromatogramWSDReader implements IChromatogramWSDReader {

	public static IChromatogramWSDReader getReader(final File file) throws IOException {

		IChromatogramWSDReader chromatogramReader = null;
		//
		final char[] charBuffer = new char[100];
		try (FileReader fileReader = new FileReader(file)) {
			fileReader.read(charBuffer);
		}
		//
		final String header = new String(charBuffer);
		if(header.contains(Reader120.VERSION)) {
			chromatogramReader = new ChromatogramReaderVersion120();
		} else {
			throw new UnknownVersionException();
		}
		//
		return chromatogramReader;
	}

	@Override
	public IChromatogramWSD read(final File file, final IProgressMonitor monitor) throws IOException {

		final IChromatogramWSDReader chromatogramReader = getReader(file);
		return chromatogramReader.read(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(final File file, final IProgressMonitor monitor) throws IOException {

		final IChromatogramWSDReader chromatogramReader = getReader(file);
		return chromatogramReader.readOverview(file, monitor);
	}
}
