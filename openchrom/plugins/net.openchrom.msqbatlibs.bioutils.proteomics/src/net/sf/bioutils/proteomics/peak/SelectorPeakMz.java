/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

public class SelectorPeakMz implements SelectorPeak {

	private final Peak peak;

	public SelectorPeakMz(Peak peak) {
		super();
		this.peak = peak;
	}

	public Peak select(Collection<? extends Peak> elements) {

		Peak result = null;
		double massDiffAbs = -1;
		for(Peak p : elements) {
			double diff = Math.abs(peak.getMz() - p.getMz());
			if(diff < massDiffAbs) {
				massDiffAbs = diff;
				result = p;
			}
		}
		return result;
	}
}
