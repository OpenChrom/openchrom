/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacements;

public class NameSynonymReplacerSettings implements ITemplateSettings {

	@JsonProperty(value = "Scans", defaultValue = "true")
	@JsonPropertyDescription(value = "Names in scan targets will be replaced.")
	private boolean scans = true;
	@JsonProperty(value = "Peaks", defaultValue = "true")
	@JsonPropertyDescription(value = "Names in peak targets will be replaced.")
	private boolean peaks = true;
	@JsonProperty(value = "Chromatogram", defaultValue = "true")
	@JsonPropertyDescription(value = "Names in chromatogram targets will be replaced.")
	private boolean chromatogram = true;
	@JsonProperty(value = "Name Replacements", defaultValue = "")
	@JsonPropertyDescription(value = "Example: '" + NameReplacements.EXAMPLE + "'")
	private NameReplacements nameReplacements;
	@JsonProperty(value = "Reference Chromatograms", defaultValue = "true")
	@JsonPropertyDescription("Process all referenced chromatograms.")
	private boolean processReferenceChromatograms = true;

	public boolean isScans() {

		return scans;
	}

	public void setScans(boolean scans) {

		this.scans = scans;
	}

	public boolean isPeaks() {

		return peaks;
	}

	public void setPeaks(boolean peaks) {

		this.peaks = peaks;
	}

	public boolean isChromatogram() {

		return chromatogram;
	}

	public void setChromatogram(boolean chromatogram) {

		this.chromatogram = chromatogram;
	}

	public NameReplacements getNameReplacements() {

		return nameReplacements;
	}

	public void setNameReplacements(NameReplacements nameReplacements) {

		this.nameReplacements = nameReplacements;
	}

	public boolean isProcessReferenceChromatograms() {

		return processReferenceChromatograms;
	}

	public void setProcessReferenceChromatograms(boolean processReferenceChromatograms) {

		this.processReferenceChromatograms = processReferenceChromatograms;
	}
}