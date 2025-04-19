/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.internal.model;

public class Mz5 {

	public static final String CHROMATOGRAM_TIME = "ChomatogramTime"; // Don't fix the typo! https://github.com/ProteoWizard/pwiz/pull/738
	public static final String CHROMATOGRAM_INTENSITY = "ChromatogramIntensity";
	public static final String CHROMATOGRAM_INDEX = "ChromatogramIndex";
	//
	public static final String SPECTRUM_INDEX = "SpectrumIndex";
	public static final String SPECTRUM_MZ = "SpectrumMZ";
	public static final String SPECTRUM_INTENSITY = "SpectrumIntensity";
	//
	public static final String CV_REFERENCE = "CVReference";
	public static final String CV_PARAM = "CVParam";

	private Mz5() {

	}
}
