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
package net.sf.bioutils.proteomics.sample;

import java.util.Arrays;
import java.util.Collection;

import net.sf.bioutils.proteomics.peak.Peak;

public class SampleGetMaxFrac {

	public int getMaxFrac(final Collection<? extends Sample> samples) {

		int result = -1;
		for(final Sample s : samples) {
			for(final Peak p : s.getPeaks()) {
				if(p.getFractionIndex() > result) {
					result = p.getFractionIndex();
				}
			}
		}
		return result;
	}

	public int getMaxFrac(final Sample sample) {

		return getMaxFrac(Arrays.asList(sample));
	}
}
