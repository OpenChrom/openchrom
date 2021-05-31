/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.gaml.internal.v120.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "units")
@XmlEnum
public enum Units {
	ABSORBANCE, AMPERES, ANGSTROMS, ATOMICMASSUNITS, CALORIES, CELSIUS, CENTIMETERS, DAYS, DECIBELS, DEGREES, ELECTRONVOLTS, EMISSION, FAHRENHEIT, GHERTZ, GRAMS, HERTZ, HOURS, JOULES, KELVIN, KILOCALORIES, KILOGRAMS, KILOHERTZ, KILOMETERS, KILOWATTS, KUBELKAMUNK, LITERS, LOGREFLECTANCE, MASSCHARGERATIO, MEGAHERTZ, MEGAWATTS, METERS, MICROGRAMS, MICRONS, MICROSECONDS, MILLIABSORBANCE, MILLIAMPS, MILLIGRAMS, MILLILITERS, MILLIMETERS, MILLIMOLAR, MILLISECONDS, MILLIVOLTS, MILLIWATTS, MINUTES, MOLAR, MOLES, NANOGRAMS, NANOMETERS, NANOSECONDS, PPB, PPM, PPT, RADIANS, RAMANSHIFT, REFLECTANCE, SECONDS, TRANSMISSIONPERCENT, TRANSMITTANCE, UNKNOWN, VOLTS, WATTS, WAVENUMBER, YEARS, INCHES, MICROABSORBANCE, MICROVOLTS, PERCENT, PSI, TESLA;

	public String value() {

		return name();
	}

	public static Units fromValue(String v) {

		return valueOf(v);
	}
}
