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
