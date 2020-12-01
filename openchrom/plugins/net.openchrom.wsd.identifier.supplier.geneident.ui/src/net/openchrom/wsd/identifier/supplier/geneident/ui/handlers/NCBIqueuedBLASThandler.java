/*******************************************************************************
 * Copyright (c) 2016, 2020 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactoring events
 *******************************************************************************/
package net.openchrom.wsd.identifier.supplier.geneident.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Named;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.chemclipse.support.events.IChemClipseEvents;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.openchrom.wsd.identifier.supplier.geneident.ui.internal.runnables.NCBIqueuedBLASTRunnable;

public class NCBIqueuedBLASThandler implements EventHandler {

	private static final Logger logger = Logger.getLogger(NCBIqueuedBLASThandler.class);
	private static IChromatogramSelection<?, ?> chromatogramSelection;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_PART) MPart part) {

		if(chromatogramSelection != null && chromatogramSelection instanceof IChromatogramSelectionWSD) {
			StatusLineLogger.setInfo(InfoType.MESSAGE, "Running new queued BLAST search.");
			ProgressMonitorDialog monitor = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
			try {
				// TODO: Make this minimizeable and cancelable with appropriate cleanups.
				monitor.run(true, false, new NCBIqueuedBLASTRunnable((IChromatogramSelectionWSD)chromatogramSelection));
			} catch(InvocationTargetException e) {
				logger.warn(e);
			} catch(InterruptedException e) {
				logger.warn(e);
			}
		}
	}

	@Override
	public void handleEvent(Event event) {

		if(event.getTopic().equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_XXD_UPDATE_SELECTION)) {
			chromatogramSelection = (IChromatogramSelection<?, ?>)event.getProperty(IChemClipseEvents.EVENT_BROKER_DATA);
		} else {
			chromatogramSelection = null;
		}
	}
}
