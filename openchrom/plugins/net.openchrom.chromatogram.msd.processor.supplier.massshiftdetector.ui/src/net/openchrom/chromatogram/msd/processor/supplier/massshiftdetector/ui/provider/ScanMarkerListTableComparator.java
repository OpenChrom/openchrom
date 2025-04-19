/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.provider;

import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;

public class ScanMarkerListTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		//
		if(e1 instanceof IScanMarker scanMarker1 && e2 instanceof IScanMarker scanMarker2) {
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = Double.compare(scanMarker2.getScanNumber(), scanMarker1.getScanNumber());
					break;
				case 1:
					sortOrder = Integer.compare(scanMarker2.getRetentionTimeReference(), scanMarker1.getRetentionTimeReference());
					break;
				case 2:
					sortOrder = Integer.compare(scanMarker2.getRetentionTimeIsotope(), scanMarker1.getRetentionTimeIsotope());
					break;
				case 3:
					sortOrder = Boolean.compare(scanMarker2.isValidated(), scanMarker1.isValidated());
					break;
			}
		}
		//
		if(getDirection() == ASCENDING) {
			sortOrder = -sortOrder;
		}
		return sortOrder;
	}
}
