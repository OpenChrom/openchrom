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

public abstract class AbstractRatioTitles extends AbstractMeasurementResultTitles {

	public static final String RETENTION_TIME = "RT (Minutes)";
	public static final String NAME = "Name";
	//
	public static final String DEVIATION = "Deviation [%]";
	public static final String DEVIATION_WARN = "Deviation Warn [%]";
	public static final String DEVIATION_ERROR = "Deviation Error [%]";

	public AbstractRatioTitles(String[] titles, int[] bounds) throws InvalidAttributesException {
		super(titles, bounds);
	}
}
