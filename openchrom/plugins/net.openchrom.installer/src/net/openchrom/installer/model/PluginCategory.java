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

/**
 * a category of plugins, which is a way of organizing plugins in top-level groups.
 */
public class PluginCategory {

	protected String id;
	protected String name;
	protected String description;
	protected String relevance;
	protected String icon;
	protected String summary;

	/**
	 * an id that uniquely identifies the category
	 */
	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}
	
	/**
	 * the name of the category, as it is displayed in the ui.
	 */
	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	/**
	 * A description of the category
	 */
	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	/**
	 * A relevance, which is a number from 0 to 100. Categories with higher relevance are displayed with preference in
	 * the UI.
	 */
	public String getRelevance() {

		return relevance;
	}

	public void setRelevance(String relevance) {

		this.relevance = relevance;
	}

	public String getIcon() {

		return icon;
	}

	public void setIcon(String icon) {

		this.icon = icon;
	}

	public String getSummary() {

		return summary;
	}

	public void setSummary(String summary) {

		this.summary = summary;
	}

	public void validate() throws IllegalArgumentException {

		if(id == null || id.isEmpty()) {
			throw new IllegalArgumentException("id is missing");
		}
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("name is missing");
		}
		if(icon != null && icon.trim().isEmpty()) {
			throw new IllegalArgumentException("icon is set but empty");
		}
		if(summary != null && summary.trim().isEmpty()) {
			throw new IllegalArgumentException("summary is set but empty");
		}
		if(relevance != null) {
			try {
				int r = Integer.parseInt(relevance, 10);
				if(r < 0 || r > 100) {
					throw new NumberFormatException();
				}
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("relevance is not between 1 and 100");
			}
		}
	}
}
