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
package net.openchrom.installer.util;

import java.util.Comparator;

import net.openchrom.installer.model.DiscoveryPlugin;
import net.openchrom.installer.model.Group;
import net.openchrom.installer.model.PluginCategory;

/**
 * a comparator that orders plugins by group and alphabetically by their name
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class DiscoveryConnectorComparator implements Comparator<DiscoveryPlugin> {

	private final PluginCategory category;

	public DiscoveryConnectorComparator(PluginCategory category) {

		if(category == null) {
			throw new IllegalArgumentException();
		}
		this.category = category;
	}

	/**
	 * compute the index of the group id
	 * 
	 * @param groupId
	 *            the group id or null
	 * @return the index, or -1 if not found
	 */
	private int computeGroupIndex(String groupId) {

		if(groupId != null) {
			int index = -1;
			for(Group group : category.getGroup()) {
				++index;
				if(group.getId().equals(groupId)) {
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public int compare(DiscoveryPlugin o1, DiscoveryPlugin o2) {

		if(o1.getCategory() != category || o2.getCategory() != category) {
			throw new IllegalArgumentException();
		}
		if(o1 == o2) {
			return 0;
		}
		int g1 = computeGroupIndex(o1.getGroupId());
		int g2 = computeGroupIndex(o2.getGroupId());
		int i;
		if(g1 != g2) {
			if(g1 == -1) {
				i = 1;
			} else if(g2 == -1) {
				i = -1;
			} else {
				i = g1 - g2;
			}
		} else {
			i = o1.getName().compareToIgnoreCase(o2.getName());
		}
		return i;
	}
}
