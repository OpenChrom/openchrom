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
package net.openchrom.installer.util;

import java.util.Comparator;

import net.openchrom.installer.model.IPluginDescriptor;
import net.openchrom.installer.model.PluginCategory;

/**
 * a comparator that orders plugins by group and alphabetically by their name
 */
public class DiscoveryConnectorComparator implements Comparator<IPluginDescriptor> {

	private final PluginCategory category;

	public DiscoveryConnectorComparator(PluginCategory category) {

		if(category == null) {
			throw new IllegalArgumentException();
		}
		this.category = category;
	}

	@Override
	public int compare(IPluginDescriptor o1, IPluginDescriptor o2) {

		if(o1.getCategory() != category || o2.getCategory() != category) {
			throw new IllegalArgumentException();
		}
		if(o1 == o2) {
			return 0;
		}
		return o1.getName().compareToIgnoreCase(o2.getName());
	}
}
