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
package net.sf.bioutils.proteomics.sample;

import net.sf.kerner.utils.hash.HashCalculator;
import net.sf.kerner.utils.hash.UtilHash;

public class HashCalculatorSample implements HashCalculator<Sample> {

	public int calculateHash(final Sample element) {

		return UtilHash.getHash(element.getName(), element.getNameBase(), element.getUser(), element.getProperties(), element.getPeaks());
	}

	public Integer transform(final Sample element) {

		return calculateHash(element);
	}
}
