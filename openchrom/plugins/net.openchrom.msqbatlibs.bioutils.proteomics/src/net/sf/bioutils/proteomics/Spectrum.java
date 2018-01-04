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
package net.sf.bioutils.proteomics;

import java.util.Collection;
import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 * 
 * A {@code Spectrum} is a {@link Collection} of {@link Peak Peaks} which result
 * from fractionation of another {@link Peak}.
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
 * last reviewed: 2013-07-08
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-07-08
 * 
 */
public interface Spectrum extends Iterable<Peak> {

	/**
	 * 
	 * @return this {@code Spectrum's} name, if available, or {@code null} otherwise
	 */
	String getName();

	/**
	 * 
	 * @return mass of parent ion which was fractionated
	 */
	double getParentMass();

	/**
	 * 
	 * @return {@link Peak Peaks} representing this {@code Spectrum}
	 */
	List<Peak> getPeaks();
}
