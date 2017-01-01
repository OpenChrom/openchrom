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
package net.sf.kerner.utils.transformer;

import javax.swing.text.View;

import net.sf.kerner.utils.pair.KeyValue;

/**
 * A {@link View} to a {@link KeyValue}, which returns only the key from this {@link KeyValue}.
 * <p>
 * <b>Example:</b><br>
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
 * @version 2012-04-25
 * @param <K>
 *            type of key
 */
public class ViewKeyValueKey<K> implements Transformer<KeyValue<K, ?>, K> {

	/**
	 * 
	 */
	public K transform(final KeyValue<K, ?> element) {

		return element.getKey();
	}
}
