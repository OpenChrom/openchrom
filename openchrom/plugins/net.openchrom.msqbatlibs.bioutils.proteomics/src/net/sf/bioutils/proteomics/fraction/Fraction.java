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
package net.sf.bioutils.proteomics.fraction;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;
import net.sf.kerner.utils.Cloneable;

/**
 * A {@code Fraction} is a collection of {@link Peak Peaks} eluting at the same
 * time.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed 2014-04-09
 * </p>
 *
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Fraction extends Cloneable<Fraction> {

	public void addPeak(Peak peak);

	void addStandard(Standard standard);

	/**
	 * Creates a clone of this {@code Fraction}. {@link Sample} of the new {@code Fraction} instance will be {@code null}.
	 */
	public Fraction clone();

	/**
	 *
	 * Clones this {@code Fraction}, dismissing peaks.
	 *
	 * @see #clone()
	 */
	Fraction cloneWOPeaks();

	int getIndex();

	/**
	 * Returns the name of this fraction.
	 *
	 * @return name of this fraction
	 */
	String getName();

	List<Peak> getPeaks();

	Sample getSample();

	/**
	 * Sample getSample();
	 *
	 *
	 *
	 * /** Get number of {@link Peak peaks} in this fraction.
	 *
	 * @return number of {@link Peak peaks} in this fraction
	 */
	int getSize();

	/**
	 * Returns this fraction's {@link Standard standards}.
	 *
	 * @return this fraction's {@link Standard standards}
	 */
	Set<Standard> getStandards();

	/**
	 * Checks weather this fraction is empty {@code getSize() == 0}.
	 *
	 * @return {@code true}, if number of {@link Peak peaks} in this fraction is {@code 0}; {@code false} otherwise
	 */
	boolean isEmpty();

	void setPeaks(Collection<? extends Peak> peaks);

	void setSample(Sample sample);

	void setStandards(Collection<? extends Standard> standards);
}
