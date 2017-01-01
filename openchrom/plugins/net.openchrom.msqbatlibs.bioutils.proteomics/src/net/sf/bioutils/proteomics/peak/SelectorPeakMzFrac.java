/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peak;

import java.util.Collection;
import java.util.TreeMap;

import net.sf.kerner.utils.collections.UtilCollection;

public class SelectorPeakMzFrac implements SelectorPeak {

	public static boolean DEFAULT_PPM = true;
	private final double mz;
	private final int frac;
	private boolean ppm = DEFAULT_PPM;

	public SelectorPeakMzFrac(double mz, int frac) {
		this.mz = mz;
		this.frac = frac;
	}

	public SelectorPeakMzFrac(Peak peak) {
		this(peak.getMz(), peak.getFractionIndex());
	}

	public synchronized boolean isPpm() {

		return ppm;
	}

	public Peak select(final Collection<? extends Peak> elements) {

		if(UtilCollection.nullOrEmpty(elements)) {
			throw new IllegalArgumentException("Empty param");
		}
		final TreeMap<Double, Peak> map = new TreeMap<Double, Peak>();
		for(final Peak p : elements) {
			double diffAbsMz = Math.abs(p.getMz() - mz);
			diffAbsMz = UtilPeak.getDeltaPpm(mz, diffAbsMz);
			// TODO: remove magic number
			diffAbsMz = diffAbsMz / 100;
			double diffAbsFrac = Math.abs(p.getFractionIndex() - frac);
			// TODO: delegate this to affinity calculator
			if(diffAbsFrac == 0) {
				diffAbsFrac = 1;
			}
			if(diffAbsMz == 0) {
				diffAbsMz = 1;
			}
			final double result = diffAbsFrac * diffAbsMz;
			map.put(result, p);
		}
		return map.firstEntry().getValue();
	}

	public synchronized void setPpm(boolean ppm) {

		this.ppm = ppm;
	}
}
