/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig, Marwin Wollschläger.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.internal.handlers.SmilesFormulaCalculatorRunnable;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;

public class CalculateSmilesFormulaPeaksHandler implements EventHandler {

	private static final Logger logger = Logger.getLogger(CalculateSmilesFormulaPeaksHandler.class);
	private static IChromatogramSelectionMSD chromatogramSelection;

	@Execute
	public void execute() {

		if(chromatogramSelection != null) {
			final Display display = Display.getCurrent();
			StatusLineLogger.setInfo(InfoType.MESSAGE, "Start peak formula calculation.");
			IRunnableWithProgress runnable = new SmilesFormulaCalculatorRunnable(chromatogramSelection, false);
			ProgressMonitorDialog monitor = new ProgressMonitorDialog(display.getActiveShell());
			try {
				/*
				 * Use true, true ... instead of false, true ... if the progress bar
				 * should be shown in action.
				 */
				monitor.run(true, true, runnable);
			} catch(InvocationTargetException e) {
				logger.warn(e);
			} catch(InterruptedException e) {
				logger.warn(e);
			}
			StatusLineLogger.setInfo(InfoType.MESSAGE, "Done: Peak result formulas calculated.");
		}
	}

	@Override
	public void handleEvent(Event event) {

		if(event.getTopic().equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			chromatogramSelection = (IChromatogramSelectionMSD)event.getProperty(IChemClipseEvents.PROPERTY_CHROMATOGRAM_SELECTION);
		}
	}
}