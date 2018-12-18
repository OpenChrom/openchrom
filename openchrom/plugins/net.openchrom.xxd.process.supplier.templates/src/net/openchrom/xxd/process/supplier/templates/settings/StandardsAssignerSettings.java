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

import org.eclipse.chemclipse.chromatogram.msd.quantitation.settings.AbstractPeakQuantifierSettings;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.AssignerSetting;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerListUtil;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerValidator;

public class StandardsAssignerSettings extends AbstractPeakQuantifierSettings {

	public static final String DESCRIPTION = "Template Standards Assigner";
	//
	@JsonProperty(value = "Assigner Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + StandardsAssignerListUtil.EXAMPLE_MULTIPLE + "'")
	private String assignerSettings = "";

	public void setAssignerSettings(String assignerSettings) {

		this.assignerSettings = assignerSettings;
	}

	public List<AssignerSetting> getAssignerSettings() {

		StandardsAssignerListUtil util = new StandardsAssignerListUtil();
		StandardsAssignerValidator validator = new StandardsAssignerValidator();
		List<AssignerSetting> settings = new ArrayList<>();
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
