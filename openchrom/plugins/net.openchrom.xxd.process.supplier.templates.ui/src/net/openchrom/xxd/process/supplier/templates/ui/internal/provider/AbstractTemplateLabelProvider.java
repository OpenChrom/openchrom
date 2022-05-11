/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.provider.AbstractChemClipseLabelProvider;

public abstract class AbstractTemplateLabelProvider extends AbstractChemClipseLabelProvider {

	public static final String POSITION_START = "Position Start";
	public static final String POSITION_STOP = "Position Stop";
	public static final String POSITION_DIRECTIVE = "Position Directive";
	public static final String REFERENCE_IDENTIFIER = "Reference Identifier";
	public static final String TRACES = "Traces";
	public static final String NAME = "Name";
	public static final String PEAK_TYPE = "Peak Type";
	public static final String OPTIMIZE_RANGE = "Optimize Range (VV)";
	public static final String CAS_NUMBER = "CAS";
	public static final String COMMENTS = "Comments";
	public static final String CONTRIBUTOR = "Contributor";
	public static final String REFERENCE = "Reference";
	public static final String IDENTIFIER = "Identifier";
	public static final String INTEGRATOR = "Integrator";
	public static final String REPORT_STRATEGY = "Strategy";
	public static final String NAME_ISTD = "ISTD (Internal Standard)";
	public static final String CONCENTRATION = "Concentration";
	public static final String CONCENTRATION_UNIT = "Concentration Unit";
	public static final String RESPONSE_FACTOR = "Response Factor";
	//
	public static final String PLACEHOLDER = "--";
	//
	protected DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.0##");

	public String getFormattedPosition(double value) {

		return getFormattedPosition(value, null);
	}

	public String getFormattedPosition(double value, String placeholder) {

		if(value == 0.0d && placeholder != null) {
			return placeholder;
		} else {
			return decimalFormat.format(value);
		}
	}

	public String getFormattedPeakType(PeakType peakType) {

		return peakType != null ? peakType.label() : PLACEHOLDER;
	}

	public String getFormattedIdentifier(String identifier) {

		return (identifier == null || identifier.isEmpty()) ? PLACEHOLDER : identifier;
	}
}