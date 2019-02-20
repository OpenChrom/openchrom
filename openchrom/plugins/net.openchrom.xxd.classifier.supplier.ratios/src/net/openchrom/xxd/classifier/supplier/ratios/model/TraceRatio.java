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

import org.eclipse.chemclipse.msd.model.core.IPeakMSD;

public class TraceRatio {

	private IPeakMSD peakMSD = null; // optional
	//
	private String name = "";
	private String testCase = "";
	private double expectedRatio = 0.0d;
	private double actualRatio = 0.0d; // transient
	private double deviationWarn = 0.0d;
	private double deviationError = 0.0d;

	public void copyFrom(TraceRatio setting) {

		if(setting != null) {
			setName(setting.getName());
			setTestCase(setting.getTestCase());
			setExpectedRatio(setting.getExpectedRatio());
			setDeviationWarn(setting.getDeviationWarn());
			setDeviationError(setting.getDeviationError());
		}
	}

	public IPeakMSD getPeakMSD() {

		return peakMSD;
	}

	public void setPeakMSD(IPeakMSD peakMSD) {

		this.peakMSD = peakMSD;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
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

	public double getActualRatio() {

		return actualRatio;
	}

	public void setActualRatio(double actualRatio) {

		this.actualRatio = actualRatio;
	}

	public double getDeviationWarn() {

		return deviationWarn;
	}

	public void setDeviationWarn(double deviationWarn) {

		this.deviationWarn = deviationWarn;
	}

	public double getDeviationError() {

		return deviationError;
	}

	public void setDeviationError(double deviationError) {

		this.deviationError = deviationError;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((testCase == null) ? 0 : testCase.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		TraceRatio other = (TraceRatio)obj;
		if(testCase == null) {
			if(other.testCase != null)
				return false;
		} else if(!testCase.equals(other.testCase))
			return false;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "TraceRatio [peakMSD=" + peakMSD + ", name=" + name + ", testCase=" + testCase + ", expectedRatio=" + expectedRatio + ", actualRatio=" + actualRatio + ", deviationWarn=" + deviationWarn + ", deviationError=" + deviationError + "]";
	}
}
