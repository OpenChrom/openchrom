/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
 *
 * All rights reserved.
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.handlers;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.swt.widgets.Shell;

public class ExtensionInstallHandler {

	@Execute
	void execute(Shell shell, ECommandService commandService, EHandlerService handlerService) {

		ProvisioningUI provisioningUI = ProvisioningUI.getDefaultUI();
		provisioningUI.openInstallWizard(null, null, null);
	}
}
