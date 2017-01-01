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
package net.sf.kerner.utils.collections.trasformer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

public class TransformerEnumerationToIterable<T> implements Transformer<Enumeration<T>, Iterable<T>> {

	public Iterable<T> transform(Enumeration<T> enumeration) {

		final List<T> v = new ArrayList<T>();
		while(enumeration.hasMoreElements()) {
			v.add(enumeration.nextElement());
		}
		return v;
	}
}
