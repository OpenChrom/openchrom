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
package net.sf.kerner.utils.collections.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FilterGiven implements Filter<Object> {

    private final Set<Object> set;

    public FilterGiven(final Collection<Object> objects) {
        set = new HashSet<Object>(objects);
    }

    public boolean filter(final Object e) {
        return set.contains(e);
    }

}
