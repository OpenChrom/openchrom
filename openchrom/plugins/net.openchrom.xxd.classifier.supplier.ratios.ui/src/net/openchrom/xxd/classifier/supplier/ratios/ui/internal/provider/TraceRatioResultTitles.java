/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider;

import javax.naming.directory.InvalidAttributesException;

import org.eclipse.chemclipse.ux.extension.ui.support.AbstractMeasurementResultTitles;
import org.eclipse.chemclipse.ux.extension.ui.support.IMeasurementResultTitles;

public class TraceRatioResultTitles extends AbstractMeasurementResultTitles implements IMeasurementResultTitles {

	public static final String RETENTION_TIME = "RT (Minutes)";
	public static final String NAME = "Name";
	public static final String TEST_CASE = "Test Case";
	public static final String EXPECTED_RATIO = "Expected Ratio [%]";
	public static final String ACTUAL_RATIO = "Actual Ratio [%]";
	public static final String DEVIATION_WARN = "Deviation Warn [%]";
	public static final String DEVIATION_ERROR = "Deviation Error [%]";
	//
	public static final String[] TITLES = { //
			RETENTION_TIME, //
			NAME, //
			TEST_CASE, //
			EXPECTED_RATIO, //
			ACTUAL_RATIO, //
			DEVIATION_WARN, //
			DEVIATION_ERROR //
	};
	public static final int[] BOUNDS = { //
			80, //
			150, //
			120, //
			120, //
			80, //
			80, //
			80 //
	};

	public TraceRatioResultTitles() throws InvalidAttributesException {
		super(TITLES, BOUNDS);
	}
}
