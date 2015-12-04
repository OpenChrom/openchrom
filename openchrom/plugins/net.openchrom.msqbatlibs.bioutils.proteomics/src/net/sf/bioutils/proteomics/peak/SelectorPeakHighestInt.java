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
package net.sf.bioutils.proteomics.peak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sf.kerner.utils.collections.ComparatorInverter;
import net.sf.kerner.utils.collections.Selector;

public class SelectorPeakHighestInt<T extends Peak> implements Selector<T> {

	@Override
	public T select(final Collection<? extends T> elements) {

		final ArrayList<T> list = new ArrayList<T>(elements);
		Collections.sort(list, new ComparatorInverter<Peak>(new ComparatorPeakByIntensity()));
		return list.get(0);
	}
}
