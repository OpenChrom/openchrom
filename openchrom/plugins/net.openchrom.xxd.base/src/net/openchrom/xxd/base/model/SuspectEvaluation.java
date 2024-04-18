/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.chemclipse.model.core.IPeak;

public class SuspectEvaluation {

	public static final String GROUP_SEPARATOR = ",";
	//
	private Suspect suspect = null;
	private List<IPeak> peaksTop = new ArrayList<>();
	private List<IPeak> peaksBottom = new ArrayList<>();

	public SuspectEvaluation(Suspect suspect) {

		this.suspect = suspect;
	}

	public Suspect getSuspect() {

		return suspect;
	}

	public String getGroupsAsString() {

		List<GroupMarker> groupMarkersSorted = new ArrayList<>(suspect.getGroupMarkers());
		Collections.sort(groupMarkersSorted, (g1, g2) -> g1.getName().compareTo(g2.getName()));
		//
		return groupMarkersSorted.stream().map(m -> m.getName().toString()).collect(Collectors.joining(GROUP_SEPARATOR));
	}

	public void reset() {

		peaksTop.clear();
		peaksBottom.clear();
	}

	public boolean isTop() {

		return !peaksTop.isEmpty();
	}

	public boolean isBottom() {

		return !peaksBottom.isEmpty();
	}

	public String getStatus() {

		boolean top = isTop();
		boolean bottom = isBottom();
		//
		if(top && bottom) {
			return "OK";
		} else if(top) {
			return "T [" + peaksTop.size() + "]";
		} else if(bottom) {
			return "B [" + peaksBottom.size() + "]";
		} else {
			return "--";
		}
	}

	public List<IPeak> getPeaksTop() {

		return peaksTop;
	}

	public List<IPeak> getPeaksBottom() {

		return peaksBottom;
	}
}