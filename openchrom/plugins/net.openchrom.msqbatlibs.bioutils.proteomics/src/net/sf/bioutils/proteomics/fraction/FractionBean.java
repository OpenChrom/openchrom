/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;

public class FractionBean implements Fraction {

	private int fractionIndex;
	private String name;
	private Sample sample;
	private List<Peak> peaks = new ArrayList<Peak>(0);
	private Set<Standard> standards = new LinkedHashSet<Standard>(0);

	public FractionBean() {
	}

	public FractionBean(final int fractionIndex) {
		this.fractionIndex = fractionIndex;
	}

	public FractionBean(final Sample sample, final int fractionIndex) {
		this.fractionIndex = fractionIndex;
		this.sample = sample;
	}

	public FractionBean(final String name, final int fractionIndex, final Collection<? extends Peak> peaks, final Collection<? extends Standard> standards) {
		this.name = name;
		this.fractionIndex = fractionIndex;
		if(peaks != null)
			this.peaks = new ArrayList<Peak>(peaks);
		if(standards != null)
			this.standards = new LinkedHashSet<Standard>(standards);
	}

	public synchronized void addPeak(final Peak peak) {

		peaks.add(peak);
	}

	public synchronized void addStandard(final Standard peak) {

		peaks.add(peak);
	}

	public FractionBean clone() {

		return new FractionBean(name, fractionIndex, peaks, standards);
	}

	public FractionBean cloneWOPeaks() {

		return new FractionBean(name, fractionIndex, null, null);
	}

	public synchronized int getIndex() {

		return fractionIndex;
	}

	public synchronized String getName() {

		return name;
	}

	public synchronized List<Peak> getPeaks() {

		return peaks;
	}

	public synchronized Sample getSample() {

		return sample;
	}

	public synchronized int getSize() {

		return getPeaks().size();
	}

	public synchronized Set<Standard> getStandards() {

		return standards;
	}

	public synchronized boolean isEmpty() {

		return getPeaks().isEmpty();
	}

	public boolean sampleMatch(final Collection<? extends Peak> peaks) {

		for(final Peak p : peaks) {
			if(sampleMatch(p)) {
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean sampleMatch(final Peak peak) {

		if(peak.getSample() != null && getSample() != null && !peak.getSample().equals(getSample())) {
			return false;
		}
		return true;
	}

	public synchronized void setIndex(final int fractionIndex) {

		this.fractionIndex = fractionIndex;
	}

	public synchronized void setName(final String name) {

		this.name = name;
	}

	public synchronized void setPeaks(final Collection<? extends Peak> peaks) {

		this.peaks = new ArrayList<Peak>(peaks);
	}

	public synchronized void setSample(final Sample sample) {

		this.sample = sample;
	}

	public synchronized void setStandards(final Collection<? extends Standard> standards) {

		this.standards = new LinkedHashSet<Standard>(standards);
	}
}
