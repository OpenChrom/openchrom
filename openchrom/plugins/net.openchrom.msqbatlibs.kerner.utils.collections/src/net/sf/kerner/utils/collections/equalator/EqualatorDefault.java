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
package net.sf.kerner.utils.collections.equalator;

import net.sf.kerner.utils.equal.EqualatorAbstract;

public class EqualatorDefault<T> extends EqualatorAbstract<T> {

	public boolean areEqual(final T o1, final Object o2) {

		return o1.equals(o2);
	}
}
