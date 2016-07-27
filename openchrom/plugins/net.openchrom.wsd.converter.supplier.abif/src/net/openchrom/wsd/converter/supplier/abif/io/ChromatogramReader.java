/*******************************************************************************
 * Copyright (c) 2016 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.wsd.converter.io.AbstractChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.abif.internal.support.ChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.internal.support.IChromatogramArrayReader;
import net.openchrom.wsd.converter.supplier.abif.model.IVendorChromatogram;
import net.openchrom.wsd.converter.supplier.abif.model.VendorChromatogram;

public class ChromatogramReader extends AbstractChromatogramWSDReader {

	@Override
	public IChromatogramWSD read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readChromatogram(file, monitor);
	}

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return readChromatogram(file, monitor);
	}

	private IChromatogramWSD readChromatogram(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IChromatogramArrayReader in = new ChromatogramArrayReader(file);
		/*
		 * Chromatogram
		 */
		IVendorChromatogram chromatogram = new VendorChromatogram();
		chromatogram.setConverterId(""); // If the chromatogram shall be exportable, set the id otherwise its null or "".
		chromatogram.setFile(file);
		in.resetPosition();
		/*
		 * Read and check the magic bytes
		 */
		String magicNumber = in.readBytesAsString(4);
		if(!magicNumber.equals("ABIF")) {
			throw new FileIsNotReadableException("Not an ABIF sequence trace file.");
		}
		/*
		 * Read and check the version
		 */
		short version = in.read2BShortBE();
		if(version / 100 != 1)
			throw new FileIsNotReadableException("ABIF files other than major version 1 are not supported.");
		chromatogram.setVersion(version);
		return chromatogram;
	}
}
