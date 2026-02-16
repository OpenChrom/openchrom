/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface IPluginDescriptor {

	List<PluginDescriptorKind> getKind();

	void setKind(List<PluginDescriptorKind> kind);

	/**
	 * the name of the plugin including the name of the organization that produces the repository if appropriate
	 */
	String getName();

	void setName(String name);

	/**
	 * The name of the organization that supplies the plugin.
	 */
	String getProvider();

	void setProvider(String provider);

	/**
	 * The name of the organization that supplies the plugin.
	 */
	String getUrl();

	void setUrl(String url);

	/**
	 * The short name of the license, for example 'EPL 1.0', 'GPL 2.0', or 'Commercial'.
	 */
	String getLicense();

	void setLicense(String license);

	/**
	 * A description of the plugin. Plug-ins should provide a description, especially if the description is not
	 * self-evident from the @name and
	 * 
	 * @organization.
	 */
	String getDescription();

	void setDescription(String description);

	/**
	 * The id of the feature that installs this plugin
	 */
	String getInstallableUnit();

	void setInstallableUnit(String id);

	/**
	 * the id of the pluginCategory in which this plugin belongs
	 */
	String getCategoryId();

	void setCategoryId(String categoryId);

	/**
	 * E.g., "(& (osgi.os=macosx) (osgi.ws=carbon))"
	 */
	String getPlatformFilter();

	void setPlatformFilter(String platformFilter);

	/**
	 * The id of the pluginCategory group. See group/@id for more details.
	 */
	String getGroupId();

	void setGroupId(String groupId);

	String getIcon();

	void setIcon(String icon);

	/**
	 * A description providing detailed information about the item. Newlines can be used to format the text into
	 * multiple paragraphs if necessary. Text must fit into an area 320x240, otherwise it will be truncated in the UI.
	 * More lengthy descriptions can be provided on a web page if required, see @url.
	 */
	String getSummary();

	void setSummary(String summary);

	void validate() throws IllegalArgumentException;

	void setCategory(DiscoveryCategory category);

	PluginCategory getCategory();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void setSelected(boolean selected);

}