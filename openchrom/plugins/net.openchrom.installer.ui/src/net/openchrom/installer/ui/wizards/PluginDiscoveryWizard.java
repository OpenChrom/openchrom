/*******************************************************************************
 * Copyright (c) 2009, 2026 Tasktop Technologies, Polarion Software and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Tasktop Technologies - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.osgi.framework.Bundle;

import net.openchrom.installer.model.PluginDescriptorKind;
import net.openchrom.installer.ui.Activator;
import net.openchrom.installer.ui.discovery.IPluginInstallJob;

/**
 * A wizard for performing discovery of plugins and selecting plugins to install. When finish is pressed, selected
 * plugins are downloaded and installed.
 * 
 * @see PluginDiscoveryWizardMainPage
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDiscoveryWizard extends Wizard {

	private static final Logger logger = Logger.getLogger(PluginDiscoveryWizard.class);
	private PluginDiscoveryWizardMainPage mainPage;
	protected IPluginInstallJob installJob;
	private final Map<PluginDescriptorKind, Boolean> pluginDescriptorKindToVisibility = new HashMap<>();
	{
		for(PluginDescriptorKind kind : PluginDescriptorKind.values()) {
			pluginDescriptorKindToVisibility.put(kind, true);
		}
	}
	private boolean showConnectorDescriptorKindFilter = true;
	private boolean showConnectorDescriptorTextFilter = true;

	public PluginDiscoveryWizard(IPluginInstallJob installJob) {

		this.installJob = installJob;
		setWindowTitle("Plug-In Installation");
		setNeedsProgressMonitor(true);
		Bundle bundle = Activator.getDefault().getBundle();
		URL fullPathString = FileLocator.find(bundle, new Path("icons/32x32/Plugin.gif"));
		setDefaultPageImageDescriptor(ImageDescriptor.createFromURL(fullPathString)); // TODO
	}

	@Override
	public void addPages() {

		addPage(mainPage = new PluginDiscoveryWizardMainPage());
	}

	@Override
	public boolean performFinish() {

		try {
			this.installJob.setInstallableConnectors(this.mainPage.getInstallableConnectors());
			this.getContainer().run(true, true, this.installJob);
			return true;
		} catch(InvocationTargetException e) {
			logger.warn(e.getCause());
		} catch(InterruptedException e) {
			// canceled
			Thread.currentThread().interrupt();
		}
		return false;
	}

	/**
	 * configure the page to show or hide plugin descriptors of the given kind
	 * 
	 * @see #pluginDescriptorKindVisibilityUpdated()
	 */
	public void setVisibility(PluginDescriptorKind kind, boolean visible) {

		if(kind == null) {
			throw new IllegalArgumentException();
		}
		pluginDescriptorKindToVisibility.put(kind, visible);
	}

	/**
	 * indicate if the given kind of plugin is currently visible in the wizard
	 * 
	 * @see #setVisibility(PluginDescriptorKind, boolean)
	 */
	public boolean isVisible(PluginDescriptorKind kind) {

		if(kind == null) {
			throw new IllegalArgumentException();
		}
		return pluginDescriptorKindToVisibility.get(kind);
	}

	/**
	 * indicate if the plugin descriptor filters should be shown in the UI. Changing this setting only has an effect
	 * before the UI is presented.
	 */
	public boolean isShowConnectorDescriptorKindFilter() {

		return this.showConnectorDescriptorKindFilter & false; // TODO always disabled
	}

	/**
	 * indicate if the plugin descriptor filters should be shown in the UI. Changing this setting only has an effect
	 * before the UI is presented.
	 */
	public void setShowConnectorDescriptorKindFilter(boolean showConnectorDescriptorKindFilter) {

		this.showConnectorDescriptorKindFilter = showConnectorDescriptorKindFilter;
	}

	/**
	 * indicate if a text field should be provided to allow the user to filter plugin descriptors
	 */
	public boolean isShowConnectorDescriptorTextFilter() {

		return showConnectorDescriptorTextFilter;
	}

	/**
	 * indicate if a text field should be provided to allow the user to filter plugin descriptors
	 */
	public void setShowConnectorDescriptorTextFilter(boolean showConnectorDescriptorTextFilter) {

		this.showConnectorDescriptorTextFilter = showConnectorDescriptorTextFilter;
	}

}
