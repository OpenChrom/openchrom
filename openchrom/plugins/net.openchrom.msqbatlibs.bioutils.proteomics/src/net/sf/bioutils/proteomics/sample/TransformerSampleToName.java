/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

	@Override
	public String transform(final Sample element) {

		if(baseName)
			return element.getNameBase();
		return element.getName();
	}
}
