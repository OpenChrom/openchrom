/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

/**
 *
 * Extends {@link Peak} by modifiability.
 * <ol>
 * <li>Intensity,</li>
 * <li>name</li>
 * <li>and sample</li>
 * </ol>
 * may be set.
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 *
 * </p>
 * <p>
 * last reviewed: 2015-06-14
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface PeakModifiable extends Peak, PeakModifiableIntensity {

	void setName(String name);

	void setSample(Sample sample);
}
