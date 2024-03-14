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
 * A means of specifying that a feature must be present in order for the pluginDescriptor to be presented to the
 * user.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class FeatureFilter {

	protected String featureId;
	protected String version;
	protected PluginDescriptor pluginDescriptor;

	/**
	 * The id of the feature to test
	 */
	public String getFeatureId() {

		return featureId;
	}

	public void setFeatureId(String featureId) {

		this.featureId = featureId;
	}

	/**
	 * A version specifier, specified in the same manner as version dependencies are specified in an OSGi manifest. For
	 * example: "[3.0,4.0)"
	 */
	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public PluginDescriptor getConnectorDescriptor() {

		return pluginDescriptor;
	}

	public void setConnectorDescriptor(PluginDescriptor pluginDescriptor) {

		this.pluginDescriptor = pluginDescriptor;
	}

	public void validate() throws IllegalArgumentException {

		if(featureId == null || featureId.length() == 0) {
			throw new IllegalArgumentException("featureID is missing");
		}
		if(version == null || version.length() == 0) {
			throw new IllegalArgumentException("version is missing");
		}
	}
}
