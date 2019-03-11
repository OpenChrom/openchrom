/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.settings;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.classifier.settings.AbstractChromatogramClassifierSettings;
import org.eclipse.core.runtime.IStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.classifier.supplier.ratios.model.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.TimeRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.TimeRatioValidator;

public class TimeRatioSettings extends AbstractChromatogramClassifierSettings {

	@JsonProperty(value = "Time Ratio Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + TimeRatioListUtil.EXAMPLE_MULTIPLE + "'")
	private String timeRatioSettings = "";

	public void setTimeRatioSettings(String timeRatioSettings) {

		this.timeRatioSettings = timeRatioSettings;
	}

	public TimeRatios getTimeRatioSettings() {

		TimeRatioListUtil util = new TimeRatioListUtil();
		TimeRatioValidator validator = new TimeRatioValidator();
		TimeRatios timeRatios = new TimeRatios();
		//
		List<String> items = util.getList(timeRatioSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				timeRatios.add(validator.getSetting());
			}
		}
		// traceRatios.add(new TraceRatio("Naphthalin-D8 (ISTD)", "136:137", 9.90, 5.0d, 15.0));
		// traceRatios.add(new TraceRatio("Naphthalin", "128:127", 14.60, 5.0d, 15.0));
		//
		return timeRatios;
	}
}
