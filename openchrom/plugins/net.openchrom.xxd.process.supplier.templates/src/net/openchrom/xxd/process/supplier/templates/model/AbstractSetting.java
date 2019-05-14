/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.IChromatogram;

public abstract class AbstractSetting {

	/*
	 * RT in Milliseconds
	 */
	private int startRetentionTime = 0;
	private int stopRetentionTime = 0;

	public int getStartRetentionTime() {

		return startRetentionTime;
	}

	public void setStartRetentionTime(int startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	public double getStartRetentionTimeMinutes() {

		return startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR;
	}

	public void setStartRetentionTimeMinutes(double startRetentionTimeMinutes) {

		this.startRetentionTime = (int)(startRetentionTimeMinutes * IChromatogram.MINUTE_CORRELATION_FACTOR);
	}

	public int getStopRetentionTime() {

		return stopRetentionTime;
	}

	public void setStopRetentionTime(int stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	public double getStopRetentionTimeMinutes() {

		return stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR;
	}

	public void setStopRetentionTimeMinutes(double stopRetentionTimeMinutes) {

		this.stopRetentionTime = (int)(stopRetentionTimeMinutes * IChromatogram.MINUTE_CORRELATION_FACTOR);
	}
}
