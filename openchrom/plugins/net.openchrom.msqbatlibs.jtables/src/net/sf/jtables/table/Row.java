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
package net.sf.jtables.table;

import java.util.List;

/**
 *
 * A table row.
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2014-12-03
 *
 * @param <T>
 *            type of table element
 */
public interface Row<T> extends List<T>, Cloneable {

	T get(Object o);

	List<Object> getIdentifier();

	boolean hasColumn(int index);

	boolean hasColumn(Object o);

	boolean isEmpty();

	void setIdentifier(List<? extends Object> identifier);

	String toString(String delimiter);
}
