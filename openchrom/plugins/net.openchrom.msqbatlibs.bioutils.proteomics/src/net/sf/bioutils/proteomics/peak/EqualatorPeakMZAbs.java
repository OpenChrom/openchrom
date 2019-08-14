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

import net.sf.bioutils.proteomics.provider.ProviderMz;
import net.sf.kerner.utils.equal.EqualatorAbstract;
import net.sf.kerner.utils.math.UtilMath;

public class EqualatorPeakMZAbs extends EqualatorAbstract<Peak> {

	private final int accuracy;

	public EqualatorPeakMZAbs(final int accuracy) {
		super();
		this.accuracy = accuracy;
	}

	public boolean areEqual(final Peak o1, final Object o2) {

		return Double.valueOf(UtilMath.round(o1.getMz(), accuracy)).equals(Double.valueOf(UtilMath.round(((ProviderMz)o2).getMz(), accuracy)));
	}
}
