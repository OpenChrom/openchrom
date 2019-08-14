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
package net.openchrom.xxd.classifier.supplier.ratios.model.time;

import org.eclipse.chemclipse.model.core.IChromatogram;

import net.openchrom.xxd.classifier.supplier.ratios.model.AbstractPeakRatio;

public class TimeRatio extends AbstractPeakRatio {

	private int expectedRetentionTime = 0; // Milliseconds

	public void copyFrom(TimeRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setExpectedRetentionTime(setting.getExpectedRetentionTime());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public int getExpectedRetentionTime() {

		return expectedRetentionTime;
	}

	public double getExpectedRetentionTimeMinutes() {

		return expectedRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR;
	}

	public void setExpectedRetentionTime(int expectedRetentionTime) {

		this.expectedRetentionTime = expectedRetentionTime;
	}

	public void setExpectedRetentionTimeMinutes(double expectedRetentionTimeMinutes) {

		this.expectedRetentionTime = (int)(expectedRetentionTimeMinutes * IChromatogram.MINUTE_CORRELATION_FACTOR);
	}

	@Override
	public String toString() {

		return "TimeRatio [expectedRetentionTime=" + expectedRetentionTime + ", AbstractRatio=" + super.toString() + "]";
	}
}
