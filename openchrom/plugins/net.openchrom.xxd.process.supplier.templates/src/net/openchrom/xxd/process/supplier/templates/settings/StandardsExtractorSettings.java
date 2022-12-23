/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.xxd.quantitation.settings.AbstractPeakQuantifierSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;

public class StandardsExtractorSettings extends AbstractPeakQuantifierSettings {

	/*
	 * The ISTD is stored in the miscInfo section of the header file e.g. as follows:
	 * IS:7409:102.62637
	 * ---
	 * IS <- entry marker
	 * 7409 <- reference identifier
	 * 102.62637 <- concentration
	 * ---
	 * Due to limits in header size, the unit is not stored.
	 */
	@JsonProperty(value = "Concentration Unit", defaultValue = PreferenceSupplier.DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT)
	@JsonPropertyDescription(value = "Example: " + PreferenceSupplier.DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT)
	private String concentrationUnit = PreferenceSupplier.DEF_STANDARDS_EXTRACTOR_CONCENTRATION_UNIT;

	public String getConcentrationUnit() {

		return concentrationUnit;
	}

	public void setConcentrationUnit(String concentrationUnit) {

		this.concentrationUnit = concentrationUnit;
	}
}
