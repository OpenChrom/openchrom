/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
