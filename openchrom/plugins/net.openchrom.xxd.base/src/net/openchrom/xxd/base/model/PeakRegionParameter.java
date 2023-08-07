/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
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

import org.eclipse.chemclipse.numeric.core.IPoint;

public class PeakRegionParameter {

	private List<IPoint> points = new ArrayList<>();

	public void clear() {

		points.clear();
	}

	public void add(IPoint point) {

		points.add(point);
		sort();
	}

	public void remove(IPoint point) {

		points.remove(point);
		sort();
	}

	public boolean isValid() {

		return size() >= 3;
	}

	public IPoint getStart() {

		if(isValid()) {
			return points.get(0);
		}
		//
		return null;
	}

	public List<IPoint> getProposedMaxima() {

		if(isValid()) {
			return points.subList(1, size() - 1);
		}
		//
		return null;
	}

	public IPoint getStop() {

		if(isValid()) {
			return points.get(size() - 1);
		}
		//
		return null;
	}

	private void sort() {

		Collections.sort(points, (p1, p2) -> Double.compare(p1.getX(), p2.getX()));
	}

	private int size() {

		return points.size();
	}
}