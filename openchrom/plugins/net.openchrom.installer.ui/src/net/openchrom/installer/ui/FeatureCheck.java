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
 * Philip Wenig - refactoring vibrational spectroscopy
 *******************************************************************************/
package net.openchrom.installer.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.Strings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;

import net.openchrom.installer.model.IPluginDescriptor;
import net.openchrom.installer.model.PluginDescriptor;
import net.openchrom.installer.model.PluginDescriptorKind;
import net.openchrom.installer.model.PluginDiscoveryExtensionReader;
import net.openchrom.installer.preferences.PreferenceSupplier;
import net.openchrom.installer.ui.discovery.IPluginInstallJob;
import net.openchrom.installer.ui.discovery.PrepareInstallProfileJob;
import net.openchrom.installer.ui.wizards.PluginDiscoveryWizard;

public class FeatureCheck implements IStartup {

	private static final Logger logger = Logger.getLogger(FeatureCheck.class);

	@Override
	public void earlyStartup() {

		for(String feature : getConverterFeatures()) {
			Bundle bundle = Platform.getBundle(feature);
			if(bundle != null) {
				return;
			}
		}
		/*
		 * Show dialog on demand.
		 */
		if(!PreferenceSupplier.getProprietaryConverters().equals(MessageDialogWithToggle.ALWAYS)) {
			return;
		}
		try {
			DisplayUtils.executeInUserInterfaceThread(() -> {

				MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(DisplayUtils.getShell(), "Vendor plugins missing", //
						"You currently have no proprietary converters installed. These are required to open instrument vendor files. Do you want to install converter plug-ins now?", //
						"Don't ask again.", false, Activator.getDefault().getPreferenceStore(), PreferenceSupplier.P_PROPRIETARY_CONVERTERS);
				/*
				 * Process the decision
				 */
				PreferenceSupplier.setProprietaryConverters(dialog.getToggleState() ? MessageDialogWithToggle.NEVER : MessageDialogWithToggle.ALWAYS);
				if(dialog.getReturnCode() == IDialogConstants.YES_ID) {
					try {
						IPluginInstallJob installJob = new PrepareInstallProfileJob();
						PluginDiscoveryWizard wizard = new PluginDiscoveryWizard(installJob);
						WizardDialog wizardDialog = new WizardDialog(DisplayUtils.getShell(), wizard);
						wizardDialog.open();
					} catch(IllegalArgumentException e) {
						logger.warn(e);
					}
				}
			});
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			logger.warn(e);
		} catch(ExecutionException e) {
			logger.warn(e);
		}
	}

	private List<String> getConverterFeatures() {

		List<String> features = new ArrayList<>();
		PluginDiscoveryExtensionReader extensionReader = new PluginDiscoveryExtensionReader();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PluginDiscoveryExtensionReader.EXTENSION_POINT_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		for(IExtension extension : extensions) {
			IConfigurationElement[] elements = extension.getConfigurationElements();
			for(IConfigurationElement element : elements) {
				if(PluginDiscoveryExtensionReader.PLUGIN_DESCRIPTOR.equals(element.getName())) {
					IPluginDescriptor descriptor = extensionReader.readConnectorDescriptor(element, PluginDescriptor.class);
					if(descriptor.getKind().contains(PluginDescriptorKind.CONVERTER)) {
						features.add(Strings.CS.removeEnd(descriptor.getInstallableUnit(), ".feature"));
					}
				}
			}
		}
		return features;
	}
}