/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramExportConverter;
import net.openchrom.chromatogram.msd.converter.exceptions.FileIsNotWriteableException;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

public class PDFChromatogramExportConverter extends AbstractChromatogramExportConverter {

	@Override
	public File convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		return null;
	}
}
