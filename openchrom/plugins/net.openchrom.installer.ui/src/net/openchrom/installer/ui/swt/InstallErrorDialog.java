/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class InstallErrorDialog {

	private static final Logger logger = Logger.getLogger(InstallErrorDialog.class);

	public static void notifyError(Shell parentShell, String message, Exception e) {

		StatusLineLogger.setInfo(InfoType.ERROR_MESSAGE, message);
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {

				MultiStatus status = createMultiStatus(e);
				String errorMessage = message + System.lineSeparator() + System.lineSeparator();
				errorMessage = errorMessage + "Visit faq.openchrom.net for solutions to common problems." + System.lineSeparator();
				errorMessage = errorMessage + "Contact support@lablicate.com if the problem persists.";
				ErrorDialog.openError(parentShell, "Plug-in Installation", errorMessage, status);
			}
		});
		logger.error(e);
	}

	private static MultiStatus createMultiStatus(Throwable t) {

		List<Status> childStatuses = new ArrayList<>();
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		for(StackTraceElement stackTrace : stackTraces) {
			Status status = new Status(IStatus.ERROR, "net.openchrom.auth.ui", stackTrace.toString());
			childStatuses.add(status);
		}
		return new MultiStatus("net.openchrom.installer.ui", IStatus.ERROR, childStatuses.toArray(new Status[]{}), t.toString(), t);
	}
}