/*******************************************************************************
 * Copyright (c) 2014, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.chromatogram.msd.identifier.settings.ChromatogramIdentifierAdapterSettings;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class IdentifierSettings extends ChromatogramIdentifierAdapterSettings {

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
}