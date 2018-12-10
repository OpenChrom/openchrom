/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.converter;

import org.eclipse.swtchart.extensions.core.AbstractAxisScaleConverter;
import org.eclipse.swtchart.extensions.core.IAxisScaleConverter;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class MillisecondsToMillimeterConverter extends AbstractAxisScaleConverter implements IAxisScaleConverter {

	/**
	 * 20 mm/s
	 * 1000.0d -> 0.02 mm/ms
	 * 10.0d -> 0.002 cm/ms
	 */
	private static final double CENTIMETER_CORRELATION_FACTOR = PreferenceSupplier.getScanVelocity() / 1000.0d;

	@Override
	public double convertToSecondaryUnit(double primaryValue) {

		return primaryValue * CENTIMETER_CORRELATION_FACTOR;
	}

	@Override
	public double convertToPrimaryUnit(double secondaryValue) {

		return secondaryValue / CENTIMETER_CORRELATION_FACTOR;
	}
}
