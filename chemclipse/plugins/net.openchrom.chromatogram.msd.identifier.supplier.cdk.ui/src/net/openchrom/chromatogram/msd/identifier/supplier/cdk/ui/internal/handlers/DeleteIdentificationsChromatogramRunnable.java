/*******************************************************************************
 * Copyright (c) 2013, 2017 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.targets.IPeakTarget;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IMassSpectrumTarget;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public class DeleteIdentificationsChromatogramRunnable implements IRunnableWithProgress {

	private IChromatogramSelectionMSD chromatogramSelection;

	public DeleteIdentificationsChromatogramRunnable(IChromatogramSelectionMSD chromatogramSelection) {
		this.chromatogramSelection = chromatogramSelection;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("SMILES calculator", IProgressMonitor.UNKNOWN);
			/*
			 * Scans
			 */
			IChromatogramMSD chromatogram = chromatogramSelection.getChromatogramMSD();
			int startScan = chromatogram.getScanNumber(chromatogramSelection.getStartRetentionTime());
			int stopScan = chromatogram.getScanNumber(chromatogramSelection.getStopRetentionTime());
			/*
			 * Scans
			 */
			for(int scan = startScan; scan <= stopScan; scan++) {
				IScan supplierScan = chromatogram.getScan(scan);
				if(supplierScan instanceof IScanMSD) {
					IScanMSD scanMSD = (IScanMSD)supplierScan;
					/*
					 * Scan
					 */
					if(scanMSD.getTargets().size() > 0) {
						deleteTargets(scanMSD);
					}
					/*
					 * Optimized Scan.
					 */
					IScanMSD optimizedMassSpectrum = scanMSD.getOptimizedMassSpectrum();
					if(optimizedMassSpectrum != null) {
						if(optimizedMassSpectrum.getTargets().size() > 0) {
							deleteTargets(scanMSD);
						}
					}
				}
			}
			/*
			 * Peaks
			 */
			List<IChromatogramPeakMSD> peaks = chromatogramSelection.getChromatogramMSD().getPeaks(chromatogramSelection);
			deleteTargets(peaks);
			/*
			 * Fire an update.
			 */
			updateSelection();
		} finally {
			monitor.done();
		}
	}

	private void deleteTargets(IScanMSD scanMSD) {

		/*
		 * Get the targets for each peak.
		 */
		List<IMassSpectrumTarget> targets = scanMSD.getTargets();
		List<IMassSpectrumTarget> targetsToDelete = new ArrayList<IMassSpectrumTarget>();
		for(IMassSpectrumTarget target : targets) {
			/*
			 * Check if the peak is a peak identification entry.
			 */
			if(target instanceof IMassSpectrumTarget) {
				ILibraryInformation libraryInformation = ((IMassSpectrumTarget)target).getLibraryInformation();
				if(libraryInformation.getSmiles().equals("")) {
					targetsToDelete.add(target);
				}
			}
		}
		/*
		 * Delete each marked entry in the selected peak.
		 */
		scanMSD.removeTargets(targetsToDelete);
	}

	private void deleteTargets(List<IChromatogramPeakMSD> peaks) {

		for(IChromatogramPeakMSD peak : peaks) {
			/*
			 * Get the targets for each peak.
			 */
			List<IPeakTarget> targets = peak.getTargets();
			List<IPeakTarget> targetsToDelete = new ArrayList<IPeakTarget>();
			for(IPeakTarget target : targets) {
				/*
				 * Check if the peak is a peak identification entry.
				 */
				if(target instanceof IPeakTarget) {
					ILibraryInformation libraryInformation = ((IPeakTarget)target).getLibraryInformation();
					if(libraryInformation.getSmiles().equals("")) {
						targetsToDelete.add(target);
					}
				}
			}
			/*
			 * Delete each marked entry in the selected peak.
			 */
			peak.removeTargets(targetsToDelete);
		}
	}

	// ---------------------------------------------------------private methods
	/*
	 * Updates the selection using the GUI thread.
	 */
	private void updateSelection() {

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {

				if(chromatogramSelection instanceof ChromatogramSelectionMSD) {
					((ChromatogramSelectionMSD)chromatogramSelection).update(false);
				}
			}
		});
	}
	// ---------------------------------------------------------private methods
}
