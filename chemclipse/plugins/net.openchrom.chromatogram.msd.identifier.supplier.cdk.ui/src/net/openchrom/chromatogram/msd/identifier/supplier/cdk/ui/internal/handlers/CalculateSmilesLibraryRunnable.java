/*******************************************************************************
 * Copyright (c) 2013, 2016 Dr. Philip Wenig, Marwin Wollschläger.
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

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.core.SmilesCalculator;

public class CalculateSmilesLibraryRunnable implements IRunnableWithProgress {

	private IMassSpectra massSpectra;

	public CalculateSmilesLibraryRunnable(IMassSpectra massSpectra) {
		this.massSpectra = massSpectra;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		try {
			monitor.beginTask("SMILES calculator", IProgressMonitor.UNKNOWN);
			SmilesCalculator smilesCalculator = new SmilesCalculator();
			smilesCalculator.calculate(massSpectra);
			updateSelection();
		} finally {
			monitor.done();
		}
	}

	/*
	 * Updates the selection using the GUI thread.
	 */
	private void updateSelection() {

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {

				// TODO
			}
		});
	}
}
