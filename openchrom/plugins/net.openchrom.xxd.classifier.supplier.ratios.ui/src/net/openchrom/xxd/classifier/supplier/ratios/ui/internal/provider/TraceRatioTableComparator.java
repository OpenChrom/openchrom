/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;

public class TraceRatioTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof TraceRatio && e2 instanceof TraceRatio) {
			TraceRatio traceRatio1 = (TraceRatio)e1;
			TraceRatio traceRatio2 = (TraceRatio)e2;
			//
			IPeakMSD peakMSD1 = traceRatio1.getPeakMSD();
			IPeakMSD peakMSD2 = traceRatio2.getPeakMSD();
			//
			switch(getPropertyIndex()) {
				case 0:
					if(peakMSD1 != null && peakMSD2 != null) {
						sortOrder = Integer.compare(peakMSD2.getPeakModel().getRetentionTimeAtPeakMaximum(), peakMSD1.getPeakModel().getRetentionTimeAtPeakMaximum());
					}
					break;
				case 1:
					sortOrder = traceRatio2.getName().compareTo(traceRatio1.getName());
					break;
				case 2:
					sortOrder = traceRatio2.getTestCase().compareTo(traceRatio1.getTestCase());
					break;
				case 3:
					sortOrder = Double.compare(traceRatio2.getExpectedRatio(), traceRatio1.getExpectedRatio());
					break;
				case 4:
					sortOrder = Double.compare(traceRatio2.getActualRatio(), traceRatio1.getActualRatio());
					break;
				case 5:
					sortOrder = Double.compare(traceRatio2.getDeviationWarn(), traceRatio1.getDeviationWarn());
					break;
				case 6:
					sortOrder = Double.compare(traceRatio2.getDeviationError(), traceRatio1.getDeviationError());
					break;
			}
			if(getDirection() == ASCENDING) {
				sortOrder = -sortOrder;
			}
		}
		return sortOrder;
	}
}
