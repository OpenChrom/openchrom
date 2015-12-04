package net.sf.bioutils.proteomics.peak;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import net.sf.bioutils.proteomics.User;
import net.sf.bioutils.proteomics.sample.RawSample;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.Util;
import net.sf.kerner.utils.collections.map.MapList;

public class SampleUnmodifiable implements Sample {

	private final Sample delegate;

	@Override
	public long getId() {

		return delegate.getId();
	}

	public SampleUnmodifiable(final Sample delegate) {

		Util.checkForNull(delegate);
		this.delegate = delegate;
	}

	@Override
	public RawSample getRawSample() {

		return delegate.getRawSample();
	}

	@Override
	public SampleUnmodifiable clone() {

		return new SampleUnmodifiable(delegate.clone());
	}

	@Override
	public Sample clone(final String newName) {

		return new SampleUnmodifiable(delegate.clone(newName));
	}

	@Override
	public Sample cloneWOPeaks(final String newName) {

		return delegate.cloneWOPeaks(newName);
	}

	@Override
	public boolean equals(final Object obj) {

		return delegate.equals(obj);
	}

	@Override
	public ReadWriteLock getLock() {

		return delegate.getLock();
	}

	@Override
	public String getName() {

		return delegate.getName();
	}

	@Override
	public String getNameBase() {

		return delegate.getNameBase();
	}

	@Override
	public List<Peak> getPeaks() {

		final List<Peak> l = new ArrayList<Peak>(new TransformerPeakToUnmodifiable().transformCollection(delegate.getPeaks()));
		return Collections.unmodifiableList(l);
	}

	@Override
	public MapList<String, Object> getProperties() {

		return delegate.getProperties();
	}

	@Override
	public int getSize() {

		return delegate.getSize();
	}

	@Override
	public User getUser() {

		return delegate.getUser();
	}

	@Override
	public int hashCode() {

		return delegate.hashCode();
	}

	@Override
	public void setPeaks(final List<Peak> peaks) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperties(final MapList<String, Object> properties) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {

		return "SampleUnmodifiable:" + delegate;
	}
}
