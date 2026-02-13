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

import java.util.ArrayList;
import java.util.List;

/**
 * A description of a plugin, including kind, description, licensing and brand.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDescriptor {

	protected List<PluginDescriptorKind> kind = new ArrayList<>();
	protected String name;
	protected String provider;
	protected String license;
	protected String description;
	protected String installableUnit;
	protected String categoryId;
	protected String platformFilter;
	protected String groupId;
	protected String icon;
	protected String url;
	protected String summary;

	public List<PluginDescriptorKind> getKind() {

		return kind;
	}

	public void setKind(List<PluginDescriptorKind> kind) {

		this.kind = kind;
	}

	/**
	 * the name of the plugin including the name of the organization that produces the repository if appropriate
	 */
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	/**
	 * The name of the organization that supplies the plugin.
	 */
	public String getProvider() {

		return provider;
	}

	public void setProvider(String provider) {

		this.provider = provider;
	}
	
	/**
	 * The name of the organization that supplies the plugin.
	 */
	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}


	/**
	 * The short name of the license, for example 'EPL 1.0', 'GPL 2.0', or 'Commercial'.
	 */
	public String getLicense() {

		return license;
	}

	public void setLicense(String license) {

		this.license = license;
	}

	/**
	 * A description of the plugin. Plug-ins should provide a description, especially if the description is not
	 * self-evident from the @name and
	 * 
	 * @organization.
	 */
	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * The id of the feature that installs this plugin
	 */
	public String getInstallableUnit() {

		return installableUnit;
	}

	public void setInstallableUnit(String id) {

		this.installableUnit = id;
	}

	/**
	 * the id of the pluginCategory in which this plugin belongs
	 */
	public String getCategoryId() {

		return categoryId;
	}

	public void setCategoryId(String categoryId) {

		this.categoryId = categoryId;
	}

	/**
	 * E.g., "(& (osgi.os=macosx) (osgi.ws=carbon))"
	 */
	public String getPlatformFilter() {

		return platformFilter;
	}

	public void setPlatformFilter(String platformFilter) {

		this.platformFilter = platformFilter;
	}

	/**
	 * The id of the pluginCategory group. See group/@id for more details.
	 */
	public String getGroupId() {

		return groupId;
	}

	public void setGroupId(String groupId) {

		this.groupId = groupId;
	}

	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}
	
	/**
	 * A description providing detailed information about the item. Newlines can be used to format the text into
	 * multiple paragraphs if necessary. Text must fit into an area 320x240, otherwise it will be truncated in the UI.
	 * More lengthy descriptions can be provided on a web page if required, see @url.
	 */
	public String getSummary() {

		return summary;
	}

	public void setSummary(String summary) {

		this.summary = summary;
	}

	public void validate() throws IllegalArgumentException {

		if(kind == null || kind.isEmpty()) {
			throw new IllegalArgumentException("kind is empty");
		}
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if(provider == null || provider.isEmpty()) {
			throw new IllegalArgumentException("provider is empty");
		}
		if(license == null || license.isEmpty()) {
			throw new IllegalArgumentException("license is empty");
		}
		if(installableUnit == null || installableUnit.isEmpty()) {
			throw new IllegalArgumentException("installableUnit is empty");
		}
		if(categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("category is empty");
		}
		if(icon != null && icon.isEmpty()) {
			throw new IllegalArgumentException("icon is provided but is empty");
		}
		
		if(url != null && url.isEmpty()) {
			throw new IllegalArgumentException("icon is provided but is empty");
		}
		
		if(summary != null && summary.isEmpty()) {
			throw new IllegalArgumentException("icon is provided but is empty");
		}
	}
}
