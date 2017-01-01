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
package net.sf.bioutils.proteomics.peak;

import net.sf.kerner.utils.hash.HashCalculatorAbstract;
import net.sf.kerner.utils.hash.UtilHash;
import net.sf.kerner.utils.math.UtilMath;

public class HashCalculatorPeak extends HashCalculatorAbstract<Peak> {

	public int calculateHash(final Peak peak) {

		return UtilHash.getHash(peak.getSampleName(), peak.getFractionIndex(), UtilMath.round(peak.getMz(), 4));
	}

	public Integer transform(final Peak element) {

		return Integer.valueOf(calculateHash(element));
	}
}
