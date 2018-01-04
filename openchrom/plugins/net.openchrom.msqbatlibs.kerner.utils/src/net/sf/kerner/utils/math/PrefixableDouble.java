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
package net.sf.kerner.utils.math;

public interface PrefixableDouble {

	double toPicos(double units);

	double toNanos(double units);

	double toMicros(double units);

	double toMillis(double units);

	double toUnits(double units);

	double toKilos(double units);

	double toMegas(double units);

	double toGigas(double units);

	double toTeras(double units);
}
