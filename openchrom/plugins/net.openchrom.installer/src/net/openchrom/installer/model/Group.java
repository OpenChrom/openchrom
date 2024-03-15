/*******************************************************************************
 * Copyright (c) 2009, 2024 Tasktop Technologies, Polarion Software and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

		if(id == null || id.length() == 0) {
			throw new IllegalArgumentException("id is missing.");
		}
	}
}
