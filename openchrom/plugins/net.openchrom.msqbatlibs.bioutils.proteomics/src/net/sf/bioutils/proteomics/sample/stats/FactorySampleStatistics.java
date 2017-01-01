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
package net.sf.bioutils.proteomics.sample.stats;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class FactorySampleStatistics extends AbstractTransformingListFactory<Sample, SampleStatistics> {

	public SampleStatistics transform(final Sample sample) {

		return new SampleStatisticsCallable(sample).call();
	}
}
