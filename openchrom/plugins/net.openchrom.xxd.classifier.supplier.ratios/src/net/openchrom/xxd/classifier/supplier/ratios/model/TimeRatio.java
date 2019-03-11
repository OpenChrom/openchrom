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
package net.openchrom.xxd.classifier.supplier.ratios.model;

public class TimeRatio extends AbstractPeakRatio {

	private double expectedRetentionTime = 0;
	private double retentionTime = 0; // Calculated (transient)

	public void copyFrom(TimeRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setExpectedRetentionTime(setting.getExpectedRetentionTime());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public double getExpectedRetentionTime() {

		return expectedRetentionTime;
	}

	public void setExpectedRetentionTime(double expectedRetentionTime) {

		this.expectedRetentionTime = expectedRetentionTime;
	}

	public double getRetentionTime() {

		return retentionTime;
	}

	public void setRetentionTime(double retentionTime) {

		this.retentionTime = retentionTime;
	}

	@Override
	public String toString() {

		return "TimeRatio [expectedRetentionTime=" + expectedRetentionTime + ", retentionTime=" + retentionTime + ", AbstractRatio=" + super.toString() + "]";
	}
}
