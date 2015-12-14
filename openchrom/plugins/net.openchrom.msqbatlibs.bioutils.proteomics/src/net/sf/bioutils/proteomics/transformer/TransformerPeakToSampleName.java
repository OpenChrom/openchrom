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

import net.sf.bioutils.proteomics.provider.ProviderSampleName;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class TransformerPeakToSampleName extends AbstractTransformingListFactory<ProviderSampleName, String> {

	public TransformerPeakToSampleName() {

		super();
	}

	public TransformerPeakToSampleName(final FactoryList<String> factory) {

		super(factory);
	}

	
	public String transform(final ProviderSampleName element) {

		return element.getSampleName();
	}
}
