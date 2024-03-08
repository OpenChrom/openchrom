/*******************************************************************************
 * Copyright (c) 2020, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

import java.io.File;

import org.eclipse.chemclipse.support.settings.FileSettingProperty;
import org.eclipse.chemclipse.support.settings.FileSettingProperty.DialogType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class SettingsELU extends AbstractProcessSettings {

	@JsonProperty(value = "Result File", defaultValue = "")
	@JsonPropertyDescription("Select the result file.")
	@FileSettingProperty(dialogType = DialogType.OPEN_DIALOG, extensionNames = {"AMDIS (*.elu)"}, validExtensions = {"*.ELU;*.elu"}, onlyDirectory = false)
	private File resultFile;

	public File getResultFile() {

		return resultFile;
	}

	public void setResultFile(File resultFile) {

		this.resultFile = resultFile;
	}
}
