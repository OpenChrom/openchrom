/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.bioutils.proteomics.provider.ProviderFractionIndex;
import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.bioutils.proteomics.provider.ProviderMz;
import net.sf.bioutils.proteomics.provider.ProviderSample;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.Cloneable;

/**
 *
 * A {@code Peak} represents a MS signal which has at least two properties:
 * <ol>
 * <li>a mass-to-charge-ratio</li>
 * <li>and a signal intensity</li>
 * </ol>
 * A {@code Peak} is usually also associated to a {@link Sample} and, if it was
 * detected during a MALDI-MS experiment, also a {@link Fraction}.
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface Peak extends ProviderIntensity, ProviderMz, ProviderFractionIndex, ProviderSample, Cloneable<Peak> {

	Peak clone();

	int getFractionIndex();

	String getName();

	Sample getSample();

	void setFractionIndex(int index);

	void setSample(Sample sample);
}
