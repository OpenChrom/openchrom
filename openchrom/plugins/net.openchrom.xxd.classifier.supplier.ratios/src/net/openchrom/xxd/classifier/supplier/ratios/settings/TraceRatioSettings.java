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

import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.TraceRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.TraceRatioValidator;

public class TraceRatioSettings extends AbstractChromatogramClassifierSettings {

	@JsonProperty(value = "Trace Ratio Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + TraceRatioListUtil.EXAMPLE_MULTIPLE + "'")
	private String traceRatioSettings = "";

	public void setTraceRatioSettings(String traceRatioSettings) {

		this.traceRatioSettings = traceRatioSettings;
	}

	public TraceRatios getTraceRatioSettings() {

		TraceRatioListUtil util = new TraceRatioListUtil();
		TraceRatioValidator validator = new TraceRatioValidator();
		TraceRatios traceRatios = new TraceRatios();
		//
		List<String> items = util.getList(traceRatioSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				traceRatios.add(validator.getSetting());
			}
		}
		// traceRatios.add(new TraceRatio("Naphthalin-D8 (ISTD)", "136:137", 9.90, 5.0d, 15.0));
		// traceRatios.add(new TraceRatio("Naphthalin", "128:127", 14.60, 5.0d, 15.0));
		//
		return traceRatios;
	}
}
