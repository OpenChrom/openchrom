/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakByMZPpm implements Filter<Peak> {

	protected final double massShift;
	protected final double parentMass;

	public FilterPeakByMZPpm(final double massShift, final double parentMass) {

		super();
		this.massShift = massShift;
		this.parentMass = parentMass;
	}

	public boolean filter(final Peak element) {

		final double d = (parentMass - element.getMz()) * 1000000 / parentMass;
		if(Math.abs(d) <= massShift) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public String toString() {

		return "parentMass=" + parentMass + ",shift=" + massShift;
	}
}
