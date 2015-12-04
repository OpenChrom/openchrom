/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
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
package net.sf.bioutils.proteomics.annotation;

import java.util.Comparator;

import net.sf.kerner.utils.collections.ComparatorNull;

public class ComparatorAnnotation extends ComparatorNull<AnnotatableElement> implements Comparator<AnnotatableElement> {

	@Override
	public int compareNonNull(final AnnotatableElement o1, final AnnotatableElement o2) {

		final boolean e1 = o1.getAnnotation() == null || o1.getAnnotation().isEmpty();
		final boolean e2 = o2.getAnnotation() == null || o2.getAnnotation().isEmpty();
		if(e1 && e2) {
			return 0;
		}
		if(!e1 && !e2) {
			return 0;
		}
		if(e1) {
			return 1;
		}
		if(e2) {
			return -1;
		}
		throw new RuntimeException();
	}
}
