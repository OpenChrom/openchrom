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
package net.openchrom.nmr.processing.peakdetection.peakmodel;

public class CwtPeak {

	private int ridgeLevel;
	private int index;
	private double value;
	private int centerIndex;
	private double signalToNoiseRatio;
	private double scale;

	public int getRidgeLevel() {
		return ridgeLevel;
	}

	public void setRidgeLevel(int ridgeLevel) {
		this.ridgeLevel = ridgeLevel;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getCenterIndex() {
		return centerIndex;
	}

	public void setCenterIndex(int centerIndex) {
		this.centerIndex = centerIndex;
	}

	public double getSignalToNoiseRatio() {
		return signalToNoiseRatio;
	}

	public void setSignalToNoiseRatio(double signalToNoiseRatio) {
		this.signalToNoiseRatio = signalToNoiseRatio;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

}
