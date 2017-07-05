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
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.converter.processing.chromatogram.IChromatogramWSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.ChromatogramSelectionWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ChromatogramImportRunnable implements IRunnableWithProgress {

	private static final Logger logger = Logger.getLogger(ChromatogramImportRunnable.class);
	//
	private List<IChromatogramSelectionWSD> chromatogramSelections;
	private String pathChromatogramSample;
	private String pathChromatogramReference;

	public ChromatogramImportRunnable(String pathChromatogramSample, String pathChromatogramReference) {
		chromatogramSelections = new ArrayList<IChromatogramSelectionWSD>();
		this.pathChromatogramSample = pathChromatogramSample;
		this.pathChromatogramReference = pathChromatogramReference;
	}

	public List<IChromatogramSelectionWSD> getChromatogramSelections() {

		return chromatogramSelections;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		IChromatogramWSD referenceChromatogram = importChromatogram(pathChromatogramSample, monitor);
		if(referenceChromatogram != null) {
			chromatogramSelections.add(new ChromatogramSelectionWSD(referenceChromatogram));
		}
		//
		IChromatogramWSD isotopeChromatogram = importChromatogram(pathChromatogramReference, monitor);
		if(isotopeChromatogram != null) {
			chromatogramSelections.add(new ChromatogramSelectionWSD(isotopeChromatogram));
		}
	}

	public IChromatogramWSD importChromatogram(String chromatogramPath, IProgressMonitor monitor) {

		IChromatogramWSD chromatogramWSD = null;
		try {
			File file = new File(chromatogramPath);
			IChromatogramWSDImportConverterProcessingInfo processingInfo = ChromatogramConverterWSD.convert(file, monitor);
			chromatogramWSD = processingInfo.getChromatogram();
		} catch(Exception e) {
			logger.warn(e);
		}
		return chromatogramWSD;
	}
}
