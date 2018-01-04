/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.feature;

import net.sf.bioutils.proteomics.ComposableElement;
import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * A {@code Feature} is a {@link ComposableElement} of {@link Peak Peaks}. It
 * represents one single peptide that was detected. One peptide is composed of
 * multiple signals (peaks).
 * </p>
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Feature extends ComposableElement<Peak>, Peak, Iterable<Peak> {

	Feature clone();

	/**
	 * Implementation dependent. Typically, it will be the number of the
	 * fraction which contains the {@link Peak} that contributes to this {@code Feature} and has the highest intensity.
	 *
	 * @return fraction number this {@code Feature} is associated with
	 */
	int getIndexCenter();

	/**
	 * First fraction this {@code Feature} occurs in. This is also the number of
	 * the first fraction which contains a {@link Peak} contributing to this {@code Feature}
	 *
	 * @return index of first {@link Peak} contributing to this {@code Feature}
	 */
	int getIndexFirst();

	/**
	 * Last fraction this {@code Feature} occurs in. This is also the number of
	 * the last fraction which contains a {@link Peak} contributing to this {@code Feature}
	 *
	 * @return index of last {@link Peak} contributing to this {@code Feature}
	 */
	int getIndexLast();
}
