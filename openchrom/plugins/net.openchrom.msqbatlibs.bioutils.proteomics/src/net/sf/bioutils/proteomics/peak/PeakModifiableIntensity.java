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

/**
 * 
 * Extends {@link Peak} by modifiability.
 *
 * <p>
 * <b>Example:</b><br>
 * </p>
 * 
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 *
 * <p>
 * last reviewed: 2015-06-14
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface PeakModifiableIntensity {

	/**
	 * Sets this Peak's intensity to given intensity.
	 *
	 * @param intensity
	 *            new intensity
	 */
	void setIntensity(double intensity);

	/**
	 * Sets this Peak's s/n to given s/n.
	 *
	 * @param intensityToNoise
	 *            new s/n
	 */
	void setIntensityToNoise(double intensityToNoise);
}
