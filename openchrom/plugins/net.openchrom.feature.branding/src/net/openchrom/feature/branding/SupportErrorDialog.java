/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved.
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.feature.branding;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class SupportErrorDialog extends ErrorDialog {

	public SupportErrorDialog(Shell parentShell, String dialogTitle, String message, IStatus status) {

		super(parentShell, dialogTitle, message, status, IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR);
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		Control control = super.createDialogArea(parent);
		if(control instanceof Composite composite) {
			Link link = new Link(composite, SWT.NONE);
			link.setText("Contact <a href=\"mailto:support@lablicate.com\">support@lablicate.com</a> for further assistance.");
			GridData linkData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
			linkData.horizontalSpan = 2;
			link.setLayoutData(linkData);
		}
		return control;
	}
}