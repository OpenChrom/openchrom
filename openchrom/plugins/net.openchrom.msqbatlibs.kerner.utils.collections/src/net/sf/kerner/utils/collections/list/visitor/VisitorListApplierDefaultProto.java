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
package net.sf.kerner.utils.collections.list.visitor;

import java.util.Collection;

import net.sf.kerner.utils.collections.UtilCollection;

public class VisitorListApplierDefaultProto<E> implements VisitorApplierListDefault<E> {

	protected final Collection<VisitorList<Void, E>> visitors = UtilCollection.newCollection();

	public synchronized void addVisitor(final VisitorList<Void, E> visitor) {

		visitors.add(visitor);
	}

	public synchronized void clearVisitors() {

		visitors.clear();
	}

	public void visit(final E e, final int index) {

		for(final VisitorList<Void, E> v : visitors) {
			v.visit(e, index);
		}
	};
}
