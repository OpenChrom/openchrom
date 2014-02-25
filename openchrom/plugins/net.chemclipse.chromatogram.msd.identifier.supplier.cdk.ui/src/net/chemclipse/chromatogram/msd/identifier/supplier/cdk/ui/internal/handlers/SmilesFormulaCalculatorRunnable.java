/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig, Marwin Wollschläger.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.chemclipse.chromatogram.msd.identifier.peak.PeakIdentifier;
import net.chemclipse.chromatogram.msd.model.core.IPeakMSD;
import net.chemclipse.chromatogram.msd.model.core.selection.ChromatogramSelectionMSD;
import net.chemclipse.chromatogram.msd.model.core.selection.IChromatogramSelectionMSD;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public class SmilesFormulaCalculatorRunnable implements IRunnableWithProgress {

	private static final String IDENTIFIER_ID = "net.chemclipse.chromatogram.msd.identifier.supplier.cdk";
	private IChromatogramSelectionMSD chromatogramSelection;
	private boolean useOnlySelectedPeak;

	public SmilesFormulaCalculatorRunnable(IChromatogramSelectionMSD chromatogramSelection, boolean useOnlySelectedPeak) {

		this.chromatogramSelection = chromatogramSelection;
		this.useOnlySelectedPeak = useOnlySelectedPeak;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("Peak result formula calculator", IProgressMonitor.UNKNOWN);
			/*
			 * Get the list of selected peaks.
			 */
			List<IPeakMSD> peaks = new ArrayList<IPeakMSD>();
			if(useOnlySelectedPeak) {
				/*
				 * Selected peak.
				 */
				peaks.add(chromatogramSelection.getSelectedPeak());
			} else {
				/*
				 * All peaks
				 */
				for(IPeakMSD peakMSD : chromatogramSelection.getChromatogramMSD().getPeaks(chromatogramSelection)) {
					peaks.add(peakMSD);
				}
			}
			/*
			 * Run the peak identifier.
			 */
			PeakIdentifier.identify(peaks, IDENTIFIER_ID, monitor);
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
