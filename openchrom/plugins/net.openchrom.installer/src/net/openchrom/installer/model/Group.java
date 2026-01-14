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
 * groups provide a way to anchor plugins in a grouping with other like entries.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class Group {

	protected String id;
	protected PluginCategory pluginCategory;

	/**
	 * An identifier that identifies the group. Must be unique for a particular pluginCategory.
	 */
	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public PluginCategory getConnectorCategory() {

		return pluginCategory;
	}

	public void setConnectorCategory(PluginCategory pluginCategory) {

		this.pluginCategory = pluginCategory;
	}

	public void validate() throws IllegalArgumentException {

		if(id == null || id.isEmpty()) {
			throw new IllegalArgumentException("id is missing.");
		}
	}
}
