/*******************************************************************************
 * Copyright (c) 2009, 2025 Tasktop Technologies, Polarion Software and others.
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
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class DiscoveryCategory extends PluginCategory {

	private IDiscoverySource source;
	private List<DiscoveryPlugin> plugins = new ArrayList<>();

	public List<DiscoveryPlugin> getConnectors() {

		return plugins;
	}

	public IDiscoverySource getSource() {

		return source;
	}

	public void setSource(IDiscoverySource source) {

		this.source = source;
	}
}
