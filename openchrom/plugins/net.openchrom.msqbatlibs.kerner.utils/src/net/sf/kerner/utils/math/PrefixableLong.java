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
package net.sf.kerner.utils.math;

public interface PrefixableLong {

	long toPicos(long units);

	long toNanos(long units);

	long toMicros(long units);

	long toMillis(long units);

	long toUnits(long units);

	long toKilos(long units);

	long toMegas(long units);

	long toGigas(long units);

	long toTeras(long units);
}
