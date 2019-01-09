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

public class MeasurementResultTitles extends AbstractMeasurementResultTitles implements IMeasurementResultTitles {

	private static final String[] titles = {"Target", "Deviation [%]", "Note"};
	private static final int[] bounds = {250, 150, 100};

	public MeasurementResultTitles() throws InvalidAttributesException {
		super(titles, bounds);
	}
}
