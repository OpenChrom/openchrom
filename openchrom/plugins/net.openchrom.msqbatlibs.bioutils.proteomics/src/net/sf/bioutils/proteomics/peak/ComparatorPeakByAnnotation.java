/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peak;

import java.util.Comparator;

import net.sf.bioutils.proteomics.annotation.AnnotatableElement;
import net.sf.bioutils.proteomics.annotation.ComparatorAnnotation;

public class ComparatorPeakByAnnotation implements Comparator<Peak> {

	private final static ComparatorAnnotation COMPARATOR_ANNOTATION = new ComparatorAnnotation();

	public int compare(final Peak o1, final Peak o2) {

		if(o1 instanceof AnnotatableElement && o2 instanceof AnnotatableElement) {
			return COMPARATOR_ANNOTATION.compare((AnnotatableElement)o1, (AnnotatableElement)o2);
		}
		return 0;
	}
}
