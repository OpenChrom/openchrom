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
package net.sf.kerner.utils.collections.trasformer;

import java.util.Enumeration;
import java.util.Iterator;

import net.sf.kerner.utils.transformer.Transformer;

public class TransformerEnumerationToIterator<T> implements Transformer<Enumeration<T>, Iterator<T>> {

	public Iterator<T> transform(Enumeration<T> enumeration) {

		return new TransformerEnumerationToIterable<T>().transform(enumeration).iterator();
	}
}
