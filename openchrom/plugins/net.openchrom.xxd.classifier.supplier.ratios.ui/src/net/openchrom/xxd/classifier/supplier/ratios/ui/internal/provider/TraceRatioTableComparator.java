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

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;

public class TraceRatioTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	private String displayOption = "";

	public TraceRatioTableComparator() {
		this(TraceRatioResultTitles.OPTION_RESULTS);
	}

	public TraceRatioTableComparator(String displayOption) {
		this.displayOption = (displayOption.equals(TraceRatioResultTitles.OPTION_SETTINGS)) ? TraceRatioResultTitles.OPTION_SETTINGS : TraceRatioResultTitles.OPTION_RESULTS;
	}

	public int compare(Viewer viewer, Object e1, Object e2) {

		if(TraceRatioResultTitles.OPTION_RESULTS.equals(displayOption)) {
			return compareResults(viewer, e1, e2);
		} else if(TraceRatioResultTitles.OPTION_SETTINGS.equals(displayOption)) {
			return compareSettings(viewer, e1, e2);
		} else {
			return 0;
		}
	}

	public int compareSettings(Viewer viewer, Object e1, Object e2) {

		int sortOrder = 0;
		if(e1 instanceof TraceRatio && e2 instanceof TraceRatio) {
			TraceRatio traceRatio1 = (TraceRatio)e1;
			TraceRatio traceRatio2 = (TraceRatio)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = traceRatio2.getName().compareTo(traceRatio1.getName());
					break;
				case 1:
					sortOrder = traceRatio2.getTestCase().compareTo(traceRatio1.getTestCase());
					break;
				case 2:
					sortOrder = Double.compare(traceRatio2.getExpectedRatio(), traceRatio1.getExpectedRatio());
					break;
				case 3:
					sortOrder = Double.compare(traceRatio2.getDeviationWarn(), traceRatio1.getDeviationWarn());
					break;
				case 4:
					sortOrder = Double.compare(traceRatio2.getDeviationError(), traceRatio1.getDeviationError());
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
		if(e1 instanceof TraceRatio && e2 instanceof TraceRatio) {
			TraceRatio traceRatio1 = (TraceRatio)e1;
			TraceRatio traceRatio2 = (TraceRatio)e2;
			//
			IPeak peak1 = traceRatio1.getPeak();
			IPeak peak2 = traceRatio2.getPeak();
			//
			switch(getPropertyIndex()) {
				case 0:
					if(peak1 != null && peak2 != null) {
						sortOrder = Integer.compare(peak2.getPeakModel().getRetentionTimeAtPeakMaximum(), peak1.getPeakModel().getRetentionTimeAtPeakMaximum());
					}
					break;
				case 1:
					sortOrder = traceRatio2.getName().compareTo(traceRatio1.getName());
					break;
				case 2:
					sortOrder = traceRatio2.getTestCase().compareTo(traceRatio1.getTestCase());
					break;
				case 3:
					sortOrder = Double.compare(traceRatio2.getRatio(), traceRatio1.getRatio());
					break;
				case 4:
					sortOrder = Double.compare(traceRatio2.getDeviation(), traceRatio1.getDeviation());
					break;
			}
			if(getDirection() == ASCENDING) {
				sortOrder = -sortOrder;
			}
		}
		return sortOrder;
	}
}
