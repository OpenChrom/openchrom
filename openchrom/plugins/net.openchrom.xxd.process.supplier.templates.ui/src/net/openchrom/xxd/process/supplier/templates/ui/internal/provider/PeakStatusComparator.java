/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

public class PeakStatusComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof IPeak && e2 instanceof IPeak) {
			//
			IPeak peak1 = (IPeak)e1;
			IPeakModel peakModel1 = peak1.getPeakModel();
			IPeak peak2 = (IPeak)e2;
			IPeakModel peakModel2 = peak2.getPeakModel();
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = PeakStatusLabelProvider.getName(peak2).compareTo(PeakStatusLabelProvider.getName(peak1));
					break;
				case 1:
					sortOrder = Integer.compare(peakModel2.getStartRetentionTime(), peakModel1.getStartRetentionTime());
					break;
				case 2:
					sortOrder = Integer.compare(peakModel2.getStopRetentionTime(), peakModel1.getStopRetentionTime());
					break;
				case 3:
					sortOrder = Double.compare(peak2.getIntegratedArea(), peak1.getIntegratedArea());
					break;
				case 4:
					sortOrder = Boolean.compare(ReviewSupport.isPeakReviewed(peak2), ReviewSupport.isPeakReviewed(peak1));
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
