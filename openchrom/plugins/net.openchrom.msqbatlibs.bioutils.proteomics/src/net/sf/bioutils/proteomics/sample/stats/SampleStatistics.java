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
package net.sf.bioutils.proteomics.sample.stats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

public class SampleStatistics {

	public static SampleStatistics merge(final Collection<? extends SampleStatistics> stats) {

		final SampleStatistics result = new SampleStatistics();
		long intt = 0;
		long sn = 0;
		for(final SampleStatistics s : stats) {
			result.setNumberOfFeatures(result.getNumberOfFeatures() + s.getNumberOfFeatures());
			result.setNumberOfPeaks(result.getNumberOfPeaks() + s.getNumberOfPeaks());
			result.setTotalIntensity(result.getTotalIntensity().add(s.getTotalIntensity()));
			result.setTotalIntensityToNoise(result.getTotalIntensityToNoise().add(s.getTotalIntensityToNoise()));
			intt += s.getMedianPeakIntensity();
			sn += s.getMeanPeakSN();
			if(result.getMaxPeakInt() < s.getMaxPeakInt()) {
				result.setMaxPeakInt(s.getMaxPeakInt());
			}
			if(result.getMaxPeakMz() < s.getMaxPeakMz()) {
				result.setMaxPeakMz(s.getMaxPeakMz());
			}
			if(result.getMaxPeakSN() < s.getMaxPeakSN()) {
				result.setMaxPeakSN(s.getMaxPeakSN());
			}
			result.setMedianPeakIntensity(intt / stats.size());
			result.setMedianPeakSn(sn / stats.size());
		}
		return result;
	}

	private BigDecimal totalIntensity = BigDecimal.ZERO;
	private BigDecimal totalIntensityToNoise = BigDecimal.ZERO;
	private long numberOfPeaks = 0;
	private long numberOfFeatures = 0;
	private double medianPeakMz;
	private double maxPeakMz;
	private double maxPeakInt;
	private double maxPeakSN;
	private double medianPeakIntensity;
	private double medianPeakSn;

	public SampleStatistics() {
	}

	public SampleStatistics(final SampleStatistics s) {
		setMaxPeakInt(s.getMaxPeakInt());
		setTotalIntensityToNoise(s.getTotalIntensityToNoise());
		setTotalIntensity(s.getTotalIntensity());
		setMaxPeakMz(s.getMaxPeakMz());
		setMaxPeakSN(s.getMaxPeakSN());
		setMedianPeakIntensity(s.getMedianPeakIntensity());
		setMedianPeakMz(s.getMedianPeakMz());
		setMedianPeakSn(s.getMedianPeakSn());
		setNumberOfFeatures(s.getNumberOfFeatures());
		setNumberOfPeaks(s.getNumberOfPeaks());
	}

	public synchronized double getMaxPeakInt() {

		return maxPeakInt;
	}

	public synchronized double getMaxPeakMz() {

		return maxPeakMz;
	}

	public synchronized double getMaxPeakSN() {

		return maxPeakSN;
	}

	public synchronized double getMeanPeakIntensity() {

		if(getTotalIntensity().equals(BigDecimal.ZERO)) {
			return 0;
		}
		return getTotalIntensity().divide(BigDecimal.valueOf(numberOfPeaks), 6, RoundingMode.HALF_UP).doubleValue();
	}

	public synchronized double getMeanPeakSN() {

		if(getTotalIntensityToNoise().equals(BigDecimal.ZERO)) {
			return 0;
		}
		return getTotalIntensityToNoise().divide(BigDecimal.valueOf(numberOfPeaks), 6, RoundingMode.HALF_UP).doubleValue();
	}

	public synchronized double getMedianPeakIntensity() {

		return medianPeakIntensity;
	}

	public synchronized double getMedianPeakMz() {

		return medianPeakMz;
	}

	public synchronized double getMedianPeakSn() {

		return medianPeakSn;
	}

	public synchronized long getNumberOfFeatures() {

		return numberOfFeatures;
	}

	public synchronized long getNumberOfPeaks() {

		return numberOfPeaks;
	}

	public synchronized BigDecimal getTotalIntensity() {

		return totalIntensity;
	}

	public synchronized BigDecimal getTotalIntensityToNoise() {

		return totalIntensityToNoise;
	}

	public synchronized void incrementNumberOfFeatures() {

		numberOfFeatures++;
	}

	public synchronized void incrementNumberOfPeaks() {

		numberOfPeaks++;
	}

	public synchronized void setMaxPeakInt(final double maxPeakInt) {

		this.maxPeakInt = maxPeakInt;
	}

	public synchronized void setMaxPeakMz(final double maxPeakMz) {

		this.maxPeakMz = maxPeakMz;
	}

	public synchronized void setMaxPeakSN(final double maxPeakSN) {

		this.maxPeakSN = maxPeakSN;
	}

	public synchronized void setMedianPeakIntensity(final double medianPeakIntensity) {

		this.medianPeakIntensity = medianPeakIntensity;
	}

	public synchronized void setMedianPeakMz(final double medianPeakMz) {

		this.medianPeakMz = medianPeakMz;
	}

	public synchronized void setMedianPeakSn(final double medianPeakSn) {

		this.medianPeakSn = medianPeakSn;
	}

	public synchronized void setNumberOfFeatures(final long numberOfFeatures) {

		this.numberOfFeatures = numberOfFeatures;
	}

	public synchronized void setNumberOfPeaks(final long numberOfPeaks) {

		this.numberOfPeaks = numberOfPeaks;
	}

	public synchronized void setTotalIntensity(final BigDecimal totalIntensity) {

		this.totalIntensity = totalIntensity;
	}

	public synchronized void setTotalIntensityToNoise(final BigDecimal totalIntensityToNoise) {

		this.totalIntensityToNoise = totalIntensityToNoise;
	}
}
