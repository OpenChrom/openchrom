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

	@Override
	public PeakUnmodifiable clone() {

		return new PeakUnmodifiable(delegate.clone());
	}

	@Override
	public boolean equals(final Object obj) {

		return delegate.equals(obj);
	}

	@Override
	public Collection<AnnotationSerializable> getAnnotation() {

		if(delegate.getAnnotation() == null) {
			return null;
		}
		return Collections.unmodifiableCollection(delegate.getAnnotation());
	}

	@Override
	public int getFractionIndex() {

		return delegate.getFractionIndex();
	}

	@Override
	public double getIntensity() {

		return delegate.getIntensity();
	}

	@Override
	public double getIntensityToNoise() {

		return delegate.getIntensityToNoise();
	}

	@Override
	public double getMz() {

		return delegate.getMz();
	}

	@Override
	public String getName() {

		return delegate.getName();
	}

	@Override
	public Sample getSample() {

		return new SampleUnmodifiable(delegate.getSample());
	}

	@Override
	public String getSampleName() {

		return delegate.getSampleName();
	}

	@Override
	public int hashCode() {

		return delegate.hashCode();
	}

	@Override
	public void setAnnotation(final Collection<AnnotationSerializable> annotation) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setFractionIndex(final int index) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setSample(final Sample sample) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {

		return "PeakUnmodifiable:" + delegate;
	}
}
