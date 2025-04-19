/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model;

public class ValueRange {

	private double min = 0.0d;
	private double max = 0.0d;

	public ValueRange(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double getMin() {

		return min;
	}

	public void setMin(double min) {

		this.min = min;
	}

	public double getMax() {

		return max;
	}

	public void setMax(double max) {

		this.max = max;
	}
}
