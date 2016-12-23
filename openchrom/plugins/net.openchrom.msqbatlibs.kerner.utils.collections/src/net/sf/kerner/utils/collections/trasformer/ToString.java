/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.util.Iterator;
import java.util.ListIterator;

import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.list.visitor.VisitorList;
import net.sf.kerner.utils.transformer.TransformerToString;
import net.sf.kerner.utils.transformer.TransformerToStringDefault;

public class ToString {

	public final static String DEFAULT_OBJECT_SEPARATOR = ", ";
	private String objectSeparator = DEFAULT_OBJECT_SEPARATOR;

	public synchronized String getObjectSeparator() {

		return objectSeparator;
	}

	public synchronized ToString setObjectSeparator(String objectSeparator) {

		this.objectSeparator = objectSeparator;
		return this;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public synchronized <T> String toString(final Iterable<T> iterable) {

		return toString(iterable, (TransformerToString)new TransformerToStringDefault());
	}

	public synchronized <T> String toString(final Iterable<T> iterable, final TransformerToString<T> transformer) {

		final StringBuilder sb = new StringBuilder();
		final Iterator<T> it = iterable.iterator();
		while(it.hasNext()) {
			sb.append(transformer.transform(it.next()));
			if(it.hasNext())
				sb.append(UtilString.NEW_LINE_STRING);
		}
		return sb.toString();
	}

	public synchronized <T> String toString(final ListIterator<T> it, final VisitorList<String, T> visitor) {

		final StringBuilder sb = new StringBuilder();
		while(it.hasNext()) {
			final int index = it.nextIndex();
			sb.append(visitor.visit(it.next(), index));
			if(it.hasNext())
				sb.append(UtilString.NEW_LINE_STRING);
		}
		return sb.toString();
	}
}
