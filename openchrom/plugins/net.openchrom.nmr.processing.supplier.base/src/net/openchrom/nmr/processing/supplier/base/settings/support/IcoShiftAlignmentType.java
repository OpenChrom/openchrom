/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings.support;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * IcoShiftAlignmentType is used to define how / what should be aligned
 *
 * @author Alexander Stark
 */
public enum IcoShiftAlignmentType {
	/**
	 * select "SINGLE_PEAK" to align all spectra referencing a single peak
	 *
	 * @param SINGLE_PEAK
	 */
	SINGLE_PEAK("Single Peak"), //
	/**
	 * select "WHOLE_SPECTRUM" to align all spectra using the whole spectrum range
	 *
	 * @param WHOLE_SPECTRUM
	 */
	WHOLE_SPECTRUM("Whole Spectrum"), //
	/**
	 * select "NUMBER_OF_INTERVALS" to align the spectra divided in the given no. of
	 * intervals
	 *
	 * @param NUMBER_OF_INTERVALS
	 */
	NUMBER_OF_INTERVALS("Number of Intervals"), //
	/**
	 * select "INTERVAL_LENGTH" to align the spectra divided in intervals of given
	 * length
	 *
	 * @param INTERVAL_LENGTH
	 */
	INTERVAL_LENGTH("Interval Length"), //
	/**
	 * select "USER_DEFINED_INTERVALS" to align the spectra only within the selected
	 * ranges
	 *
	 * @param USER_DEFINED_INTERVALS
	 */
	USER_DEFINED_INTERVALS("User Defined Intervals");

	@JsonValue
	private String alignmentType;

	private IcoShiftAlignmentType(String alignmentType){

		this.alignmentType = alignmentType;
	}

	@Override
	public String toString() {

		return alignmentType;
	}
}
