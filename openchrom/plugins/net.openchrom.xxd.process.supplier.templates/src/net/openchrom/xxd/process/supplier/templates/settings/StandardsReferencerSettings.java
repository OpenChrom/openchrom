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

import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.IPeakQuantifierSettings;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.peaks.ReferencerSettings;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerValidator;

public class StandardsReferencerSettings implements IPeakQuantifierSettings {

	public static final String DESCRIPTION = "Template Standards Referencer";
	//
	@JsonProperty(value = "Referencer Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + StandardsReferencerListUtil.EXAMPLE_MULTIPLE + "'")
	private String referencerSettings = "";

	public void setReferencerSettings(String referencerSettings) {

		this.referencerSettings = referencerSettings;
	}

	public List<ReferencerSettings> getReferencerSettings() {

		StandardsReferencerListUtil util = new StandardsReferencerListUtil();
		StandardsReferencerValidator validator = new StandardsReferencerValidator();
		List<ReferencerSettings> settings = new ArrayList<>();
		//
		List<String> items = util.getList(referencerSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				settings.add(validator.getSettings());
			}
		}
		//
		return settings;
	}
}
