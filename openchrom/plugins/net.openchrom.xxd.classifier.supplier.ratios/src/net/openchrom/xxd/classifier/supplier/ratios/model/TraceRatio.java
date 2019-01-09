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

public class TraceRatio implements Comparable<TraceRatio> {

	private IPeakMSD peakMSD = null;
	private String name = "";
	private String test = "";
	private double expected = 0.0d;
	private double actual = 0.0d;

	public TraceRatio(String name, String test, double expected) {
		this.name = name;
		this.test = test;
		this.expected = expected;
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

	public String getTest() {

		return test;
	}

	public void setTest(String test) {

		this.test = test;
	}

	public double getExpected() {

		return expected;
	}

	public void setExpected(double expected) {

		this.expected = expected;
	}

	public double getActual() {

		return actual;
	}

	public void setActual(double actual) {

		this.actual = actual;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((test == null) ? 0 : test.hashCode());
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
		if(test == null) {
			if(other.test != null)
				return false;
		} else if(!test.equals(other.test))
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

		return "TraceRatio [name=" + name + ", test=" + test + ", expected=" + expected + ", actual=" + actual + "]";
	}

	@Override
	public int compareTo(TraceRatio traceRatio) {

		int result = 0;
		if(traceRatio != null) {
			result = name.compareTo(traceRatio.getName());
		}
		return result;
	}
}
