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
	
	public synchronized PeakImpl clone() {

		final PeakImpl result = new PeakImpl(getName(), getFractionIndex(), getMz(), getIntensity(), getIntensityToNoise(), null);
		result.getAnnotation().addAll(getAnnotation());
		return result;
	}

	
	public synchronized boolean equals(final Object obj) {

		return EQUALATOR_PEAK.areEqual(this, obj);
	}

	
	public synchronized int getFractionIndex() {

		return fractionIndex;
	}

	
	public synchronized double getIntensity() {

		return intensity;
	}

	
	public synchronized double getIntensityToNoise() {

		return intensityToNoise;
	}

	
	public synchronized double getMz() {

		return mz;
	}

	
	public synchronized String getName() {

		return name;
	}

	
	public synchronized Sample getSample() {

		return sample;
	}

	
	public synchronized String getSampleName() {

		if(getSample() == null) {
			return null;
		}
		return getSample().getName();
	}

	
	public synchronized int hashCode() {

		return HASH_CALCULATOR_PEAK.calculateHash(this);
	}

	
	public synchronized void setFractionIndex(final int index) {

		fractionIndex = index;
	}

	
	public synchronized void setSample(final Sample sample) {

		this.sample = sample;
	}

	
	public synchronized String toString() {

		return getClass().getSimpleName() + ":mz:" + mz + ",int:" + intensity;
	}
}
