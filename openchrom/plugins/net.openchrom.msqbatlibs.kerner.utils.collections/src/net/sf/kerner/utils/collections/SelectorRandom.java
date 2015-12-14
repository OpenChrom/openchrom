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
package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.math.UtilRandom;

public class SelectorRandom<T> implements Selector<T> {

	public T select(Collection<? extends T> elements) {

		List<T> l = new ArrayList<T>(elements);
		int index = UtilRandom.generateBetween(0, l.size() - 1);
		return l.get(index);
	}
}
