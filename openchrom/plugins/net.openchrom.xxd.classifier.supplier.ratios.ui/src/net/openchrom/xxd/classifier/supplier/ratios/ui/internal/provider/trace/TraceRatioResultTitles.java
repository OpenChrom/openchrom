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
package net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.trace;

import javax.naming.directory.InvalidAttributesException;

import org.eclipse.chemclipse.ux.extension.ui.support.IMeasurementResultTitles;

import net.openchrom.xxd.classifier.supplier.ratios.ui.internal.provider.AbstractPeakRatioTitles;

public class TraceRatioResultTitles extends AbstractPeakRatioTitles implements IMeasurementResultTitles {

	public static final String TEST_CASE = "Test Case";
	public static final String EXPECTED_RATIO = "Expected Ratio [%]";
	public static final String RATIO = "Ratio [%]";
	//
	public static final String[] TITLES_SETTINGS = { //
			NAME, //
			TEST_CASE, //
			EXPECTED_RATIO, //
			DEVIATION_WARN, //
			DEVIATION_ERROR //
	};
	//
	public static final int[] BOUNDS_SETTINGS = { //
			150, //
			120, //
			80, //
			80, //
			80 //
	};
	//
	public static final String[] TITLES_RESULTS = { //
			RETENTION_TIME, //
			NAME, //
			TEST_CASE, //
			EXPECTED_RATIO, //
			RATIO, //
			DEVIATION //
	};
	//
	public static final int[] BOUNDS_RESULTS = { //
			80, //
			130, //
			110, //
			80, //
			80, //
			80 //
	};

	public TraceRatioResultTitles() throws InvalidAttributesException {
		super(TITLES_RESULTS, BOUNDS_RESULTS);
	}
}
