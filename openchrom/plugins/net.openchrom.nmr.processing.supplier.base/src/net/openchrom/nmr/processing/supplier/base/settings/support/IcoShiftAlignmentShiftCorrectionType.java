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
 * ShiftCorrectionType will select the way the maximum shift correction value is
 * calculated
 *
 * @author Alexander Stark
 */
public enum IcoShiftAlignmentShiftCorrectionType {
	/**
	 * select "FAST" to get a shift correction value fast, without any correction
	 *
	 * @param FAST
	 */
	FAST("Fast"), //
	/**
	 * select "BEST" to get a possibly corrected shift correction value
	 *
	 * @param BEST
	 */
	BEST("Best"), //
	/**
	 * select "USER_DEFINED" to define a shift correction value requires user input
	 * {@link shiftCorrectionTypeValue}
	 *
	 * @param USER_DEFINED
	 *
	 */
	USER_DEFINED("User Defined");//

	@JsonValue
	private String shiftCorrectionType;

	private IcoShiftAlignmentShiftCorrectionType(String shiftCorrectionType){

		this.shiftCorrectionType = shiftCorrectionType;
	}

	@Override
	public String toString() {

		return shiftCorrectionType;
	}
}
