/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
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

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.model.AssignerReferences;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerValidator;

public class StandardsReferencerSettings extends AbstractPeakQuantifierSettings implements ITemplateSettings {

	public static final String DESCRIPTION = "Template Standards Referencer";
	/*
	 * 10.52 | 10.63 | Toluene | Styrene
	 */
	@JsonProperty(value = "Referencer Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + StandardsReferencerListUtil.EXAMPLE_SINGLE + "'")
	@StringSettingsProperty(regExp = RE_START + //
			RE_NUMBER + // Start RT
			RE_SEPARATOR + //
			RE_NUMBER + // Stop RT
			RE_SEPARATOR + //
			RE_TEXT + // ISTD
			RE_SEPARATOR + //
			RE_TEXT, // Identifier
			isMultiLine = true)
	private String referencerSettings = "";

	public void setReferencerSettings(String referencerSettings) {

		this.referencerSettings = referencerSettings;
	}

	public String getReferencerSettings() {

		return referencerSettings;
	}

	@JsonIgnore
	public void setReferencerSettings(List<AssignerReference> referencerSettings) {

		AssignerReferences settings = new AssignerReferences();
		this.referencerSettings = settings.extractSettings(referencerSettings);
	}

	@JsonIgnore
	public List<AssignerReference> getReferencerSettingsList() {

		StandardsReferencerListUtil util = new StandardsReferencerListUtil();
		StandardsReferencerValidator validator = new StandardsReferencerValidator();
		List<AssignerReference> settings = new ArrayList<>();
		//
		List<String> items = util.getList(referencerSettings);
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
