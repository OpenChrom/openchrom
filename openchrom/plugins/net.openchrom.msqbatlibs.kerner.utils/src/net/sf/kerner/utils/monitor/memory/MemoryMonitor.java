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
package net.sf.kerner.utils.monitor.memory;

import net.sf.kerner.utils.math.LongUnit;

public interface MemoryMonitor {

	static LongUnit DEFAULT_UNIT = LongUnit.UNIT;

	LongUnit getUnit();

	long getCurrentUsage();

	long getCurrentUsage(LongUnit unit);

	long getMaxUsage();

	long getMaxUsage(LongUnit unit);

	long getAverageUsage();

	long getAverageUsage(LongUnit unit);

	void start();

	long stop();
}
