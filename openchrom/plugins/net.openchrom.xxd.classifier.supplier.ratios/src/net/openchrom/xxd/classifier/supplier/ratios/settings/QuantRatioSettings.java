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

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;
import net.openchrom.xxd.classifier.supplier.ratios.util.quant.QuantRatioListUtil;
import net.openchrom.xxd.classifier.supplier.ratios.util.quant.QuantRatioValidator;

public class QuantRatioSettings extends AbstractChromatogramClassifierSettings {

	@JsonProperty(value = "Quant Ratio Settings", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + QuantRatioListUtil.EXAMPLE_MULTIPLE + "'")
	private String ratioSettings = "";

	public void setRatioSettings(String ratioSettings) {

		this.ratioSettings = ratioSettings;
	}

	public QuantRatios getRatioSettings() {

		QuantRatioListUtil util = new QuantRatioListUtil();
		QuantRatioValidator validator = new QuantRatioValidator();
		QuantRatios ratios = new QuantRatios();
		//
		List<String> items = util.getList(ratioSettings);
		for(String item : items) {
			IStatus status = validator.validate(item);
			if(status.isOK()) {
				ratios.add(validator.getSetting());
			}
		}
		//
		return ratios;
	}
}
