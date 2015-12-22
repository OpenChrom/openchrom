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

import java.util.LinkedHashMap;
import java.util.Map;

public class FactoryLinkedHashMap<K, V> implements FactoryMap<K, V> {

	public Map<K, V> create() {

		return new LinkedHashMap<K, V>();
	}
}
