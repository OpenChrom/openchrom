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
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class FactoryViewPeakToFractionIndex extends AbstractTransformingListFactory<Peak, ViewPeakToFractionIndex> {

	public FactoryViewPeakToFractionIndex() {

		super();
	}

	public FactoryViewPeakToFractionIndex(final FactoryList<ViewPeakToFractionIndex> factory) {

		super(factory);
	}

	
	public ViewPeakToFractionIndex transform(final Peak element) {

		return new ViewPeakToFractionIndex(element);
	}
}
