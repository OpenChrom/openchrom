/*******************************************************************************
 * Copyright (c) 2015 Alexander Kerner. All rights reserved.
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

package net.sf.kerner.utils.visitor;

public abstract class VisitorCounting<E> implements Visitor<E> {

	protected int cnt = 0;

	public synchronized int getCount() {
		return cnt;
	}

	public synchronized final Void transform(E element) {
		transform(element, cnt);
		cnt++;
		return null;
	}

	public abstract Void transform(E element, int cnt);

}
