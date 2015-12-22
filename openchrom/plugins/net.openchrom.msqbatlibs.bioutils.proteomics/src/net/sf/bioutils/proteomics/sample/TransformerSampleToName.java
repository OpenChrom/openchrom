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
package net.sf.bioutils.proteomics.sample;

import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;

public class TransformerSampleToName extends AbstractTransformingListFactory<Sample, String> {

	private final boolean baseName;

	public TransformerSampleToName() {

		this(false);
	}

	public TransformerSampleToName(final boolean baseName) {

		this.baseName = baseName;
	}

	public TransformerSampleToName(final FactoryList<String> factory) {

		this(factory, false);
	}

	public TransformerSampleToName(final FactoryList<String> factory, final boolean baseName) {

		super(factory);
		this.baseName = baseName;
	}

	public String transform(final Sample element) {

		if(baseName)
			return element.getNameBase();
		return element.getName();
	}
}
