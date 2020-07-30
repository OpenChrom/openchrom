/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.AbstractPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PeakDetectorDirectSettings extends AbstractPeakDetectorSettingsMSD implements IPeakDetectorSettingsMSD, IPeakDetectorSettingsCSD, ITemplateSettings {

	@JsonProperty(value = "Traces", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '103 104' or empty for using TIC.")
	@StringSettingsProperty(regExp = RE_TRACES, isMultiLine = false)
	private String traces = "";
	@JsonProperty(value = "Peak Detector (VV)", defaultValue = "true")
	@JsonPropertyDescription(value = "The valley-valley peak detection is performed. Otherwise: BB.")
	private boolean detectorTypeVV = true;
	@JsonProperty(value = "Optimize Range", defaultValue = "false")
	@JsonPropertyDescription(value = "If VV as a peak detection technique has been selected, the peak is optimized.")
	private boolean optimizeRange = false;
	@JsonProperty(value = "Use Existing Peaks", defaultValue = "true")
	@JsonPropertyDescription(value = "If true, ranges will be created using the existing peak(s).")
	private boolean useExistingPeaks = true;
	@JsonProperty(value = "Use Selected Range", defaultValue = "true")
	@JsonPropertyDescription(value = "If true, a range will be created using the start and stop retention time of the selection.")
	private boolean useSelectedRange = true;

	public String getTraces() {

		return traces;
	}

	public void setTraces(String traces) {

		this.traces = traces;
	}

	public boolean isDetectorTypeVV() {

		return detectorTypeVV;
	}

	public void setDetectorTypeVV(boolean detectorTypeVV) {

		this.detectorTypeVV = detectorTypeVV;
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}

	public boolean isUseExistingPeaks() {

		return useExistingPeaks;
	}

	public void setUseExistingPeaks(boolean useExistingPeaks) {

		this.useExistingPeaks = useExistingPeaks;
	}

	public boolean isUseSelectedRange() {

		return useSelectedRange;
	}

	public void setUseSelectedRange(boolean useSelectedRange) {

		this.useSelectedRange = useSelectedRange;
	}
}
