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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * A description of a plugin, including kind, description, licensing and brand.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class PluginDescriptor implements IPluginDescriptor {

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
	private boolean selected;
	private final PropertyChangeSupport changeSupport;
	private DiscoveryCategory category;

	public PluginDescriptor() {

		changeSupport = new PropertyChangeSupport(this);
	}

	@Override
	public List<PluginDescriptorKind> getKind() {

		return kind;
	}

	@Override
	public void setKind(List<PluginDescriptorKind> kind) {

		this.kind = kind;
	}

	/**
	 * the name of the plugin including the name of the organization that produces the repository if appropriate
	 */
	@Override
	public String getName() {

		return name;
	}

	@Override
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * The name of the organization that supplies the plugin.
	 */
	@Override
	public String getProvider() {

		return provider;
	}

	@Override
	public void setProvider(String provider) {

		this.provider = provider;
	}

	/**
	 * The name of the organization that supplies the plugin.
	 */
	@Override
	public String getUrl() {

		return url;
	}

	@Override
	public void setUrl(String url) {

		this.url = url;
	}

	/**
	 * The short name of the license, for example 'EPL 1.0', 'GPL 2.0', or 'Commercial'.
	 */
	@Override
	public String getLicense() {

		return license;
	}

	@Override
	public void setLicense(String license) {

		this.license = license;
	}

	/**
	 * A description of the plugin. Plug-ins should provide a description, especially if the description is not
	 * self-evident from the @name and
	 * 
	 * @organization.
	 */
	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * The id of the feature that installs this plugin
	 */
	@Override
	public String getInstallableUnit() {

		return installableUnit;
	}

	@Override
	public void setInstallableUnit(String id) {

		this.installableUnit = id;
	}

	/**
	 * the id of the pluginCategory in which this plugin belongs
	 */
	@Override
	public String getCategoryId() {

		return categoryId;
	}

	@Override
	public void setCategoryId(String categoryId) {

		this.categoryId = categoryId;
	}

	/**
	 * E.g., "(& (osgi.os=macosx) (osgi.ws=carbon))"
	 */
	@Override
	public String getPlatformFilter() {

		return platformFilter;
	}

	@Override
	public void setPlatformFilter(String platformFilter) {

		this.platformFilter = platformFilter;
	}

	/**
	 * The id of the pluginCategory group. See group/@id for more details.
	 */
	@Override
	public String getGroupId() {

		return groupId;
	}

	@Override
	public void setGroupId(String groupId) {

		this.groupId = groupId;
	}

	@Override
	public String getIcon() {

		return icon;
	}

	@Override
	public void setIcon(String icon) {

		this.icon = icon;
	}

	/**
	 * A description providing detailed information about the item. Newlines can be used to format the text into
	 * multiple paragraphs if necessary. Text must fit into an area 320x240, otherwise it will be truncated in the UI.
	 * More lengthy descriptions can be provided on a web page if required, see @url.
	 */
	@Override
	public String getSummary() {

		return summary;
	}

	@Override
	public void setSummary(String summary) {

		this.summary = summary;
	}

	@Override
	public DiscoveryCategory getCategory() {

		return category;
	}

	@Override
	public void setCategory(DiscoveryCategory category) {

		this.category = category;
	}

	/**
	 * support selection
	 * 
	 * @return true if the item is selected, otherwise false
	 */
	public boolean isSelected() {

		return selected;
	}

	/**
	 * support selection
	 * 
	 * @param selected
	 *            true if the item is selected, otherwise false
	 */
	@Override
	public void setSelected(boolean selected) {

		this.selected = selected;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {

		changeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {

		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {

		changeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {

		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
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
