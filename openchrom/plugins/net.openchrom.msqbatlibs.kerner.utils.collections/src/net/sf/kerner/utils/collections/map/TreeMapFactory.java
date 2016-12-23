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
package net.sf.kerner.utils.collections.map;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapFactory<K, V> implements FactoryMap<K, V> {

	public Map<K, V> create() {

		return new TreeMap<K, V>();
	}
}
