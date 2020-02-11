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
 * Christoph Läubrich - adjust names of getter/setter, RE is no longer used
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.AbstractPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
<<<<<<< HEAD
=======
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
>>>>>>> upstream/wavelet-peak-detector
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorSettings extends AbstractPeakDetectorSettingsMSD implements IPeakDetectorSettingsMSD, IPeakDetectorSettingsCSD, ITemplateSettings {

	public static final String DESCRIPTION = "Template Peak Detector";
	/*
	 * 10.52 | 10.63 | VV | 103, 104, 108-110 | true | Styrene
	 */
	@JsonProperty(value = "Detector Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + PeakDetectorListUtil.EXAMPLE_SINGLE + "'")
	private String detectorSettings = "";
	@JsonProperty(value = "Use Comments As Names", defaultValue = "false")
	private boolean useCommentAsNames;

	public void setDetectorSettings(String detectorSettings) {

		this.detectorSettings = detectorSettings;
	}

	@JsonIgnore
	public void setDetectorSettings(List<DetectorSetting> detectorSettings) {

		DetectorSettings settings = new DetectorSettings();
		this.detectorSettings = settings.extractSettings(detectorSettings);
	}

	public String getDetectorSettings() {

		return detectorSettings;
	}

	@JsonIgnore
	public List<DetectorSetting> getDetectorSettingsList() {

		PeakDetectorListUtil util = new PeakDetectorListUtil();
		PeakDetectorValidator validator = new PeakDetectorValidator();
		List<DetectorSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(detectorSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				settings.add(validator.getSetting());
			}
		}
		//
		return settings;
	}

	public boolean isUseCommentAsNames() {

		return useCommentAsNames;
	}

	public void setUseCommentAsNames(boolean useCommentAsNames) {

		this.useCommentAsNames = useCommentAsNames;
	}
}
