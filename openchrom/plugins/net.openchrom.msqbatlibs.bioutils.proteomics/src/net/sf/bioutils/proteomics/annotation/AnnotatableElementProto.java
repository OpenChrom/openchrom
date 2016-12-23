/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Prototype implementation for {@link AnnotatableElement}.
 *
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class AnnotatableElementProto implements AnnotatableElement {

	private Collection<AnnotationSerializable> annotation = new ArrayList<AnnotationSerializable>(0);

	public Collection<AnnotationSerializable> getAnnotation() {

		return annotation;
	}

	public void setAnnotation(final AnnotationSerializable annotation) {

		this.setAnnotation(Arrays.asList(annotation));
	}

	public void setAnnotation(final Collection<AnnotationSerializable> annotation) {

		this.annotation = annotation;
	}
}
