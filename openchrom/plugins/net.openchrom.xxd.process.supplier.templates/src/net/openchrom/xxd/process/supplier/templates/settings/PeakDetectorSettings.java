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
package net.openchrom.xxd.process.supplier.templates.settings;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.model.settings.AbstractProcessSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.DetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorSettings extends AbstractProcessSettings implements IPeakDetectorSettingsMSD, IPeakDetectorSettingsCSD {

	public static final String DESCRIPTION = "Template Peak Detector";
	//
	@JsonProperty(value = "Detector Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + PeakDetectorListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = "^(\\d+\\.\\d+)(\\s*\\|\\s*)(\\d+\\.\\d+)(\\s*\\|\\s*)(VV|BB)(\\s*\\|\\s*)([\\d+,|\\d+-]*)(\\s*\\|\\s*)(true|false)", isMultiLine = true)
	private String detectorSettings = "";

	public void setDetectorSettings(String detectorSettings) {

		this.detectorSettings = detectorSettings;
	}

	public void setDetectorSettings(List<DetectorSetting> detectorSettings) {

		DetectorSettings settings = new DetectorSettings();
		this.detectorSettings = settings.extractSettings(detectorSettings);
	}

	public List<DetectorSetting> getDetectorSettings() {

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
}
