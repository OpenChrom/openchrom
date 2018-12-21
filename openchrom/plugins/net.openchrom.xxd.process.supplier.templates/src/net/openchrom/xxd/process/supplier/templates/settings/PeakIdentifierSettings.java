/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.csd.identifier.settings.IPeakIdentifierSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.IPeakIdentifierSettingsMSD;
import org.eclipse.chemclipse.model.identifier.AbstractIdentifierSettings;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierValidator;

public class PeakIdentifierSettings extends AbstractIdentifierSettings implements IPeakIdentifierSettingsMSD, IPeakIdentifierSettingsCSD {

	public static final String DESCRIPTION = "Template Peak Identifier";
	//
	@JsonProperty(value = "Identifier Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + PeakIdentifierListUtil.EXAMPLE_MULTIPLE + "'")
	private String identifierSettings = "";

	public void setIdentifierSettings(String identifierSettings) {

		this.identifierSettings = identifierSettings;
	}

	public List<IdentifierSetting> getIdentifierSettings() {

		PeakIdentifierListUtil util = new PeakIdentifierListUtil();
		PeakIdentifierValidator validator = new PeakIdentifierValidator();
		List<IdentifierSetting> settings = new ArrayList<>();
		//
		List<String> items = util.getList(identifierSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				settings.add(validator.getSetting());
			}
		}
		//
		return settings;
	}

	@Override
	public String getMassSpectrumComparatorId() {

		return "not needed here";
	}

	@Override
	public void setMassSpectrumComparatorId(String massSpectrumComparatorId) {

	}

	@Override
	public IMarkedIons getExcludedIons() {

		return null;
	}
}
