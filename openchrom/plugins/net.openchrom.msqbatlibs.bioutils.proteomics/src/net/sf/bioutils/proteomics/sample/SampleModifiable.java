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
package net.sf.bioutils.proteomics.sample;

import java.util.Collection;

import net.sf.bioutils.proteomics.User;
import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * {@code SampleModifiable} extends {@link Sample} by modifiability.
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
 * </p>
 * <p>
 * last reviewed: 2013-09-23
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 *
 */
public interface SampleModifiable extends Sample {

	void addPeak(Peak peak);

	void addPeaks(Collection<? extends Peak> peak);

	void removePeak(Peak peak);

	void removePeaks(Collection<? extends Peak> peak);

	/**
	 *
	 * @param name
	 *            new name for this {@code Sample}
	 */
	void setName(String name);

	void setNameBase(String baseName);

	/**
	 *
	 * @param user
	 *            new {@link User} for this {@code Sample}
	 */
	void setUser(User user);
}
