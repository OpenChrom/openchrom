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
package net.sf.bioutils.proteomics.annotation;

import java.util.Comparator;

import net.sf.kerner.utils.collections.comparator.ComparatorNull;

public class ComparatorAnnotation extends ComparatorNull<AnnotatableElement> implements Comparator<AnnotatableElement> {

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
