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
package net.sf.bioutils.proteomics.peak;

import java.util.Comparator;

import net.sf.bioutils.proteomics.annotation.AnnotatableElement;
import net.sf.bioutils.proteomics.annotation.ComparatorAnnotation;

public class ComparatorPeakByAnnotation implements Comparator<Peak> {

	private final static ComparatorAnnotation COMPARATOR_ANNOTATION = new ComparatorAnnotation();

	@Override
	public int compare(final Peak o1, final Peak o2) {

		if(o1 instanceof AnnotatableElement && o2 instanceof AnnotatableElement) {
			return COMPARATOR_ANNOTATION.compare((AnnotatableElement)o1, (AnnotatableElement)o2);
		}
		return 0;
	}
}
