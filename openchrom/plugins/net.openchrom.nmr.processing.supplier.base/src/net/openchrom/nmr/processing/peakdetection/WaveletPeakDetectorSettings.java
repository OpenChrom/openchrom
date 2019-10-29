/*******************************************************************************
 * Copyright (c) 2019 Alexander Stark.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
*******************************************************************************/
package net.openchrom.nmr.processing.peakdetection;

public class WaveletPeakDetectorSettings {

	private static final int[] DEFAULT_PSI_SCALES = { 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 36, 40, 44, 48, 52, 56, 60, 64 };
	private int[] psiScales = DEFAULT_PSI_SCALES;
	//
	private boolean considerNearbyPeaks = true;
	private int signalToNoiseThreshold = 3;
	private double amplitudeThreshold = 0.01d;
	private int ridgeLength = 24;
	private int peakScaleRange = 5;

	public double getAmplitudeThreshold() {
		return amplitudeThreshold;
	}

	public void setAmplitudeThreshold(double amplitudeThreshold) {
		this.amplitudeThreshold = amplitudeThreshold;
	}

	public boolean isConsiderNearbyPeaks() {
		return considerNearbyPeaks;
	}

	public void setConsiderNearbyPeaks(boolean considerNearbyPeaks) {
		this.considerNearbyPeaks = considerNearbyPeaks;
	}

	public int getSignalToNoiseThreshold() {
		return signalToNoiseThreshold;
	}

	public void setSignalToNoiseThreshold(int signalToNoiseThreshold) {
		this.signalToNoiseThreshold = signalToNoiseThreshold;
	}

	public int getRidgeLength() {
		return ridgeLength;
	}

	public void setRidgeLength(int ridgeLength) {
		this.ridgeLength = ridgeLength;
	}

	public int getPeakScaleRange() {
		return peakScaleRange;
	}

	public void setPeakScaleRange(int peakScaleRange) {
		this.peakScaleRange = peakScaleRange;
	}

	public int[] getPsiScales() {
		return psiScales;
	}

	public void setPsiScales(int[] psiScales) {
		this.psiScales = psiScales;
	}
}
