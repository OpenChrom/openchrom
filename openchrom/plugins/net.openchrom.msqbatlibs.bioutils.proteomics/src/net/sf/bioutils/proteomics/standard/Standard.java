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
package net.sf.bioutils.proteomics.standard;

import net.sf.bioutils.proteomics.peak.Peak;

/**
 *
 * A {@link Standard} is a {@link Peak} that is used to normalize one or more
 * other {@link Peak Peak's} intensities or masses.
 * </p>
 * Its typically spiked
 * into a sample during sample preparation or processing.
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
public interface Standard extends Peak {

	public Standard clone();
}
