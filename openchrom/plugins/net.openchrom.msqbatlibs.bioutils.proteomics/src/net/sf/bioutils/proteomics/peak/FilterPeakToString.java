/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

public class FilterPeakToString implements Filter<Peak> {

	private final String text;

	public FilterPeakToString(final String text) {

		this.text = text;
	}

	
	public boolean filter(final Peak e) {

		final String toString = e.toString();
		final boolean matches = toString.replaceAll("\n", " ").matches(".*(?i)" + text + ".*");
		return matches;
	}
}
