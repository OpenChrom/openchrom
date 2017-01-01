/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.ClonerImpl;

public class ClonerSample extends ClonerImpl<Sample> {

	private final String newName;
	private final ClonerPeak clonerPeak = new ClonerPeak();

	public ClonerSample(final String newName) {
		this.newName = newName;
	}

	public Sample clone(final Sample element) {

		final Sample result = element.clone(newName);
		result.setPeaks(clonerPeak.cloneList(element.getPeaks()));
		return result;
	}
}
