/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

public class DetectorSetting {

	public static final String DETECTOR_TYPE_BB = "BB";
	public static final String DETECTOR_TYPE_VV = "VV";
	//
	private double startRetentionTime = 0.0d; // Minutes
	private double stopRetentionTime = 0.0d; // Minutes
	private String detectorType = "";
	private String traces = "";

	public void copyFrom(DetectorSetting setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setDetectorType(setting.getDetectorType());
			setTraces(setting.getTraces());
		}
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public void setStartRetentionTime(double startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public void setStopRetentionTime(double stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	public String getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(String detectorType) {

		this.detectorType = detectorType;
	}

	public String getTraces() {

		return traces;
	}

	public void setTraces(String traces) {

		this.traces = traces;
	}

	public boolean isIncludeBackground() {

		if(DETECTOR_TYPE_VV.equals(detectorType)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(startRetentionTime);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(stopRetentionTime);
		result = prime * result + (int)(temp ^ (temp >>> 32));
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
		DetectorSetting other = (DetectorSetting)obj;
		if(Double.doubleToLongBits(startRetentionTime) != Double.doubleToLongBits(other.startRetentionTime))
			return false;
		if(Double.doubleToLongBits(stopRetentionTime) != Double.doubleToLongBits(other.stopRetentionTime))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "DetectorSetting [startRetentionTime=" + startRetentionTime + ", stopRetentionTime=" + stopRetentionTime + ", detectorType=" + detectorType + ", traces=" + traces + "]";
	}
}
