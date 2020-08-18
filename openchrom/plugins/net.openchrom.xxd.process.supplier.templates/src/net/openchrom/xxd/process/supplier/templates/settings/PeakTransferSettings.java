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
import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PeakTransferSettings extends AbstractPeakDetectorSettingsMSD implements IPeakDetectorSettingsMSD, IPeakDetectorSettingsCSD {

	public static final String DETECTOR_DESCRIPTION = "Gaussian Model";
	public static final String IDENTIFIER_DESCRIPTION = "Peak Transfer [Shape]";
	//
	@JsonProperty(value = "Transfer Identified Peaks Only", defaultValue = "true")
	@JsonPropertyDescription(value = "If this value is true, only identified peaks will be transfered.")
	private boolean useIdentifiedPeaksOnly = true;
	//
	@JsonProperty(value = "Transfer Best Target Only", defaultValue = "false")
	@JsonPropertyDescription(value = "If this value is true, only the best target will be transfered.")
	private boolean useBestTargetOnly = false;
	//
	@JsonProperty(value = "Delta Retention Time Left [ms]", defaultValue = "0")
	@JsonPropertyDescription(value = "This is the left delta retention time in milliseconds.")
	private int deltaRetentionTimeLeft = 0;
	//
	@JsonProperty(value = "Delta Retention Time Right [ms]", defaultValue = "0")
	@JsonPropertyDescription(value = "This is the right delta retention time in milliseconds.")
	private int deltaRetentionTimeRight = 0;
	//
	@JsonProperty(value = "Offset Retention Time (Peak Maximum) [ms]", defaultValue = "500")
	@JsonPropertyDescription(value = "This offset retention time in milliseconds is used to find the max signal in the reference.")
	private int offsetRetentionTimePeakMaximum = 500;
	//
	@JsonProperty(value = "Adjust Peak Height", defaultValue = "true")
	@JsonPropertyDescription(value = "This flag enables to adjust the peak height by intensity comparison.")
	private boolean adjustPeakHeight = true;
	//
	@JsonProperty(value = "Create Model Peak (CSD)", defaultValue = "true")
	@JsonPropertyDescription(value = "Create a model peak if the peak is part of a peak group and the sink is a CSD chromatogram.")
	private boolean createModelPeak = true;
	//
	@JsonProperty(value = "Peak Overlap Coverage [%]", defaultValue = "12.5")
	@JsonPropertyDescription(value = "If a peak overlaps with at least the given coverage, it is grouped.")
	@DoubleSettingsProperty(minValue = 1, maxValue = 100)
	private double peakOverlapCoverage = 12.5d;
	//
	@JsonProperty(value = "Optimize Range (VV)", defaultValue = "true")
	@JsonPropertyDescription(value = "Optimize the peak model range if the source peak type is VV.")
	private boolean optimizeRange = true;
	//
	@JsonProperty(value = "Check Purity (MSD)", defaultValue = "true")
	@JsonPropertyDescription(value = "If the purity < 1.0 then the given number of traces is used to detect the peak in the reference chromatogram.")
	private boolean checkPurity = true;
	//
	@JsonProperty(value = "Number Traces (MSD)", defaultValue = "15")
	@JsonPropertyDescription(value = "If a peak (MSD) contains less/equals than the given number of traces, then a SIM instead of a TIC detection will be forced.")
	private int numberTraces = 15;

	public boolean isUseIdentifiedPeaksOnly() {

		return useIdentifiedPeaksOnly;
	}

	public void setUseIdentifiedPeaksOnly(boolean useIdentifiedPeaksOnly) {

		this.useIdentifiedPeaksOnly = useIdentifiedPeaksOnly;
	}

	public boolean isUseBestTargetOnly() {

		return useBestTargetOnly;
	}

	public void setUseBestTargetOnly(boolean useBestTargetOnly) {

		this.useBestTargetOnly = useBestTargetOnly;
	}

	public int getDeltaRetentionTimeLeft() {

		return deltaRetentionTimeLeft;
	}

	public void setDeltaRetentionTimeLeft(int deltaRetentionTimeLeft) {

		this.deltaRetentionTimeLeft = deltaRetentionTimeLeft;
	}

	public int getDeltaRetentionTimeRight() {

		return deltaRetentionTimeRight;
	}

	public void setDeltaRetentionTimeRight(int deltaRetentionTimeRight) {

		this.deltaRetentionTimeRight = deltaRetentionTimeRight;
	}

	public int getOffsetRetentionTimePeakMaximum() {

		return offsetRetentionTimePeakMaximum;
	}

	public void setOffsetRetentionTimePeakMaximum(int offsetRetentionTimePeakMaximum) {

		this.offsetRetentionTimePeakMaximum = offsetRetentionTimePeakMaximum;
	}

	public boolean isAdjustPeakHeight() {

		return adjustPeakHeight;
	}

	public void setAdjustPeakHeight(boolean adjustPeakHeight) {

		this.adjustPeakHeight = adjustPeakHeight;
	}

	public boolean isCreateModelPeak() {

		return createModelPeak;
	}

	public void setCreateModelPeak(boolean createModelPeak) {

		this.createModelPeak = createModelPeak;
	}

	public double getPeakOverlapCoverage() {

		return peakOverlapCoverage;
	}

	public void setPeakOverlapCoverage(double peakOverlapCoverage) {

		this.peakOverlapCoverage = peakOverlapCoverage;
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}

	public boolean isCheckPurity() {

		return checkPurity;
	}

	public void setCheckPurity(boolean checkPurity) {

		this.checkPurity = checkPurity;
	}

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}
}
