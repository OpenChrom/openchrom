/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.core;

import java.util.Comparator;

import org.eclipse.chemclipse.support.comparator.SortOrder;

import net.openchrom.xxd.processor.supplier.tracecompare.model.TrackStatistics;

public class TrackStatisticComparator implements Comparator<TrackStatistics> {

	private SortOrder sortOrder;

	public TrackStatisticComparator(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int compare(TrackStatistics trackStatistics1, TrackStatistics trackStatistics2) {

		if(sortOrder == SortOrder.DESC) {
			return Double.compare(trackStatistics2.getMatchProbability(), trackStatistics1.getMatchProbability());
		} else {
			return Double.compare(trackStatistics1.getMatchProbability(), trackStatistics2.getMatchProbability());
		}
	}
}
