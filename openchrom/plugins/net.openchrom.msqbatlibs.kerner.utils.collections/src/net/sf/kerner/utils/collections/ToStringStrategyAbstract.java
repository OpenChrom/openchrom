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
package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.transformer.Transformer;

public abstract class ToStringStrategyAbstract<T> implements Transformer<T, String> {

	public final static String DEFAULT_DELIMITER = "\t";
	public final static String DEFAULT_SEPARATOR = UtilString.NEW_LINE_STRING;
	protected String separator = DEFAULT_SEPARATOR;
	protected String delimiter = DEFAULT_DELIMITER;
	protected List<?> prefix = UtilList.newList();
	protected List<?> postfix = UtilList.newList();

	/**
	 * e.g. {@code \t}
	 *
	 */
	public synchronized String getDelimiter() {

		return delimiter;
	}

	public abstract String getEmptyRow();

	public synchronized List<?> getPostfix() {

		return postfix;
	}

	public synchronized List<?> getPrefix() {

		return prefix;
	}

	/**
	 * e.g. {@code \n}
	 *
	 */
	public synchronized String getSeparator() {

		return separator;
	}

	public synchronized void setDelimiter(final String delimiter) {

		this.delimiter = delimiter;
	}

	public synchronized void setPostfix(final List<?> postfix) {

		this.postfix = postfix;
	}

	public synchronized void setPrefix(final List<?> prefix) {

		this.prefix = prefix;
	}

	public synchronized void setSeparator(final String separator) {

		this.separator = separator;
	}

	public String toString(final Collection<? extends T> elements) {

		return UtilCollection.toString(new ArrayList<T>(elements), this, separator);
	}

	public String toString(final T element) {

		return transform(element);
	}
}
