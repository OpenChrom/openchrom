/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.map;

import java.util.Map;

import net.sf.kerner.utils.Factory;

/**
 * A {@code MapFactory} provides factory methods to retrieve all kind of direct and indirect implementations of {@link java.util.Map Map}.
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
 * @version 2010-11-12
 * @param <K>
 *            type of keys in the map
 * @param <V>
 *            type of values in the map
 */
public interface FactoryMap<K, V> extends Factory<Map<K, V>> {
}
