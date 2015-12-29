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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MapSetObject extends MapSet<Object, Object> {

	public MapSetObject() {
	}

	public MapSetObject(final Map<Object, Set<Object>> map) {
		super(map);
	}

	public MapSetObject(final Map<Object, Set<Object>> map, final MapCollectionAbstract<Object, Object, Set<Object>> template) {
		super(map, template);
	}

	public MapSetObject(final MapCollectionAbstract<Object, Object, Set<Object>> template) {
		super(template);
	}

	@SuppressWarnings("unchecked")
	public void put(final Object key, final Object value) {

		if(value instanceof Collection) {
			putAll(key, (Collection<Object>)value);
		} else {
			super.put(key, value);
		}
	}
}
