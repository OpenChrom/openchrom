/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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
package net.openchrom.installer.ui.handlers;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.wizard.WizardDialog;

import net.openchrom.installer.ui.discovery.IPluginInstallJob;
import net.openchrom.installer.ui.discovery.PrepareInstallProfileJob;
import net.openchrom.installer.ui.wizards.PluginDiscoveryWizard;

public class AddonsInstallHandler {

	private static final Logger logger = Logger.getLogger(AddonsInstallHandler.class);

	@Execute
	void execute() {

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