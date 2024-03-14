/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.discovery;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.installer.ui.wizards.PluginDiscoveryWizard;

public class PluginInstallHandler {

	private static final Logger logger = Logger.getLogger(PluginInstallHandler.class);

	@Execute
	void execute(Shell shell, ECommandService commandService, EHandlerService handlerService) {

		try {
			IPluginInstallJob installJob = new PrepareInstallProfileJob();
			PluginDiscoveryWizard wizard = new PluginDiscoveryWizard(installJob);
			WizardDialog dialog = new WizardDialog(DisplayUtils.getShell(), wizard);
			dialog.open();
		} catch(IllegalArgumentException e) {
			logger.warn(e);
		}
	}
}