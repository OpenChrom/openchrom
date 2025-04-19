/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.filter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.chemclipse.model.core.IPeak;

public class PeakGroup<T extends IPeak> {

	private final Map<Integer, T> peakMap = new HashMap<>();

	public void addPeak(T peak, int index) {

		peakMap.put(index, peak);
	}

	public void merge(PeakGroup<T> other) {

		this.peakMap.putAll(other.peakMap);
		other.peakMap.clear();
	}

	public boolean intersects(PeakGroup<T> other) {

		if(other.peakMap.isEmpty()) {
			return false;
		}
		for(Integer index : other.peakMap.keySet()) {
			if(this.peakMap.containsKey(index)) {
				return true;
			}
		}
		return false;
	}

	public T getMaxPeak(Comparator<T> comparator) {

		return peakMap.values().stream().max(comparator).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public String toString() {

		return peakMap.keySet().toString();
	}

	public boolean isEmpty() {

		return peakMap.isEmpty();
	}

	public Collection<T> values() {

		return Collections.unmodifiableCollection(peakMap.values());
	}
}