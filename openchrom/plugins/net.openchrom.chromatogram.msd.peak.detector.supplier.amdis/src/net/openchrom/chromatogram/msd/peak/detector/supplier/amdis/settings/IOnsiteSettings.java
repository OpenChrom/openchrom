/*******************************************************************************
 * Copyright (c) 2016, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

public interface IOnsiteSettings {

	int MIN_PEAK_WIDTH = 12;
	int MAX_PEAK_WIDTH = 32;
	int VALUE_SOLVENT_TAILING_MZ = 84;
	int VALUE_COLUMN_BLEED_MZ = 207;
	//
	String KEY_SCAN_DIRECTION = "SCANDIR"; // High to Low = -1, None = 0, Low to High = 1
	String KEY_INSTRUMENT_FILE = "INFILE"; // CDF = 2
	String KEY_INSTRUMENT_TYPE = "INSTYPE"; // Quadrupole = 0, Ion Trap = 1, Magnetic Sector = 2, SIM = 3
	//
	String KEY_LOW_MZ_AUTO = "LOWMZAUTO"; // No = 0, Yes = 1
	String KEY_START_MZ = "LOMASS";
	String KEY_HIGH_MZ_AUTO = "HIGHMZAUTO"; // No = 0, Yes = 1
	String KEY_STOP_MZ = "MXMASS";
	//
	String KEY_OMIT_MZ = "OMITMZ"; // 0 = no, 1 = yes
	String KEY_OMITED_MZ = "OMITEDMZ"; // 0 = TIC ... 58 59 60
	//
	String KEY_USE_SOLVENT_TAILING = "USESTAIL"; // No = 0, Yes = 1
	String KEY_SOLVENT_TAILING_MZ = "STAILMZ"; // 84
	String KEY_USE_COLUMN_BLEED = "USECOLUMNBLEED";// No = 0, Yes = 1
	String KEY_COLUMN_BLEED_MZ = "BLEEDMZ"; // 207
	//
	String KEY_THRESHOLD = "THRESHOLD"; // Threshold -> High = 4, Medium = 3, Low = 2, Off = 1
	String KEY_PEAK_WIDTH = "PEAKWIDTH"; // 12 ... 32
	String KEY_ADJACENT_PEAK_SUBTRACTION = "DECLEVEL"; // Two = 0, One = 1, None = 2
	String KEY_RESOLUTION = "RESOLUTION"; // High = 0, Medium = 1, Low = 2
	String KEY_SENSITIVITY = "SENSIT"; // Very High = 60, High = 30, Medium = 10, Low = 3, Very Low = 1
	String KEY_SHAPE_REQUIREMENTS = "PEAKSHAPE"; // High = 2, Medium = 1, Low = 0

	String getLine(String line);

	void setValue(String key, String value);
}
