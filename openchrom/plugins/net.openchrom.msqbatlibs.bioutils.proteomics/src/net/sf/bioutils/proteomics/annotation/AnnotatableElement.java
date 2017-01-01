/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

import java.util.Collection;

/**
 *
 * <p>
 * An object to which meta-information can be attached.
 * </p>
 * This information is stored in an {@link Annotation} object.
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * <p>
 * last reviewed: 2014-05-16
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public interface AnnotatableElement {

	/**
	 *
	 * Returns annotations which are assigned to this {@code AnnotatableElement}
	 *
	 * @return annotations which are assigned to this {@code AnnotatableElement}
	 */
	Collection<AnnotationSerializable> getAnnotation();

	void setAnnotation(Collection<AnnotationSerializable> annotation);
}
