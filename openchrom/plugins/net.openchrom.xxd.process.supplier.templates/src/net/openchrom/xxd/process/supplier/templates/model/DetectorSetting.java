/*******************************************************************************
 * Copyright (c) 2018, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.PeakType;

public class DetectorSetting extends AbstractSetting {

<<<<<<< HEAD
	private PeakType detectorType = PeakType.VV; // VV => include background: true
=======
	public static final String DETECTOR_TYPE_BB = "BB";
	public static final String DETECTOR_TYPE_VV = "VV";
	//
	private String detectorType = DETECTOR_TYPE_VV; // VV => include background: true
>>>>>>> upstream/wavelet-peak-detector
	private String traces = "";
	private boolean optimizeRange = false;
	private String referenceIdentifier = ""; // Used for relative retention time
	private String comment = "";

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

	public PeakType getDetectorType() {

		return detectorType;
	}

	public void setDetectorType(PeakType detectorType) {

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

<<<<<<< HEAD
=======
	public boolean isIncludeBackground() {

		return (DETECTOR_TYPE_VV.equals(detectorType)) ? true : false;
	}

>>>>>>> upstream/wavelet-peak-detector
	@Override
	public String toString() {

		return "DetectorSetting [startRetentionTime=" + getStartRetentionTime() + ", stopRetentionTime=" + getStopRetentionTime() + ", detectorType=" + detectorType + ", traces=" + traces + ", optimizeRange=" + optimizeRange + ", referenceIdentifier=" + referenceIdentifier + "]";
	}

	public String getComment() {

		return comment;
	}

	public void setComment(String comment) {

		this.comment = comment;
	}
}
