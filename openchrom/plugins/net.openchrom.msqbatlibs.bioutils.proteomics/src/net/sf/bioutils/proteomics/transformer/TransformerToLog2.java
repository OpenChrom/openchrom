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

import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;
import net.sf.kerner.utils.math.UtilMath;

public class TransformerToLog2 extends AbstractTransformingListFactory<Double, Double> {

	public TransformerToLog2() {
		super();
	}

	public TransformerToLog2(final FactoryList<Double> factory) {
		super(factory);
	}

	public Double transform(final Double element) {

		return UtilMath.log2(element);
	}
}
