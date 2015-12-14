/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.view;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.pair.KeyValue;

public class ViewPeakToFractionIndex extends KeyValue<Peak, Integer> {

	public ViewPeakToFractionIndex(final Peak key) {

		super(key, key.getFractionIndex());
	}
}
