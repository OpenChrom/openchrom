/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class PeakIdentifierComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof IdentifierSetting setting1 && e2 instanceof IdentifierSetting setting2) {
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = setting2.getName().compareTo(setting1.getName());
					break;
				case 1:
					sortOrder = Double.compare(setting2.getPositionStart(), setting1.getPositionStart());
					break;
				case 2:
					sortOrder = Double.compare(setting2.getPositionStop(), setting1.getPositionStop());
					break;
				case 3:
					sortOrder = setting2.getPositionDirective().compareTo(setting1.getPositionDirective());
					break;
				case 4:
					sortOrder = setting2.getCasNumber().compareTo(setting1.getCasNumber());
					break;
				case 5:
					sortOrder = setting2.getComments().compareTo(setting1.getComments());
					break;
				case 6:
					sortOrder = setting2.getContributor().compareTo(setting1.getContributor());
					break;
				case 7:
					sortOrder = setting2.getReference().compareTo(setting1.getReference());
					break;
				case 8:
					sortOrder = setting2.getTraces().compareTo(setting1.getTraces());
					break;
				case 9:
					sortOrder = setting2.getReferenceIdentifier().compareTo(setting1.getReferenceIdentifier());
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
