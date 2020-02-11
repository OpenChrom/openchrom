/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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
	private static final int[] EXT_DEFAULT_PSI_SCALES = { 0, 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 36, 40, 44, 48, 52, 56, 60, 64 };
	private int[] psiScales = DEFAULT_PSI_SCALES;
	private int[] extendedPsiScales = EXT_DEFAULT_PSI_SCALES;

	//
	private boolean considerNearbyPeaks = true;
	private int signalToNoiseThreshold = 3;
	private double amplitudeThreshold = 0.0001d;
	private int ridgeLength = 24;
	private int peakScaleRange = 5;
	//
	private int minimumWindowSize = 5;
	//
	private int gapThreshold = 3;
	private int skipValue = 2;
	//
	private double minimumNoiseLevel = 0.003;
	private int nearbyWinSize = 100;
	private int windowSizeNoise = 500;

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

	public int getMinimumWindowSize() {
		return minimumWindowSize;
	}

	public void setMinimumWindowSize(int minimumWindowSize) {
		this.minimumWindowSize = minimumWindowSize;
	}

	public int getGapThreshold() {
		return gapThreshold;
	}

	public void setGapThreshold(int gapThreshold) {
		this.gapThreshold = gapThreshold;
	}

	public int getSkipValue() {
		return skipValue;
	}

	public void setSkipValue(int skipValue) {
		this.skipValue = skipValue;
	}

	public int[] getExtendedPsiScales() {
		return extendedPsiScales;
	}

	public void setExtendedPsiScales(int[] extendedPsiScales) {
		this.extendedPsiScales = extendedPsiScales;
	}

	public double getMinimumNoiseLevel() {
		return minimumNoiseLevel;
	}

	public void setMinimumNoiseLevel(double minimumNoiseLevel) {
		this.minimumNoiseLevel = minimumNoiseLevel;
	}

	public int getNearbyWinSize() {
		return nearbyWinSize;
	}

	public void setNearbyWinSize(int nearbyWinSize) {
		this.nearbyWinSize = nearbyWinSize;
	}

	public int getWindowSizeNoise() {
		return windowSizeNoise;
	}

	public void setWindowSizeNoise(int windowSizeNoise) {
		this.windowSizeNoise = windowSizeNoise;
	}

}
