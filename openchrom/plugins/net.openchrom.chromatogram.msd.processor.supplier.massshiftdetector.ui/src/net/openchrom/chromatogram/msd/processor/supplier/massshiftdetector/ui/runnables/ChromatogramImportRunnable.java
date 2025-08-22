/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.msd.converter.chromatogram.ChromatogramConverterMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ChromatogramImportRunnable implements IRunnableWithProgress {

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
