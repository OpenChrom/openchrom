/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.swt;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.progress.core.InfoType;
import org.eclipse.chemclipse.progress.core.StatusLineLogger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.feature.branding.SupportErrorDialog;

public class InstallErrorDialog {

	private static final Logger logger = Logger.getLogger(InstallErrorDialog.class);

	public static void notifyError(Shell parentShell, String message, Exception e) {

		StatusLineLogger.setInfo(InfoType.ERROR_MESSAGE, message);
		Display.getDefault().syncExec(() -> {

			MultiStatus status = createMultiStatus(e);
			SupportErrorDialog dialog = new SupportErrorDialog(parentShell, "Plug-in Installation", message, status);
			dialog.open();
		});
		logger.error(e);
	}

	static MultiStatus createMultiStatus(Throwable t) {

		IStatus[] children = (t instanceof CoreException coreException) ? coreException.getStatus().getChildren() : new IStatus[0];
		String reason = (t instanceof CoreException coreException) ? buildReason(coreException.getStatus()) : t.getMessage();
		return new MultiStatus("net.openchrom.installer.ui", IStatus.ERROR, children, reason, t);
	}

	/**
	 * p2 buries the actual explanation of a resolution failure deep inside a tree of generic wrapper statuses
	 * (e.g. "Operation details", "Cannot satisfy dependency:"). Only the leaves of that tree (statuses without
	 * children) ever carry concrete facts ("Missing requirement: ...", "From: ...", "To: ...", conflicting IU
	 * names, ...), so collect those and skip every wrapper in between.
	 */
	private static String buildReason(IStatus status) {

		Set<String> lines = new LinkedHashSet<>();
		collectReasonLines(status, lines);
		return lines.isEmpty() ? status.getMessage() : String.join("\n", lines);
	}

	private static void collectReasonLines(IStatus status, Set<String> lines) {

		IStatus[] children = status.getChildren();
		if(children.length == 0) {
			lines.add(status.getMessage());
			return;
		}
		for(IStatus child : children) {
			collectReasonLines(child, lines);
		}
	}
}
