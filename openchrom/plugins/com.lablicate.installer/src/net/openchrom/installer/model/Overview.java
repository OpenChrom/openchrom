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
 * @author David Green
 * @author Igor Burilo
 */
public class Overview {

	protected String summary;
	protected String url;
	protected String screenshot;
	protected PluginDescriptor pluginDescriptor;
	protected PluginCategory pluginCategory;

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

	/**
	 * An URL that points to a web page with more information relevant to the plugin or category.
	 */
	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	/**
	 * 320x240 PNG, JPEG or GIF
	 */
	public String getScreenshot() {

		return screenshot;
	}

	public void setScreenshot(String screenshot) {

		this.screenshot = screenshot;
	}

	public PluginDescriptor getConnectorDescriptor() {

		return pluginDescriptor;
	}

	public void setConnectorDescriptor(PluginDescriptor pluginDescriptor) {

		this.pluginDescriptor = pluginDescriptor;
	}

	public PluginCategory getConnectorCategory() {

		return pluginCategory;
	}

	public void setConnectorCategory(PluginCategory pluginCategory) {

		this.pluginCategory = pluginCategory;
	}

	public void validate() {

	}
}
