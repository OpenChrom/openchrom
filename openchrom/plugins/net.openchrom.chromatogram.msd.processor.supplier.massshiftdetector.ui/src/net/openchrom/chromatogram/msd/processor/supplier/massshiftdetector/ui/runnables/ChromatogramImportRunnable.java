/*******************************************************************************
 * Copyright (c) 2017, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ChromatogramImportRunnable implements IRunnableWithProgress {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ChromatogramImportRunnable.class);
	//
	private List<IChromatogramSelectionMSD> chromatogramSelections;
	private String pathChromatogramReference;
	private String pathChromatogramIsotope;

	public ChromatogramImportRunnable(String pathChromatogramReference, String pathChromatogramIsotope) {
		chromatogramSelections = new ArrayList<>();
		this.pathChromatogramReference = pathChromatogramReference;
		this.pathChromatogramIsotope = pathChromatogramIsotope;
	}

	public List<IChromatogramSelectionMSD> getChromatogramSelections() {

		return chromatogramSelections;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		IChromatogramMSD referenceChromatogram = importChromatogram(pathChromatogramReference, monitor);
		if(referenceChromatogram != null) {
			chromatogramSelections.add(new ChromatogramSelectionMSD(referenceChromatogram));
		}
		//
		IChromatogramMSD isotopeChromatogram = importChromatogram(pathChromatogramIsotope, monitor);
		if(isotopeChromatogram != null) {
			chromatogramSelections.add(new ChromatogramSelectionMSD(isotopeChromatogram));
		}
	}

	public IChromatogramMSD importChromatogram(String chromatogramPath, IProgressMonitor monitor) {

		IChromatogramMSD chromatogramMSD = null;
		File file = new File(chromatogramPath);
		IProcessingInfo<IChromatogramMSD> processingInfo = ChromatogramConverterMSD.getInstance().convert(file, monitor);
		chromatogramMSD = processingInfo.getProcessingResult();
		return chromatogramMSD;
	}
}
