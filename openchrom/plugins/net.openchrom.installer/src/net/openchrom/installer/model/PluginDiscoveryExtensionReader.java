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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Connector Discovery extension point reader, for extension points of type
 * <tt>org.eclipse.team.svn.core.pluginDiscovery</tt>
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDiscoveryExtensionReader {

	public static final String EXTENSION_POINT_ID = "net.openchrom.installer.pluginDiscovery"; //$NON-NLS-1$
	public static final String PLUGIN_DESCRIPTOR = "pluginDescriptor"; //$NON-NLS-1$
	public static final String PLUGIN_CATEGORY = "pluginCategory"; //$NON-NLS-1$
	public static final String ICON = "icon"; //$NON-NLS-1$
	public static final String OVERVIEW = "overview"; //$NON-NLS-1$
	public static final String FEATURE_FILTER = "featureFilter"; //$NON-NLS-1$
	public static final String GROUP = "group"; //$NON-NLS-1$

	private static final Logger logger = Logger.getLogger(PluginDiscoveryExtensionReader.class);

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
		for(IConfigurationElement child : element.getChildren(OVERVIEW)) {
			Overview overviewItem = readOverview(child);
			overviewItem.setConnectorDescriptor(pluginDescriptor);
			if(pluginDescriptor.getOverview() != null) {
				logger.warn("Unexpected element overview");
			}
			pluginDescriptor.setOverview(overviewItem);
		}
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
		for(IConfigurationElement child : element.getChildren("overview")) { //$NON-NLS-1$
			Overview overviewItem = readOverview(child);
			overviewItem.setConnectorCategory(pluginCategory);
			if(pluginCategory.getOverview() != null) {
				logger.warn("Unexpected element overview");
			}
			pluginCategory.setOverview(overviewItem);
		}
		for(IConfigurationElement child : element.getChildren(GROUP)) {
			Group groupItem = readGroup(child);
			groupItem.setConnectorCategory(pluginCategory);
			pluginCategory.getGroup().add(groupItem);
		}
		pluginCategory.validate();
		return pluginCategory;
	}

	public Overview readOverview(IConfigurationElement element) {

		Overview overview = new Overview();
		overview.setSummary(element.getAttribute("summary")); //$NON-NLS-1$
		overview.setUrl(element.getAttribute("url")); //$NON-NLS-1$
		overview.setScreenshot(element.getAttribute("screenshot")); //$NON-NLS-1$
		overview.validate();
		return overview;
	}

	public Group readGroup(IConfigurationElement element) {

		Group group = new Group();
		group.setId(element.getAttribute("id")); //$NON-NLS-1$
		group.validate();
		return group;
	}
}
