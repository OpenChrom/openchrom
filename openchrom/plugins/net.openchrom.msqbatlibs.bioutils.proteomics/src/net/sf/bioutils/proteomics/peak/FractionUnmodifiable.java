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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;
import net.sf.kerner.utils.Util;

public class FractionUnmodifiable implements Fraction {

	private final Fraction delegate;

	public FractionUnmodifiable(final Fraction delegate) {
		Util.checkForNull(delegate);
		this.delegate = delegate;
	}

	public void addPeak(final Peak peak) {

		throw new UnsupportedOperationException();
	}

	public void addStandard(final Standard standard) {

		throw new UnsupportedOperationException();
	}

	public FractionUnmodifiable clone() {

		return new FractionUnmodifiable(delegate.clone());
	}

	public FractionUnmodifiable cloneWOPeaks() {

		return new FractionUnmodifiable(delegate.cloneWOPeaks());
	}

	public boolean equals(final Object obj) {

		return delegate.equals(obj);
	}

	public int getIndex() {

		return delegate.getIndex();
	}

	public String getName() {

		return delegate.getName();
	}

	public List<Peak> getPeaks() {

		final List<Peak> l = new ArrayList<Peak>(new TransformerPeakToUnmodifiable().transformCollection(delegate.getPeaks()));
		return Collections.unmodifiableList(l);
	}

	public Sample getSample() {

		return delegate.getSample();
	}

	public int getSize() {

		return delegate.getSize();
	}

	public Set<Standard> getStandards() {

		return Collections.unmodifiableSet(delegate.getStandards());
	}

	public int hashCode() {

		return delegate.hashCode();
	}

	public boolean isEmpty() {

		return delegate.isEmpty();
	}

	public void setPeaks(final Collection<? extends Peak> peaks) {

		delegate.setPeaks(peaks);
	}

	public void setSample(final Sample sample) {

		throw new UnsupportedOperationException();
	}

	public void setStandards(final Collection<? extends Standard> standards) {

		throw new UnsupportedOperationException();
	}

	public String toString() {

		return "FractionUnmodifiable:" + delegate;
	}
}
