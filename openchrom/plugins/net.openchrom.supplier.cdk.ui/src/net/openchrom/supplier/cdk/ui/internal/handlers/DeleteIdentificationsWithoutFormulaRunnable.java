/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.openchrom.chromatogram.model.identifier.ILibraryInformation;
import net.openchrom.chromatogram.model.targets.IPeakTarget;
import net.openchrom.chromatogram.msd.model.core.IChromatogramPeakMSD;
import net.openchrom.chromatogram.msd.model.core.identifier.peak.IPeakIdentificationEntry;
import net.openchrom.chromatogram.msd.model.core.selection.ChromatogramSelectionMSD;
import net.openchrom.chromatogram.msd.model.core.selection.IChromatogramSelectionMSD;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public class DeleteIdentificationsWithoutFormulaRunnable implements IRunnableWithProgress {

	private IChromatogramSelectionMSD chromatogramSelection;

	public DeleteIdentificationsWithoutFormulaRunnable(IChromatogramSelectionMSD chromatogramSelection) {

		this.chromatogramSelection = chromatogramSelection;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("Peak result formula calculator", IProgressMonitor.UNKNOWN);
			/*
			 * Get the list of selected peaks.
			 */
			List<IChromatogramPeakMSD> peaks = chromatogramSelection.getChromatogramMSD().getPeaks(chromatogramSelection);
			/*
			 * Calculate formula for each peak.
			 */
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
					if(target instanceof IPeakIdentificationEntry) {
						ILibraryInformation libraryInformation = ((IPeakIdentificationEntry)target).getLibraryInformation();
						if(libraryInformation.getFormula().equals("")) {
							/*
							 * Mark the identification entry to be deleted when there is no formula set.
							 */
							targetsToDelete.add(target);
						}
					}
				}
				/*
				 * Delete each marked entry in the selected peak.
				 */
				peak.removeTargets(targetsToDelete);
			}
			/*
			 * Fire an update.
			 */
			updateSelection();
		} finally {
			monitor.done();
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
