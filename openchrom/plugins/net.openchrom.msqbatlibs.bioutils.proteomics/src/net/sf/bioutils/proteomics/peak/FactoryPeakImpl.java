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
package net.sf.bioutils.proteomics.peak;

import net.sf.bioutils.proteomics.sample.Sample;

public class FactoryPeakImpl implements FactoryPeak {

	private Sample sample;
	private int fractionIndex;

	public PeakImpl create(final double mz, final double intensity, final double intensityToNoise) {

		return create(null, mz, intensity, intensityToNoise);
	}

	public PeakImpl create(final String name, final double mz, final double intensity, final double intensityToNoise) {

		return new PeakImpl(name, getFractionIndex(), mz, intensity, intensityToNoise, getSample());
	}

	public synchronized int getFractionIndex() {

		return fractionIndex;
	}

	public synchronized Sample getSample() {

		return sample;
	}

	public synchronized void setFractionIndex(final int fractionIndex) {

		this.fractionIndex = fractionIndex;
	}

	public synchronized void setSample(final Sample sample) {

		this.sample = sample;
	}
}
