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

import java.util.Comparator;

import net.sf.bioutils.proteomics.comparator.ComparatorIntensity;

public class ComparatorPeakByIntensity implements Comparator<Peak> {

	private final static ComparatorIntensity COMPARATOR_INTENSITY = new ComparatorIntensity();

	public int compare(final Peak o1, final Peak o2) {

		return COMPARATOR_INTENSITY.compare(o1, o2);
	}
}
