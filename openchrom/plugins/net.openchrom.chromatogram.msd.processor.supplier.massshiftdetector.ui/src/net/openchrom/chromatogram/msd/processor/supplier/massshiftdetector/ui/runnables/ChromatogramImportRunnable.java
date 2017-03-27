/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ChromatogramImportRunnable implements IRunnableWithProgress {

	private static final Logger logger = Logger.getLogger(ChromatogramImportRunnable.class);
	//
	private List<IChromatogramMSD> chromatograms;
	private String pathChromatogramReference;
	private String pathChromatogramIsotope;

	public ChromatogramImportRunnable(String pathChromatogramReference, String pathChromatogramIsotope) {
		chromatograms = new ArrayList<IChromatogramMSD>();
		this.pathChromatogramReference = pathChromatogramReference;
		this.pathChromatogramIsotope = pathChromatogramIsotope;
	}

	public List<IChromatogramMSD> getChromatograms() {

		return chromatograms;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		IChromatogramMSD referenceChromatogram = importChromatogram(pathChromatogramReference, monitor);
		if(referenceChromatogram != null) {
			chromatograms.add(referenceChromatogram);
		}
		//
		IChromatogramMSD isotopeChromatogram = importChromatogram(pathChromatogramIsotope, monitor);
		if(isotopeChromatogram != null) {
			chromatograms.add(isotopeChromatogram);
		}
	}

	public IChromatogramMSD importChromatogram(String chromatogramPath, IProgressMonitor monitor) {

		IChromatogramMSD chromatogramMSD = null;
		try {
			/*
			 * Import the chromatogram.
			 */
			File file = new File(chromatogramPath);
			IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(file, monitor);
			chromatogramMSD = processingInfo.getChromatogram();
		} catch(Exception e) {
			logger.warn(e);
		}
		return chromatogramMSD;
	}
}
