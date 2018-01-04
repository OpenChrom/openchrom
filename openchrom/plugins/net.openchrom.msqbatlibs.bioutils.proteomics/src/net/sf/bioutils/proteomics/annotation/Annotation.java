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
package net.sf.bioutils.proteomics.annotation;

import java.util.Map;

/**
 *
 * A collection of additional meta-informations which are assigned to an {@link AnnotatableElement}.
 *
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 * @param <K>
 *            the type of annotation keys/ identifiers maintained by this {@code Annotation}
 * @param <V>
 *            the type of annotation values maintained by this {@code Annotation}
 *
 * @see AnnotatableElement
 * @see AnnotationSerializable
 * @see Map
 */
public interface Annotation<K, V> extends Map<K, V> {
}
