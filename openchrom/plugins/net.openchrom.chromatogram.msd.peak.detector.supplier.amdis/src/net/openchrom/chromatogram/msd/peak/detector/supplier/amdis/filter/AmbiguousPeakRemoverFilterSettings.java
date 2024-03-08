/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.filter;

import org.eclipse.chemclipse.chromatogram.msd.comparison.massspectrum.IMassSpectrumComparator;
import org.eclipse.chemclipse.chromatogram.msd.comparison.massspectrum.MassSpectrumComparator;
import org.eclipse.chemclipse.chromatogram.msd.comparison.massspectrum.MassSpectrumComparatorDynamicSettingProperty;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.support.settings.ComboSettingsProperty;
import org.eclipse.chemclipse.support.settings.DoubleSettingsProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class AmbiguousPeakRemoverFilterSettings {

	@JsonIgnore
	private final IChromatogramMSD chromatogram;
	@JsonProperty(value = "Maximum RT (minutes)", defaultValue = "0.05")
	@JsonPropertyDescription(value = "The maximum distance between two consecutive peaks to select them for the filtering")
	@DoubleSettingsProperty()
	private double rtmaxdistance = 0.05d;
	@JsonProperty(value = "Minimum matchfactor (0...1)", defaultValue = "0.95")
	@JsonPropertyDescription(value = "If the matchfactor is lower than this value we assume that two peaks are not equal")
	@DoubleSettingsProperty()
	private double minmatchfactor = 0.95d;
	@JsonProperty(value = "Selection Method", defaultValue = "SNR")
	@JsonPropertyDescription(value = "Method to use for selecting the best peak in the group")
	private SelectionMethod method = SelectionMethod.SNR;
	@JsonProperty(value = "m/z comparator", defaultValue = "org.eclipse.chemclipse.chromatogram.msd.comparison.supplier.distance.cosine")
	@JsonPropertyDescription(value = "Method to use for comparing mass spectrums")
	@ComboSettingsProperty(MassSpectrumComparatorDynamicSettingProperty.class)
	private String comparatorID = "org.eclipse.chemclipse.chromatogram.msd.comparison.supplier.distance.cosine";

	public AmbiguousPeakRemoverFilterSettings() {

		this(null);
	}

	public AmbiguousPeakRemoverFilterSettings(IChromatogramMSD chromatogram) {

		this.chromatogram = chromatogram;
		if(chromatogram == null) {
			setMethod(SelectionMethod.AREA);
		} else {
			setMethod(SelectionMethod.SNR);
		}
	}

	public enum SelectionMethod {
		SNR, AREA;
	}

	public double getRtMaxdistance() {

		return rtmaxdistance;
	}

	public void setRtMaxdistance(double rtmaxdistance) {

		this.rtmaxdistance = rtmaxdistance;
	}

	public double getMinmatchfactor() {

		return minmatchfactor;
	}

	public void setMinmatchfactor(double minmatchfactor) {

		this.minmatchfactor = minmatchfactor;
	}

	public String getComparatorID() {

		return comparatorID;
	}

	public void setComparatorID(String comparatorID) {

		this.comparatorID = comparatorID;
	}

	public SelectionMethod getMethod() {

		return method;
	}

	public void setMethod(SelectionMethod method) {

		this.method = method;
	}

	@JsonIgnore
	public IMassSpectrumComparator getMassSpectrumComparator() {

		return MassSpectrumComparator.getMassSpectrumComparator(getComparatorID());
	}
}
