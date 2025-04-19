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
