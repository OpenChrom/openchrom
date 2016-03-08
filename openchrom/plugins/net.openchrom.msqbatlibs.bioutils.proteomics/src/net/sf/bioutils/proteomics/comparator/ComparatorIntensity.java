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
package net.sf.bioutils.proteomics.comparator;

import java.util.Comparator;

import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.collections.comparator.ComparatorNull;

public class ComparatorIntensity extends ComparatorNull<ProviderIntensity> implements Comparator<ProviderIntensity> {

	public int compareNonNull(final ProviderIntensity o1, final ProviderIntensity o2) {

		return Double.valueOf(o1.getIntensity()).compareTo(o2.getIntensity());
	}
}
