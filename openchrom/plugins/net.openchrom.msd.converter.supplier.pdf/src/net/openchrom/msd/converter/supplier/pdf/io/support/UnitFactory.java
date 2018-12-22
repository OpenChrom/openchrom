/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.io.support;

import java.util.HashMap;
import java.util.Map;

public class UnitFactory {

	private static final Map<Unit, IUnitConverter> CONVERTER_MAP = new HashMap<>();

	public static IUnitConverter getInstance(Unit unit) {

		if(!CONVERTER_MAP.containsKey(unit)) {
			IUnitConverter unitConverter = new IUnitConverter() {

				@Override
				public float getFactor() {

					return unit.getFactor();
				}

				@Override
				public float convert(float value) {

					return value * getFactor();
				}
			};
			CONVERTER_MAP.put(unit, unitConverter);
		}
		//
		return CONVERTER_MAP.get(unit);
	}
}
