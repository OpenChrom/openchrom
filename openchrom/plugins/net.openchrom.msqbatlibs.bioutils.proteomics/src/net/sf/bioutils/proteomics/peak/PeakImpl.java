/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.peak;

import java.util.Collection;

import net.sf.bioutils.proteomics.annotation.AnnotatableElementProto;
import net.sf.bioutils.proteomics.annotation.AnnotationSerializable;
import net.sf.bioutils.proteomics.annotation.PeakAnnotatable;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.bioutils.proteomics.standard.Standard;
import net.sf.kerner.utils.collections.UtilCollection;

/**
 *
 * Prototype implementation of {@link Peak}.
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class PeakImpl extends AnnotatableElementProto implements Peak, Standard {

	public final static HashCalculatorPeak HASH_CALCULATOR_PEAK = new HashCalculatorPeak();
	public final static EqualatorPeak EQUALATOR_PEAK = new EqualatorPeak();
	protected Sample sample;
	protected double intensity;
	protected double intensityToNoise;
	protected double mz;
	protected String name;
	private int fractionIndex;

	public PeakImpl() {

		this(-1, -1);
	}

	public PeakImpl(final double mz, final double intensity) {

		this(mz, intensity, -1);
	}

	public PeakImpl(final double mz, final double intensity, final int fractionIndex) {

		this(null, fractionIndex, mz, intensity, -1, null);
	}

	public PeakImpl(final Peak template) {

		this(template.getName(), template.getFractionIndex(), template.getMz(), template.getIntensity(), template.getIntensityToNoise(), template.getSample());
		if(template instanceof PeakAnnotatable) {
			final Collection<AnnotationSerializable> annos = ((PeakAnnotatable)template).getAnnotation();
			if(UtilCollection.notNullNotEmpty(annos))
				getAnnotation().addAll(annos);
		}
	}

	public PeakImpl(final String name, final int fractionIndex, final double mz, final double intensity, final double intensityToNoise, final Sample sample) {

		super();
		this.intensity = intensity;
		this.intensityToNoise = intensityToNoise;
		this.mz = mz;
		this.name = name;
		this.sample = sample;
		this.fractionIndex = fractionIndex;
	}

	/**
	 * Performs a shallow copy of this {@code Peak}.
	 */
	@Override
	public synchronized PeakImpl clone() {

		final PeakImpl result = new PeakImpl(getName(), getFractionIndex(), getMz(), getIntensity(), getIntensityToNoise(), null);
		result.getAnnotation().addAll(getAnnotation());
		return result;
	}

	@Override
	public synchronized boolean equals(final Object obj) {

		return EQUALATOR_PEAK.areEqual(this, obj);
	}

	@Override
	public synchronized int getFractionIndex() {

		return fractionIndex;
	}

	@Override
	public synchronized double getIntensity() {

		return intensity;
	}

	@Override
	public synchronized double getIntensityToNoise() {

		return intensityToNoise;
	}

	@Override
	public synchronized double getMz() {

		return mz;
	}

	@Override
	public synchronized String getName() {

		return name;
	}

	@Override
	public synchronized Sample getSample() {

		return sample;
	}

	@Override
	public synchronized String getSampleName() {

		if(getSample() == null) {
			return null;
		}
		return getSample().getName();
	}

	@Override
	public synchronized int hashCode() {

		return HASH_CALCULATOR_PEAK.calculateHash(this);
	}

	@Override
	public synchronized void setFractionIndex(final int index) {

		fractionIndex = index;
	}

	@Override
	public synchronized void setSample(final Sample sample) {

		this.sample = sample;
	}

	@Override
	public synchronized String toString() {

		return getClass().getSimpleName() + ":mz:" + mz + ",int:" + intensity;
	}
}
