/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.fid.converter.supplier.cdf.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.chemclipse.chromatogram.converter.exceptions.FileIsEmptyException;
import net.chemclipse.chromatogram.converter.exceptions.FileIsNotReadableException;
import net.chemclipse.chromatogram.fid.converter.io.AbstractChromatogramFIDReader;
import net.chemclipse.chromatogram.fid.converter.io.IChromatogramFIDReader;
import net.chemclipse.chromatogram.fid.model.core.IChromatogramFID;
import net.chemclipse.chromatogram.model.core.IChromatogramOverview;

public class ChromatogramReader extends AbstractChromatogramFIDReader implements IChromatogramFIDReader {

	@Override
	public IChromatogramOverview readOverview(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return null;
	}

	@Override
	public IChromatogramFID read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return null;
	}
}
