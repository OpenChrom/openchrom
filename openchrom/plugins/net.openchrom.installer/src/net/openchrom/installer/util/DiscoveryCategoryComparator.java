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

import net.openchrom.installer.model.DiscoveryCategory;

/**
 * A comparator that orders categories by relevance and name.
 * 
 * @author David Green
 * @author Igor Burilo
 */
public class DiscoveryCategoryComparator implements Comparator<DiscoveryCategory> {

	@Override
	public int compare(DiscoveryCategory o1, DiscoveryCategory o2) {

		if(o1 == o2) {
			return 0;
		}
		String r1 = o1.getRelevance();
		String r2 = o2.getRelevance();
		int i;
		if(r1 != null && r2 != null) {
			// don't have to worry about format, since they were already validated
			// note that higher relevance appears first, thus the reverse order of
			// the comparison.
			i = Integer.compare(Integer.parseInt(r2), Integer.parseInt(r1));
		} else if(r1 == null) {
			return 1;
		} else {
			return -1;
		}
		if(i == 0) {
			i = o1.getName().compareToIgnoreCase(o2.getName());
			if(i == 0) {
				i = o1.getId().compareTo(o2.getId());
			}
		}
		return i;
	}
}