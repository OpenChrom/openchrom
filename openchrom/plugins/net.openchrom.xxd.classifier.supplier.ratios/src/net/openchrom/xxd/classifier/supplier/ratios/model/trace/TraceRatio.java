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
package net.openchrom.xxd.classifier.supplier.ratios.model.trace;

import net.openchrom.xxd.classifier.supplier.ratios.model.AbstractPeakRatio;

public class TraceRatio extends AbstractPeakRatio {

	public static final String TEST_CASE_SEPARATOR = ":";
	//
	private String testCase = "";
	private double expectedRatio = 0.0d;
	private double ratio = 0.0d; // Calculated (transient)

	public void copyFrom(TraceRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setTestCase(setting.getTestCase());
			setExpectedRatio(setting.getExpectedRatio());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public String getTestCase() {

		return testCase;
	}

	public void setTestCase(String testCase) {

		this.testCase = testCase;
	}

	public double getExpectedRatio() {

		return expectedRatio;
	}

	public void setExpectedRatio(double expectedRatio) {

		this.expectedRatio = expectedRatio;
	}

	public double getRatio() {

		return ratio;
	}

	public void setRatio(double ratio) {

		this.ratio = ratio;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((testCase == null) ? 0 : testCase.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		TraceRatio other = (TraceRatio)obj;
		if(testCase == null) {
			if(other.testCase != null)
				return false;
		} else if(!testCase.equals(other.testCase))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "TraceRatio [testCase=" + testCase + ", expectedRatio=" + expectedRatio + ", ratio=" + ratio + ", AbstractRatio=" + super.toString() + "]";
	}
}