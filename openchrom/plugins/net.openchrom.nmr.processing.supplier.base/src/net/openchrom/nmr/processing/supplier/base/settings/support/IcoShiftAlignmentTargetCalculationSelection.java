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
 * TargetCalculationSelection allows the calculation of a target at which the
 * data will be aligned
 *
 * @author Alexander Stark
 */
public enum IcoShiftAlignmentTargetCalculationSelection {
	/**
	 * select "MEAN" to calculate a mean target over all spectra from the selected
	 * dataset
	 *
	 * @param MEAN
	 */
	MEAN("Mean"), //
	/**
	 * select "MEDIAN" to calculate a median target over all spectra from the
	 * selected dataset
	 *
	 * @param MEDIAN
	 */
	MEDIAN("Median"), //
	/**
	 * select "MAX" to calculate a max target over all spectra from the selected
	 * dataset but only within each selected interval
	 *
	 * @param MAX
	 */
	MAX("Max");//

	@JsonValue
	private String targetCalculationSelection;

	private IcoShiftAlignmentTargetCalculationSelection(String targetCalculationSelection){

		this.targetCalculationSelection = targetCalculationSelection;
	}

	@Override
	public String toString() {

		return targetCalculationSelection;
	}
}
