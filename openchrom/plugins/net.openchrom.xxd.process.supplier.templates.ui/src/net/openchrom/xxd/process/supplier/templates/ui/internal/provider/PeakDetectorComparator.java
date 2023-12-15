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

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;

public class PeakDetectorComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof DetectorSetting setting1 && e2 instanceof DetectorSetting setting2) {
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = Double.compare(setting2.getPositionStart(), setting1.getPositionStart());
					break;
				case 1:
					sortOrder = Double.compare(setting2.getPositionStop(), setting1.getPositionStop());
					break;
				case 2:
					sortOrder = setting2.getPositionDirective().compareTo(setting1.getPositionDirective());
					break;
				case 3:
					sortOrder = setting2.getPeakType().compareTo(setting1.getPeakType());
					break;
				case 4:
					sortOrder = setting2.getTraces().compareTo(setting1.getTraces());
					break;
				case 5:
					sortOrder = Boolean.compare(setting2.isOptimizeRange(), setting1.isOptimizeRange());
					break;
				case 6:
					sortOrder = setting2.getReferenceIdentifier().compareTo(setting1.getReferenceIdentifier());
					break;
				case 7:
					sortOrder = setting2.getName().compareTo(setting1.getName());
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
