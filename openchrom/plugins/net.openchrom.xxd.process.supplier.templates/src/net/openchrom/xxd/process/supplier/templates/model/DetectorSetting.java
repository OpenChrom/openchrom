/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import java.util.Objects;

import org.eclipse.chemclipse.model.core.PeakType;

public class DetectorSetting extends AbstractSetting {

	private PeakType peakType = PeakType.VV; // VV => include background: true
	private String traces = "";
	private boolean optimizeRange = false;
	private String referenceIdentifier = ""; // Used for relative retention time
	private String name = ""; // Used to set a simple identification ... but rather use the peak identifier
	private String classifier = "";
	private boolean autoAdjustScanRange = false;
	private boolean autoAdjustDetectorRange = false;

	public void copyFrom(DetectorSetting setting) {

		if(setting != null) {
			setPositionDirective(setting.getPositionDirective());
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPeakType(setting.getPeakType());
			setTraces(setting.getTraces());
			setOptimizeRange(setting.isOptimizeRange());
			setReferenceIdentifier(setting.getReferenceIdentifier());
			setName(setting.getName());
			setClassifier(setting.getClassifier());
			setAutoAdjustScanRange(setting.isAutoAdjustScanRange());
			setAutoAdjustDetectorRange(setting.isAutoAdjustDetectorRange());
		}
	}

	public PeakType getPeakType() {

		return peakType;
	}

	public void setPeakType(PeakType detectorType) {

		this.peakType = detectorType;
	}

	public boolean isIncludeBackground() {

		return (PeakType.VV.equals(peakType));
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

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getClassifier() {

		return classifier;
	}

	public void setClassifier(String classifier) {

		this.classifier = classifier;
	}

	public boolean isAutoAdjustScanRange() {

		return autoAdjustScanRange;
	}

	public void setAutoAdjustScanRange(boolean autoAdjustScanRange) {

		this.autoAdjustScanRange = autoAdjustScanRange;
	}

	public boolean isAutoAdjustDetectorRange() {

		return autoAdjustDetectorRange;
	}

	public void setAutoAdjustDetectorRange(boolean autoAdjustDetectorRange) {

		this.autoAdjustDetectorRange = autoAdjustDetectorRange;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name, traces);
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
		DetectorSetting other = (DetectorSetting)obj;
		return Objects.equals(name, other.name) && Objects.equals(traces, other.traces);
	}

	@Override
	public String toString() {

		return "DetectorSetting [peakType=" + peakType + ", traces=" + traces + ", optimizeRange=" + optimizeRange + ", referenceIdentifier=" + referenceIdentifier + ", name=" + name + ", classifier=" + classifier + ", autoAdjustScanRange=" + autoAdjustScanRange + ", autoAdjustDetectorRange=" + autoAdjustDetectorRange + "]";
	}
}