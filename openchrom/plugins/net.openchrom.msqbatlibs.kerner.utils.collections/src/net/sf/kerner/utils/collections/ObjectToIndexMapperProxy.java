/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * TODO description
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
 * @version Jun 18, 2012
 * @param <T>
 */
public class ObjectToIndexMapperProxy<T> extends ObjectToIndexMapperImpl<T> {

    protected Map<T, Object> identToIdent;

    public ObjectToIndexMapperProxy(final Map<T, Object> identToIdent) {
        super(new ArrayList<T>(identToIdent.keySet()));
        this.identToIdent = new LinkedHashMap<T, Object>(identToIdent);
    }

    @Override
    public int get(final T key) {
        final List<T> keySet = new ArrayList<T>(identToIdent.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (keySet.get(i) != null && keySet.get(i).equals(key)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Object getValue(final int index) {
        final Object o = super.getValue(index);
        for (final Object oo : identToIdent.values()) {
            if (o.equals(oo)) {
                return oo;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean containsKey(final T key) {
        return identToIdent.containsKey(key);
    }

    @Override
    public boolean containsValue(final int index) {
        return identToIdent.containsValue(super.getValue(index));
    }

    @Override
    public List<T> keys() {
        return new ArrayList<T>(identToIdent.keySet());
    }

    @Override
    public void addMapping(final T key) {
        throw new IllegalStateException();
    }

    @Override
    public void addMapping(final T key, final int index) {
        throw new IllegalStateException();
    }
}
