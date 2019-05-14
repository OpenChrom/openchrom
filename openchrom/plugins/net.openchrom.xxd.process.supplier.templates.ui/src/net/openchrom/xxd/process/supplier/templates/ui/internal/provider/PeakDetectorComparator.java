/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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
		if(e1 instanceof DetectorSetting && e2 instanceof DetectorSetting) {
			//
			DetectorSetting setting1 = (DetectorSetting)e1;
			DetectorSetting setting2 = (DetectorSetting)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = Integer.compare(setting2.getStartRetentionTime(), setting1.getStartRetentionTime());
					break;
				case 1:
					sortOrder = Integer.compare(setting2.getStopRetentionTime(), setting1.getStopRetentionTime());
					break;
				case 2:
					sortOrder = setting2.getDetectorType().compareTo(setting1.getDetectorType());
					break;
				case 3:
					sortOrder = setting2.getTraces().compareTo(setting1.getTraces());
					break;
				case 4:
					sortOrder = Boolean.compare(setting2.isOptimizeRange(), setting1.isOptimizeRange());
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
