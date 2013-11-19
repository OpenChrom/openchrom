/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig, Marwin Wollschläger.
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

import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

public class SmilesFormulaCalculatorRunnable implements IRunnableWithProgress {

	private IChromatogramSelectionMSD chromatogramSelection;
	private boolean useOnlySelectedPeak;
	private NameToStructure nameStructure;
	private NameToStructureConfig nameStructureConfig;

	public SmilesFormulaCalculatorRunnable(IChromatogramSelectionMSD chromatogramSelection, boolean useOnlySelectedPeak) {

		this.chromatogramSelection = chromatogramSelection;
		this.useOnlySelectedPeak = useOnlySelectedPeak;
		nameStructure = NameToStructure.getInstance();
		nameStructureConfig = new NameToStructureConfig();
		nameStructureConfig.setAllowRadicals(true); // TODO settings Preferences
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("Peak result formula calculator", IProgressMonitor.UNKNOWN);
			/*
			 * Get the list of selected peaks.
			 */
			List<IChromatogramPeakMSD> peaks;
			if(useOnlySelectedPeak) {
				/*
				 * Selected peak.
				 */
				peaks = new ArrayList<IChromatogramPeakMSD>();
				peaks.add(chromatogramSelection.getSelectedPeak());
			} else {
				/*
				 * All peaks
				 */
				peaks = chromatogramSelection.getChromatogramMSD().getPeaks(chromatogramSelection);
			}
			/*
			 * Calculate formula for each peak.
			 */
			for(IChromatogramPeakMSD peak : peaks) {
				/*
				 * Get the targets for each peak.
				 */
				List<IPeakTarget> targets = peak.getTargets();
				for(IPeakTarget target : targets) {
					/*
					 * Check if the peak is a peak identification entry.
					 */
					if(target instanceof IPeakIdentificationEntry) {
						ILibraryInformation libraryInformation = ((IPeakIdentificationEntry)target).getLibraryInformation();
						if(libraryInformation.getFormula().equals("")) {
							/*
							 * Calculate SMILES
							 */
							String name = libraryInformation.getName();
							OpsinResult result = nameStructure.parseChemicalName(name, nameStructureConfig);
							String message = result.getMessage();
							if(message.equals("")) {
								/*
								 * Set the parsed name and smiles formula.
								 */
								libraryInformation.setName(result.getChemicalName());
								libraryInformation.setFormula(result.getSmiles());
							} else {
								/*
								 * The name couldn't be parsed.
								 */
								libraryInformation.setComments(message);
							}
						}
					}
				}
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
