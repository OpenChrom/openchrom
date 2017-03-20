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
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ChromatogramImportRunnable implements IRunnableWithProgress {

	private static final Logger logger = Logger.getLogger(ChromatogramImportRunnable.class);
	//
	private List<IChromatogramSelection> chromatogramSelections;
	private String pathChromatogramC12;
	private String pathChromatogramC13;

	public ChromatogramImportRunnable(String pathChromatogramC12, String pathChromatogramC13) {
		chromatogramSelections = new ArrayList<IChromatogramSelection>();
		this.pathChromatogramC12 = pathChromatogramC12;
		this.pathChromatogramC13 = pathChromatogramC13;
	}

	public List<IChromatogramSelection> getChromatogramSelections() {

		return chromatogramSelections;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		IChromatogramSelectionMSD chromatogramSelectionC12 = importChromatogram(pathChromatogramC12, monitor);
		if(chromatogramSelectionC12 != null) {
			chromatogramSelections.add(chromatogramSelectionC12);
		}
		//
		IChromatogramSelectionMSD chromatogramSelectionC13 = importChromatogram(pathChromatogramC13, monitor);
		if(chromatogramSelectionC13 != null) {
			chromatogramSelections.add(chromatogramSelectionC13);
		}
	}

	public IChromatogramSelectionMSD importChromatogram(String chromatogramPath, IProgressMonitor monitor) {

		IChromatogramSelectionMSD chromatogramSelectionMSD = null;
		try {
			/*
			 * Import the chromatogram.
			 */
			File file = new File(chromatogramPath);
			IChromatogramMSDImportConverterProcessingInfo processingInfo = ChromatogramConverterMSD.convert(file, monitor);
			IChromatogramMSD chromatogramMSD = processingInfo.getChromatogram();
			chromatogramSelectionMSD = new ChromatogramSelectionMSD(chromatogramMSD);
		} catch(Exception e) {
			logger.warn(e);
		}
		return chromatogramSelectionMSD;
	}
}
