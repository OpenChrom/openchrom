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
import java.util.Iterator;
import java.util.List;

import net.sf.bioutils.proteomics.annotation.FeatureAnnotatable;
import net.sf.bioutils.proteomics.feature.Feature;
import net.sf.bioutils.proteomics.sample.Sample;

public class PeakBean extends PeakImpl implements FeatureAnnotatable, PeakModifiable {

	protected int indexFirst = -1, indexLast = -1;
	private int cacheHash;

	public PeakBean() {

		super();
	}

	public PeakBean(final double mz, final double intensity) {

		super(mz, intensity);
	}

	public PeakBean(final double mz, final double intensity, final int fractionIndex) {

		super(mz, intensity, fractionIndex);
	}

	public PeakBean(final Peak template) {

		super(template);
		if(template instanceof Feature) {
			final Feature f = (Feature)template;
			indexFirst = f.getIndexFirst();
			indexLast = f.getIndexLast();
		}
	}

	public PeakBean(final String name, final int fractionIndex, final double mz, final double intensity, final double intensityToNoise, final Sample sample) {

		super(name, fractionIndex, mz, intensity, intensityToNoise, sample);
	}

	/**
	 * Same as {@code return new PeakBean(this)}.
	 *
	 */
	
	public synchronized PeakBean clone() {

		final PeakBean result = new PeakBean(this);
		return result;
	}

	
	public synchronized int getIndexCenter() {

		return getFractionIndex();
	}

	
	public synchronized int getIndexFirst() {

		return indexFirst;
	}

	
	public synchronized int getIndexLast() {

		return indexLast;
	}

	
	public synchronized List<Peak> getMembers() {

		final List<Peak> result = new ArrayList<Peak>(1);
		result.add(this);
		return result;
	}

	
	public synchronized int hashCode() {

		int result = cacheHash;
		if(result == 0) {
			result = super.hashCode();
			cacheHash = result;
		}
		return result;
	}

	
	public synchronized Iterator<Peak> iterator() {

		return getMembers().iterator();
	}

	public synchronized PeakBean setIndexFirst(final int indexFirst) {

		if(getFractionIndex() >= 0 && indexFirst > getFractionIndex()) {
			// throw new IllegalArgumentException("First bigger then center");
		}
		if(indexLast >= 0 && indexFirst > indexLast) {
			// throw new IllegalArgumentException("First bigger then last");
		}
		this.indexFirst = indexFirst;
		return this;
	}

	public synchronized void setIndexLast(final int indexLast) {

		if(getFractionIndex() >= 0 && indexLast < getFractionIndex()) {
			// throw new IllegalArgumentException("Last smaller then center");
		}
		if(indexFirst >= 0 && indexLast < indexFirst) {
			// throw new IllegalArgumentException("Last smaller then first");
		}
		this.indexLast = indexLast;
	}

	
	public synchronized void setIntensity(final double intensity) {

		this.intensity = intensity;
	}

	
	public synchronized void setIntensityToNoise(final double intensityToNoise) {

		this.intensityToNoise = intensityToNoise;
	}

	public synchronized PeakBean setMz(final double mz) {

		this.mz = mz;
		return this;
	}

	
	public synchronized void setName(final String name) {

		this.name = name;
	}

	
	public synchronized void setSample(final Sample sample) {

		this.sample = sample;
	}

	
	public synchronized String toString() {

		String s = "n/a";
		if(sample != null) {
			s = getSampleName();
		}
		return "PeakBean:int:" + getIntensity() + ",mz:" + getMz() + ",frac:" + getIndexCenter() + ",s:" + s + ",a:" + getAnnotation();
	}
}
