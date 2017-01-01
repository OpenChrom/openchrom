/*******************************************************************************
 * Copyright (c) 2013, 2017 Dr. Philip Wenig, Marwin Wollschläger.
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
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.internal.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.chemclipse.chromatogram.msd.identifier.chromatogram.ChromatogramIdentifier;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

public class CalculateSmilesChromatogramRunnable implements IRunnableWithProgress {

	private static final String IDENTIFIER_ID = "net.openchrom.chromatogram.msd.identifier.supplier.cdk";
	//
	private IChromatogramSelectionMSD chromatogramSelection;

	public CalculateSmilesChromatogramRunnable(IChromatogramSelectionMSD chromatogramSelection) {
		this.chromatogramSelection = chromatogramSelection;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("SMILES calculator", IProgressMonitor.UNKNOWN);
			ChromatogramIdentifier.identify(chromatogramSelection, IDENTIFIER_ID, monitor);
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
