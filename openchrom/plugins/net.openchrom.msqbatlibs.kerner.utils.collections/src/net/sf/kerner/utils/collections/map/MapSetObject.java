/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
	@Override
	public void put(final Object key, final Object value) {

		if(value instanceof Collection) {
			putAll(key, (Collection<Object>)value);
		} else {
			super.put(key, value);
		}
	}
}
