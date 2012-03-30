/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.converter.io.AbstractChromatogramWriter;
import net.openchrom.chromatogram.msd.converter.supplier.pdf.internal.io.PDFSupport;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

public class ChromatogramWriter extends AbstractChromatogramWriter {

	@Override
	public void writeChromatogram(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		PDFSupport pdfSupport = new PDFSupport();
		try {
			pdfSupport.exportChromatogram(file, chromatogram, monitor);
		} catch(Exception e) {
			throw new IOException(e);
		}
	}
}
