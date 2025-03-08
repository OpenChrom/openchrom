/*******************************************************************************
 * Copyright (c) 2014, 2025 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.identifier.settings.IChromatogramIdentifierSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.AbstractIdentifierSettings;
import org.eclipse.chemclipse.support.literature.LiteratureReference;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class IdentifierSettings extends AbstractIdentifierSettings implements IChromatogramIdentifierSettings {

	private static final Logger logger = Logger.getLogger(IdentifierSettings.class);

	@JsonProperty(value = "Allow Radicals", defaultValue = "false")
	private boolean allowRadicals = PreferenceSupplier.DEF_ALLOW_RADICALS;

	@JsonProperty(value = "Detailed Failure Analysis", defaultValue = "false")
	private boolean detailedFailureAnalysis = PreferenceSupplier.DEF_DETAILED_FAILURE_ANALYSIS;

	@JsonProperty(value = "Interpret Acids Without The Word Acid", defaultValue = "false")
	private boolean interpretAcidsWithoutTheWordAcid = PreferenceSupplier.DEF_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID;

	@JsonProperty(value = "Output Radicals As WildCard Atoms", defaultValue = "false")
	private boolean outputRadicalsAsWildCardAtoms = PreferenceSupplier.DEF_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS;

	@JsonProperty(value = "Warn Rather Than Fail On Uninterpretable Stereochemistry", defaultValue = "false")
	private boolean warnRatherThanFailOnUninterpretableStereochemistry = PreferenceSupplier.DEF_WARN_RATHER_THAN_FAIL;

	public boolean isAllowRadicals() {

		return allowRadicals;
	}

	public void setAllowRadicals(boolean allowRadicals) {

		this.allowRadicals = allowRadicals;
	}

	public boolean isDetailedFailureAnalysis() {

		return detailedFailureAnalysis;
	}

	public void setDetailedFailureAnalysis(boolean detailedFailureAnalysis) {

		this.detailedFailureAnalysis = detailedFailureAnalysis;
	}

	public boolean isInterpretAcidsWithoutTheWordAcid() {

		return interpretAcidsWithoutTheWordAcid;
	}

	public void setInterpretAcidsWithoutTheWordAcid(boolean interpretAcidsWithoutTheWordAcid) {

		this.interpretAcidsWithoutTheWordAcid = interpretAcidsWithoutTheWordAcid;
	}

	public boolean isOutputRadicalsAsWildCardAtoms() {

		return outputRadicalsAsWildCardAtoms;
	}

	public void setOutputRadicalsAsWildCardAtoms(boolean outputRadicalsAsWildCardAtoms) {

		this.outputRadicalsAsWildCardAtoms = outputRadicalsAsWildCardAtoms;
	}

	public boolean isWarnRatherThanFailOnUninterpretableStereochemistry() {

		return warnRatherThanFailOnUninterpretableStereochemistry;
	}

	public void setWarnRatherThanFailOnUninterpretableStereochemistry(boolean warnRatherThanFailOnUninterpretableStereochemistry) {

		this.warnRatherThanFailOnUninterpretableStereochemistry = warnRatherThanFailOnUninterpretableStereochemistry;
	}

	@Override
	public List<LiteratureReference> getLiteratureReferences() {

		List<LiteratureReference> literatureReferences = new ArrayList<>();
		literatureReferences.add(createLiteratureReference("ci025584y.ris", "10.1021/ci025584y"));
		literatureReferences.add(createLiteratureReference("ci100384d.ris", "10.1021/ci100384d"));
		return literatureReferences;
	}

	private static LiteratureReference createLiteratureReference(String file, String doi) {

		String content;
		try {
			content = new String(IdentifierSettings.class.getResourceAsStream(file).readAllBytes());
		} catch(IOException | NullPointerException e) {
			content = doi;
			logger.warn(e);
		}
		return new LiteratureReference(content);
	}
}
