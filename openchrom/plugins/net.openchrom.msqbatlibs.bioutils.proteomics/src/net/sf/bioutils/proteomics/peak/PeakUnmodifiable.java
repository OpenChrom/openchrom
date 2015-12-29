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

import java.util.Collection;
import java.util.Collections;

import net.sf.bioutils.proteomics.annotation.AnnotationSerializable;
import net.sf.bioutils.proteomics.annotation.PeakAnnotatable;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.Util;

public class PeakUnmodifiable implements PeakAnnotatable {

	protected final PeakAnnotatable delegate;

	public PeakUnmodifiable(final Peak delegate) {
		Util.checkForNull(delegate);
		this.delegate = (PeakAnnotatable)delegate;
	}

	public PeakUnmodifiable clone() {

		return new PeakUnmodifiable(delegate.clone());
	}

	public boolean equals(final Object obj) {

		return delegate.equals(obj);
	}

	public Collection<AnnotationSerializable> getAnnotation() {

		if(delegate.getAnnotation() == null) {
			return null;
		}
		return Collections.unmodifiableCollection(delegate.getAnnotation());
	}

	public int getFractionIndex() {

		return delegate.getFractionIndex();
	}

	public double getIntensity() {

		return delegate.getIntensity();
	}

	public double getIntensityToNoise() {

		return delegate.getIntensityToNoise();
	}

	public double getMz() {

		return delegate.getMz();
	}

	public String getName() {

		return delegate.getName();
	}

	public Sample getSample() {

		return new SampleUnmodifiable(delegate.getSample());
	}

	public String getSampleName() {

		return delegate.getSampleName();
	}

	public int hashCode() {

		return delegate.hashCode();
	}

	public void setAnnotation(final Collection<AnnotationSerializable> annotation) {

		throw new UnsupportedOperationException();
	}

	public void setFractionIndex(final int index) {

		throw new UnsupportedOperationException();
	}

	public void setSample(final Sample sample) {

		throw new UnsupportedOperationException();
	}

	public String toString() {

		return "PeakUnmodifiable:" + delegate;
	}
}
