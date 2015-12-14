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

	
	public long getId() {

		return delegate.getId();
	}

	public SampleUnmodifiable(final Sample delegate) {

		Util.checkForNull(delegate);
		this.delegate = delegate;
	}

	
	public RawSample getRawSample() {

		return delegate.getRawSample();
	}

	
	public SampleUnmodifiable clone() {

		return new SampleUnmodifiable(delegate.clone());
	}

	
	public Sample clone(final String newName) {

		return new SampleUnmodifiable(delegate.clone(newName));
	}

	
	public Sample cloneWOPeaks(final String newName) {

		return delegate.cloneWOPeaks(newName);
	}

	
	public boolean equals(final Object obj) {

		return delegate.equals(obj);
	}

	
	public ReadWriteLock getLock() {

		return delegate.getLock();
	}

	
	public String getName() {

		return delegate.getName();
	}

	
	public String getNameBase() {

		return delegate.getNameBase();
	}

	
	public List<Peak> getPeaks() {

		final List<Peak> l = new ArrayList<Peak>(new TransformerPeakToUnmodifiable().transformCollection(delegate.getPeaks()));
		return Collections.unmodifiableList(l);
	}

	
	public MapList<String, Object> getProperties() {

		return delegate.getProperties();
	}

	
	public int getSize() {

		return delegate.getSize();
	}

	
	public User getUser() {

		return delegate.getUser();
	}

	
	public int hashCode() {

		return delegate.hashCode();
	}

	
	public void setPeaks(final List<Peak> peaks) {

		throw new UnsupportedOperationException();
	}

	
	public void setProperties(final MapList<String, Object> properties) {

		throw new UnsupportedOperationException();
	}

	
	public String toString() {

		return "SampleUnmodifiable:" + delegate;
	}
}
