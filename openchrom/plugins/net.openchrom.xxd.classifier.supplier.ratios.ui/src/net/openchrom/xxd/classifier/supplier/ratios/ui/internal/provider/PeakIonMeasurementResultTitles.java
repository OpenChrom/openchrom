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

public class PeakIonMeasurementResultTitles extends AbstractMeasurementResultTitles implements IMeasurementResultTitles {

	private static final String[] titles = {"RT (Minutes)", "Name", "Test Case", "Expected Ratio [%]", "Actual Ratio [%]", "Deviation Warn", "Deviation Error"};
	private static final int[] bounds = {80, 150, 120, 120, 80, 80, 80};

	public PeakIonMeasurementResultTitles() throws InvalidAttributesException {
		super(titles, bounds);
	}
}
