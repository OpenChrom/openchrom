/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.AbstractPeakQuantifierSettings;
import org.eclipse.chemclipse.support.settings.StringSettingsProperty;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.AssignerStandard;
import net.openchrom.xxd.process.supplier.templates.model.AssignerStandards;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerValidator;

public class StandardsAssignerSettings extends AbstractPeakQuantifierSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Standards Assigner";
	/*
	 * 10.52 | 10.63 | Styrene | 10.5 | mg/L | 1.0
	 */
	@JsonProperty(value = "Assigner Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + StandardsAssignerListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // Name
			RE_SEPARATOR + //
			RE_NUMBER + // Concentration
			RE_SEPARATOR + //
			RE_TEXT + // Unit
			RE_SEPARATOR + //
			RE_NUMBER, // Response Factor
			isMultiLine = true)
	private String assignerSettings = "";

	public void setAssignerSettings(String assignerSettings) {

		this.assignerSettings = assignerSettings;
	}

	@JsonIgnore
	public void setAssignerSettings(List<AssignerStandard> assignerSettings) {

		AssignerStandards settings = new AssignerStandards();
		this.assignerSettings = settings.extractSettings(assignerSettings);
	}

	@JsonIgnore
	public List<AssignerStandard> getAssignerSettings() {

		StandardsAssignerListUtil util = new StandardsAssignerListUtil();
		StandardsAssignerValidator validator = new StandardsAssignerValidator();
		List<AssignerStandard> settings = new ArrayList<>();
		//
		List<String> items = util.getList(assignerSettings);
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
