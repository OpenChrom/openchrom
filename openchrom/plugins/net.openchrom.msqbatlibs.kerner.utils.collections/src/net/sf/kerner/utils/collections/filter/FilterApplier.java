/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.filter;

import java.util.List;

import net.sf.kerner.utils.collections.applier.Applier;

public interface FilterApplier<E> extends Filter<E>, Applier {

	FilterApplier<E> addFilter(Filter<E> filter);

	List<Filter<E>> getFilters();

	boolean isEmpty();
}
