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
package net.sf.bioutils.proteomics.transformer;

import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerPeakToIntensityToNoise extends AbstractTransformingListFactory<ProviderIntensity, Double> {

	public Double transform(final ProviderIntensity element) {

		return element.getIntensityToNoise();
	}
}
