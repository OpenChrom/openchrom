/**
 * *****************************************************************************
 * Copyright (c) 2010-2015 Alexander Kerner. All rights reserved.
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
 *****************************************************************************
 */
package net.sf.kerner.utils.collections.visitor;

import java.util.Collection;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.visitor.Visitor;
import net.sf.kerner.utils.visitor.VisitorApplier;

public class VisitorApplierProto<E> implements VisitorApplier<E> {

    protected final Collection<Visitor<E>> visitors = UtilCollection
            .newCollection();

    public synchronized void addVisitor(final Visitor<E> visitor) {
        visitors.add(visitor);
    }

    public synchronized void clearVisitors() {
        visitors.clear();
    }

    public synchronized Void transform(final E e) {
        for (final Visitor<E> v : visitors) {
            v.transform(e);
        }
        return null;
    }
}
