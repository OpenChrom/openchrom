/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.transformer.Transformer;

public class TransformerSampleSubset<S extends Sample> implements Transformer<S, S> {

	private int cntPerFraction;

	public S transform(final S element) {

		// TODO Auto-generated method stub
		return null;
	}
}
