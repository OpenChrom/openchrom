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
package net.sf.kerner.utils.collections.equalator;

import java.util.List;

import net.sf.kerner.utils.equal.Equalator;

public interface EqualatorApplier<T> extends Equalator<T> {

	void addEqualator(Equalator<T> equalator);

	List<Equalator<T>> getEqualators();
}
