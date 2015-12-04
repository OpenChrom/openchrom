/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.feature;

import net.sf.bioutils.proteomics.ComposableElement;
import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * A {@code Feature} is a {@link ComposableElement} of {@link Peak Peaks}. It
 * represents one single peptide that was detected. One peptide is composed of
 * multiple signals (peaks). </p>
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Feature extends ComposableElement<Peak>, Peak, Iterable<Peak> {

	@Override
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
