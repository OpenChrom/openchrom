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

import java.util.Arrays;
import java.util.Collection;

import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakByFracNr implements Filter<Peak> {

	private final Collection<? extends Number> fracNr;

	public FilterPeakByFracNr(final Collection<? extends Number> fracNr) {
		this.fracNr = fracNr;
	}

	public FilterPeakByFracNr(final Number fracNr) {
		this.fracNr = Arrays.asList(fracNr);
	}

	public boolean filter(final Peak element) {

		for(final Number n : fracNr) {
			if(element.getFractionIndex() == n.intValue()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {

		return "fracNr=" + fracNr;
	}
}
