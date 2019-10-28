/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

public class DetectorSetting extends AbstractSetting {

	public static final String DETECTOR_TYPE_BB = "BB";
	public static final String DETECTOR_TYPE_VV = "VV";
	//
	private String detectorType = DETECTOR_TYPE_VV; // VV => include background: true
	private String traces = "";
	private boolean optimizeRange = false;
	private String referenceIdentifier = ""; // Used for relative retention time

	public void copyFrom(DetectorSetting setting) {

		if(setting != null) {
			setStartRetentionTime(setting.getStartRetentionTime());
			setStopRetentionTime(setting.getStopRetentionTime());
			setDetectorType(setting.getDetectorType());
			setTraces(setting.getTraces());
			setOptimizeRange(setting.isOptimizeRange());
			setReferenceIdentifier(setting.getReferenceIdentifier());
		}
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

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}

	public String getReferenceIdentifier() {

		return referenceIdentifier;
	}

	public void setReferenceIdentifier(String referenceIdentifier) {

		this.referenceIdentifier = referenceIdentifier;
	}

	public boolean isIncludeBackground() {

		return (DETECTOR_TYPE_VV.equals(detectorType)) ? true : false;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + getStartRetentionTime();
		result = prime * result + getStopRetentionTime();
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
		if(getStartRetentionTime() != other.getStartRetentionTime())
			return false;
		if(getStopRetentionTime() != other.getStopRetentionTime())
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "DetectorSetting [startRetentionTime=" + getStartRetentionTime() + ", stopRetentionTime=" + getStopRetentionTime() + ", detectorType=" + detectorType + ", traces=" + traces + ", optimizeRange=" + optimizeRange + ", referenceIdentifier=" + referenceIdentifier + "]";
	}
}
