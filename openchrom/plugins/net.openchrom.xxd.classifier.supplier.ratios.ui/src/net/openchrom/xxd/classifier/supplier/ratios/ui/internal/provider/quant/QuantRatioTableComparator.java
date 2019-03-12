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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.quant;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.support.ui.swt.AbstractRecordTableComparator;
import org.eclipse.chemclipse.support.ui.swt.IRecordTableComparator;
import org.eclipse.jface.viewers.Viewer;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;
import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.DisplayOption;

public class QuantRatioTableComparator extends AbstractRecordTableComparator implements IRecordTableComparator {

	private DisplayOption displayOption = DisplayOption.RESULTS;

	public QuantRatioTableComparator() {
		this(DisplayOption.RESULTS);
	}

	public QuantRatioTableComparator(DisplayOption displayOption) {
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
		if(e1 instanceof QuantRatio && e2 instanceof QuantRatio) {
			QuantRatio ratio1 = (QuantRatio)e1;
			QuantRatio ratio2 = (QuantRatio)e2;
			//
			switch(getPropertyIndex()) {
				case 0:
					sortOrder = ratio2.getName().compareTo(ratio1.getName());
					break;
				case 1:
					sortOrder = Double.compare(ratio2.getExpectedConcentration(), ratio1.getExpectedConcentration());
					break;
				case 2:
					sortOrder = ratio2.getConcentrationUnit().compareTo(ratio1.getConcentrationUnit());
					break;
				case 3:
					sortOrder = Double.compare(ratio2.getDeviationWarn(), ratio1.getDeviationWarn());
					break;
				case 4:
					sortOrder = Double.compare(ratio2.getDeviationError(), ratio1.getDeviationError());
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
		if(e1 instanceof QuantRatio && e2 instanceof QuantRatio) {
			QuantRatio ratio1 = (QuantRatio)e1;
			QuantRatio ratio2 = (QuantRatio)e2;
			//
			IPeak peak1 = ratio1.getPeak();
			IPeak peak2 = ratio2.getPeak();
			//
			switch(getPropertyIndex()) {
				case 0:
					if(peak1 != null && peak2 != null) {
						sortOrder = Integer.compare(peak2.getPeakModel().getRetentionTimeAtPeakMaximum(), peak1.getPeakModel().getRetentionTimeAtPeakMaximum());
					}
					break;
				case 1:
					sortOrder = ratio2.getName().compareTo(ratio1.getName());
					break;
				case 2:
					sortOrder = Double.compare(ratio2.getExpectedConcentration(), ratio1.getExpectedConcentration());
					break;
				case 3:
					sortOrder = Double.compare(ratio2.getConcentration(), ratio1.getConcentration());
					break;
				case 4:
					sortOrder = ratio2.getConcentrationUnit().compareTo(ratio1.getConcentrationUnit());
					break;
				case 5:
					sortOrder = Double.compare(ratio2.getDeviation(), ratio1.getDeviation());
					break;
			}
			if(getDirection() == ASCENDING) {
				sortOrder = -sortOrder;
			}
		}
		return sortOrder;
	}
}