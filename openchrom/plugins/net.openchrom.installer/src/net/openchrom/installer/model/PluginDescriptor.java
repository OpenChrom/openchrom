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
	protected List<String> installableUnits = new ArrayList<>();
	protected String categoryId;
	protected String platformFilter;
	protected String groupId;
	protected List<FeatureFilter> featureFilter = new ArrayList<>();
	protected String icon;
	protected Overview overview;

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
	public List<String> getInstallableUnits() {

		return installableUnits;
	}

	public void setInstallableUnits(List<String> id) {

		this.installableUnits = id;
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

	public List<FeatureFilter> getFeatureFilter() {

		return featureFilter;
	}

	public void setFeatureFilter(List<FeatureFilter> featureFilter) {

		this.featureFilter = featureFilter;
	}

	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}

	public Overview getOverview() {

		return overview;
	}

	public void setOverview(Overview overview) {

		this.overview = overview;
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
		if(installableUnits == null || installableUnits.isEmpty()) {
			throw new IllegalArgumentException("installableUnits is empty");
		}
		if(categoryId == null || categoryId.isEmpty()) {
			throw new IllegalArgumentException("category is empty");
		}
		for(FeatureFilter featureFilterItem : featureFilter) {
			featureFilterItem.validate();
		}
		if(icon != null && icon.isEmpty()) {
			throw new IllegalArgumentException("icon is provided but is empty");
		}
		if(overview != null) {
			overview.validate();
		}
	}
}
