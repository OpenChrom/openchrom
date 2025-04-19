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
