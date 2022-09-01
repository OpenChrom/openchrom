/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.time;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.swt.DisplayOption;

public class TimeRatioTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public TimeRatioTableComparator() {

		this(DisplayOption.RESULTS);
	}

	public TimeRatioTableComparator(DisplayOption displayOption) {

		this.displayOption = displayOption;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {

		int result;
		switch(displayOption) {
			case RESULTS:
				result = compareResults(viewer, e1, e2);
				break;
			case SETTINGS:
				result = compareSettings(viewer, e1, e2);
				break;
			default:
				result = 0;
				break;
		}
		return result;
	}

	public int compareSettings(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof TimeRatio && e2 instanceof TimeRatio) {
			TimeRatio timeRatio1 = (TimeRatio)e1;
			TimeRatio timeRatio2 = (TimeRatio)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = timeRatio2.getName().compareTo(timeRatio1.getName());
					break;
				case 2:
					sortOrder = Integer.compare(timeRatio2.getExpectedRetentionTime(), timeRatio1.getExpectedRetentionTime());
					break;
				case 3:
					sortOrder = Double.compare(timeRatio2.getDeviationWarn(), timeRatio1.getDeviationWarn());
					break;
				case 4:
					sortOrder = Double.compare(timeRatio2.getDeviationError(), timeRatio1.getDeviationError());
					break;
			}
			if(getDirection() == ASCENDING) {
				sortOrder = -sortOrder;
			}
		}
		return sortOrder;
	}

	public int compareResults(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof TimeRatio && e2 instanceof TimeRatio) {
			TimeRatio timeRatio1 = (TimeRatio)e1;
			TimeRatio timeRatio2 = (TimeRatio)e2;
			//
			IPeak peak1 = timeRatio1.getPeak();
			IPeak peak2 = timeRatio2.getPeak();
			//
			switch(getPropertyIndex()) {
				case 0:
					if(peak1 != null && peak2 != null) {
						sortOrder = Integer.compare(peak2.getPeakModel().getRetentionTimeAtPeakMaximum(), peak1.getPeakModel().getRetentionTimeAtPeakMaximum());
					}
					break;
				case 1:
					sortOrder = timeRatio2.getName().compareTo(timeRatio1.getName());
					break;
				case 2:
					sortOrder = Integer.compare(timeRatio2.getExpectedRetentionTime(), timeRatio1.getExpectedRetentionTime());
					break;
				case 3:
					sortOrder = Double.compare(timeRatio2.getDeviation(), timeRatio1.getDeviation());
					break;
			}
			if(getDirection() == ASCENDING) {
				sortOrder = -sortOrder;
			}
		}
		return sortOrder;
	}
}