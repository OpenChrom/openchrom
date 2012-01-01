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
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

public class PDFChromatogramWriter implements IPDFChromatogramWriter {

	@Override
	public void writeChromatogram(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

	}
}
