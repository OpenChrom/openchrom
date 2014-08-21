/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.ui.internal.handlers.DeleteIdentificationsWithoutFormulaRunnable;
import net.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.progress.core.InfoType;
import net.chemclipse.progress.core.StatusLineLogger;
import net.chemclipse.support.events.IChemClipseEvents;

public class DeleteIdentificationsWithoutFormulaHandler implements EventHandler {

	private static final Logger logger = Logger.getLogger(DeleteIdentificationsWithoutFormulaHandler.class);
	private static IChromatogramSelectionMSD chromatogramSelection;

	@Execute
	public void execute() {

		if(chromatogramSelection != null) {
			/*
			 * Ask before deleting the identification entries.
			 */
			final Display display = Display.getCurrent();
			MessageBox messageBox = new MessageBox(display.getActiveShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
			messageBox.setText("Delete Identifications");
			messageBox.setMessage("Do you really want to delete all identifications without formula?");
			int decision = messageBox.open();
			if(SWT.YES == decision) {
				/*
				 * Yes, delete.
				 */
				StatusLineLogger.setInfo(InfoType.MESSAGE, "Start deleting identifications without formula.");
				IRunnableWithProgress runnable = new DeleteIdentificationsWithoutFormulaRunnable(chromatogramSelection);
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
				StatusLineLogger.setInfo(InfoType.MESSAGE, "Done: All identifications without formula have been removed.");
			}
		}
	}

	@Override
	public void handleEvent(Event event) {

		if(event.getTopic().equals(IChemClipseEvents.TOPIC_CHROMATOGRAM_MSD_UPDATE_CHROMATOGRAM_SELECTION)) {
			chromatogramSelection = (IChromatogramSelectionMSD)event.getProperty(IChemClipseEvents.PROPERTY_CHROMATOGRAM_SELECTION);
		}
	}
}