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
 * GapFillingType will define how the resulting gaps after the alignment are
 * closed again
 *
 * @author Alexander Stark
 */
public enum IcoShiftAlignmentGapFillingType {
	/**
	 * select "ZERO" to fill the resulting gap with zeros
	 *
	 * @param ZERO
	 */
	ZERO("Zero"), //
	/**
	 * select "MARGIN" to fill the resulting gap values from the right/left margin
	 * of each interval
	 *
	 * @param MARGIN
	 */
	MARGIN("Margin"); //

	@JsonValue
	private String gapFillingType;

	private IcoShiftAlignmentGapFillingType(String gapFillingType){

		this.gapFillingType = gapFillingType;
	}

	@Override
	public String toString() {

		return gapFillingType;
	}
}
