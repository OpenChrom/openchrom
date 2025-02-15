/*******************************************************************************
 * Copyright (c) 2008, 2025 Lablicate GmbH.
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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.AbstractPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.support.literature.LiteratureReference;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractProcessSettings extends AbstractPeakDetectorSettingsMSD implements IProcessSettings {

	@JsonProperty(value = "Min S/N Ratio", defaultValue = "0.0")
	private float minSignalToNoiseRatio = 0.0f;

	@JsonProperty(value = "Min Leading", defaultValue = "0.1")
	private float minLeading = 0.1f;

	@JsonProperty(value = "Max Leading", defaultValue = "2.0")
	private float maxLeading = 2.0f;

	@JsonProperty(value = "Min Tailing", defaultValue = "0.1")
	private float minTailing = 0.1f;

	@JsonProperty(value = "Max Tailing", defaultValue = "2.0")
	private float maxTailing = 2.0f;

	@JsonProperty(value = "Filter Model Peaks", defaultValue = "MP1")
	private ModelPeakOption modelPeakOption = ModelPeakOption.MP1;

	@Override
	public float getMinSignalToNoiseRatio() {

		return minSignalToNoiseRatio;
	}

	@Override
	public void setMinSignalToNoiseRatio(float minSignalToNoiseRatio) {

		this.minSignalToNoiseRatio = minSignalToNoiseRatio;
	}

	@Override
	public float getMinLeading() {

		return minLeading;
	}

	@Override
	public void setMinLeading(float minLeading) {

		this.minLeading = minLeading;
	}

	@Override
	public float getMaxLeading() {

		return maxLeading;
	}

	@Override
	public void setMaxLeading(float maxLeading) {

		this.maxLeading = maxLeading;
	}

	@Override
	public float getMinTailing() {

		return minTailing;
	}

	@Override
	public void setMinTailing(float minTailing) {

		this.minTailing = minTailing;
	}

	@Override
	public float getMaxTailing() {

		return maxTailing;
	}

	@Override
	public void setMaxTailing(float maxTailing) {

		this.maxTailing = maxTailing;
	}

	@Override
	public ModelPeakOption getModelPeakOption() {

		return modelPeakOption;
	}

	@Override
	public void setModelPeakOption(ModelPeakOption modelPeakOption) {

		this.modelPeakOption = modelPeakOption;
	}

	@Override
	public List<LiteratureReference> getLiteratureReferences() {

		return Arrays.asList(createLiteratureReference());
	}

	private static LiteratureReference createLiteratureReference() {

		String content;
		try {
			content = new String(AbstractProcessSettings.class.getResourceAsStream("10.1016S1044-03059900047-1.ris").readAllBytes(), StandardCharsets.US_ASCII);
		} catch(Exception e) {
			content = "https://doi.org/10.1016/S1044-0305(99)00047-1";
		}
		//
		return new LiteratureReference(content);
	}
}