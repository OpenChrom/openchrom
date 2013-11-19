/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig, Marwin Wollschläger.
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
package net.openchrom.supplier.cdk.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.chromatogram.msd.model.core.selection.IChromatogramSelectionMSD;
import net.openchrom.logging.core.Logger;
import net.openchrom.progress.core.InfoType;
import net.openchrom.progress.core.StatusLineLogger;
import net.openchrom.supplier.cdk.ui.internal.handlers.SmilesFormulaCalculatorRunnable;
import net.openchrom.support.events.IOpenChromEvents;

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

		if(event.getTopic().equals(IOpenChromEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			chromatogramSelection = (IChromatogramSelectionMSD)event.getProperty(IOpenChromEvents.PROPERTY_CHROMATOGRAM_SELECTION);
		}
	}
}