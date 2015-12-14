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
package net.sf.bioutils.proteomics.transformer;

import net.sf.bioutils.proteomics.provider.ProviderSample;
import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class TransformerPeakToSample extends AbstractTransformingListFactory<ProviderSample, Sample> {

	public TransformerPeakToSample() {

		super();
	}

	public TransformerPeakToSample(final FactoryList<Sample> factory) {

		super(factory);
	}

	
	public Sample transform(final ProviderSample element) {

		return element.getSample();
	}
}
