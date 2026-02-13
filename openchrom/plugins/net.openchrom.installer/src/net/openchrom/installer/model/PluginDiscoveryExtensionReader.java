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
package net.openchrom.installer.model;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Connector Discovery extension point reader.
 */
public class PluginDiscoveryExtensionReader {

	public static final String EXTENSION_POINT_ID = "net.openchrom.installer.pluginDiscovery"; //$NON-NLS-1$
	public static final String PLUGIN_DESCRIPTOR = "pluginDescriptor"; //$NON-NLS-1$
	public static final String PLUGIN_CATEGORY = "pluginCategory"; //$NON-NLS-1$
	public static final String ICON = "icon"; //$NON-NLS-1$
	public static final String OVERVIEW = "overview"; //$NON-NLS-1$
	public static final String FEATURE_FILTER = "featureFilter"; //$NON-NLS-1$
	public static final String GROUP = "group"; //$NON-NLS-1$

	public PluginDescriptor readConnectorDescriptor(IConfigurationElement element) {

		return readConnectorDescriptor(element, PluginDescriptor.class);
	}

	public <T extends PluginDescriptor> T readConnectorDescriptor(IConfigurationElement element, Class<T> clazz) {

		T pluginDescriptor;
		try {
			pluginDescriptor = clazz.getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
		String kinds = element.getAttribute("kind"); //$NON-NLS-1$
		if(kinds != null) {
			String[] akinds = kinds.split("\\s*,\\s*"); //$NON-NLS-1$
			for(String kind : akinds) {
				pluginDescriptor.getKind().add(PluginDescriptorKind.fromValue(kind));
			}
		}
		pluginDescriptor.setInstallableUnit(element.getAttribute("id")); //$NON-NLS-1$
		pluginDescriptor.setName(element.getAttribute("name")); //$NON-NLS-1$
		pluginDescriptor.setProvider(element.getAttribute("provider")); //$NON-NLS-1$
		pluginDescriptor.setLicense(element.getAttribute("license")); //$NON-NLS-1$
		pluginDescriptor.setDescription(element.getAttribute("description")); //$NON-NLS-1$
		pluginDescriptor.setCategoryId(element.getAttribute("categoryId")); //$NON-NLS-1$
		pluginDescriptor.setPlatformFilter(element.getAttribute("platformFilter")); //$NON-NLS-1$
		pluginDescriptor.setGroupId(element.getAttribute("groupId")); //$NON-NLS-1$
		pluginDescriptor.setIcon(element.getAttribute("icon")); //$NON-NLS-1$
		pluginDescriptor.setUrl(element.getAttribute("url")); //$NON-NLS-1$
		pluginDescriptor.setSummary(element.getAttribute("summary")); //$NON-NLS-1$
		pluginDescriptor.validate();
		return pluginDescriptor;
	}

	public PluginCategory readConnectorCategory(IConfigurationElement element) {

		return readConnectorCategory(element, PluginCategory.class);
	}

	public <T extends PluginCategory> T readConnectorCategory(IConfigurationElement element, Class<T> clazz) {

		T pluginCategory;
		try {
			pluginCategory = clazz.getDeclaredConstructor().newInstance();
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
		pluginCategory.setId(element.getAttribute("id")); //$NON-NLS-1$
		pluginCategory.setName(element.getAttribute("name")); //$NON-NLS-1$
		pluginCategory.setDescription(element.getAttribute("description")); //$NON-NLS-1$
		pluginCategory.setRelevance(element.getAttribute("relevance")); //$NON-NLS-1$
		pluginCategory.setIcon(element.getAttribute("icon")); //$NON-NLS-1$
		pluginCategory.setSummary(element.getAttribute("summary")); //$NON-NLS-1$
		pluginCategory.validate();
		return pluginCategory;
	}

}
