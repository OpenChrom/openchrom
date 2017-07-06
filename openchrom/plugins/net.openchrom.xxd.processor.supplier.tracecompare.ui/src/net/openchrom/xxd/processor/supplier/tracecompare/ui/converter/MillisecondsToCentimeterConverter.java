/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
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

import org.eclipse.eavp.service.swtchart.core.AbstractAxisScaleConverter;
import org.eclipse.eavp.service.swtchart.core.IAxisScaleConverter;

public class MillisecondsToCentimeterConverter extends AbstractAxisScaleConverter implements IAxisScaleConverter {

	private static final double CENTIMETER_CORRELATION_FACTOR = 50000.0d;

	@Override
	public double convertToSecondaryUnit(double primaryValue) {

		return primaryValue / CENTIMETER_CORRELATION_FACTOR;
	}

	@Override
	public double convertToPrimaryUnit(double secondaryValue) {

		return secondaryValue * CENTIMETER_CORRELATION_FACTOR;
	}
}
