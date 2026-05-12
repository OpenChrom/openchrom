/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;

public class NameReplacementsComparator extends AbstractRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof NameReplacement setting1 && e2 instanceof NameReplacement setting2) {
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = setting2.getName().compareTo(setting1.getName());
					break;
				case 1:
					sortOrder = setting2.getSynonym().compareTo(setting1.getSynonym());
					break;
				default:
					sortOrder = 0;
			}
		}
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}
}