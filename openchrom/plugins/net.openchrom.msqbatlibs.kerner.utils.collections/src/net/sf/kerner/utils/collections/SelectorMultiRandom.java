/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

public class SelectorMultiRandom<T> implements SelectorMulti<T> {

	public final static double DEFAULT_PROP = 1.0;
	private double prop = DEFAULT_PROP;

	public SelectorMultiRandom() {
	}

	public SelectorMultiRandom(double prop) {
		this.prop = prop;
	}

	public synchronized double getProp() {

		return prop;
	}

	public Collection<T> select(Collection<? extends T> elements) {

		List<T> result = new ArrayList<T>();
		for(T t : elements) {
			if(UtilRandom.generateWithProbability(getProp())) {
				result.add(t);
			}
		}
		return result;
	}

	public synchronized void setProp(double prop) {

		this.prop = prop;
	}
}
