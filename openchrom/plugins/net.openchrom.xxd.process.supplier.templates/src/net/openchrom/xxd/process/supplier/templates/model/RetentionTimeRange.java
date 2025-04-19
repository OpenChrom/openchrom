/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - implement IRetentionTimeRange interface
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.support.IRetentionTimeRange;

public class RetentionTimeRange implements IRetentionTimeRange {

	private int startRetentionTime = 0;
	private int stopRetentionTime = 0;

	public RetentionTimeRange(int startRetentionTime, int stopRetentionTime) {
		this.startRetentionTime = startRetentionTime;
		this.stopRetentionTime = stopRetentionTime;
	}

	@Override
	public int getStartRetentionTime() {

		return startRetentionTime;
	}

	@Override
	public int getStopRetentionTime() {

		return stopRetentionTime;
	}
}
