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

public class ConverterFactory {

	private static final Map<Unit, IUnitConverter> UNIT_CONVERTER_MAP = new HashMap<>();
	private static final Map<Base, IBaseConverter> BASE_CONVERTER_MAP = new HashMap<>();

	public static IUnitConverter getInstance(Unit unit) {

		if(!UNIT_CONVERTER_MAP.containsKey(unit)) {
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
			UNIT_CONVERTER_MAP.put(unit, unitConverter);
		}
		//
		return UNIT_CONVERTER_MAP.get(unit);
	}

	public static IBaseConverter getInstance(Base base) {

		if(!BASE_CONVERTER_MAP.containsKey(base)) {
			IBaseConverter baseConverter = new IBaseConverter() {

				@Override
				public float getPositionX(float pageWidth, float x) {

					return base.getFactorWidth() * pageWidth + (base.isSubtractWidth() ? -x : x);
				}

				@Override
				public float getPositionY(float pageHeight, float y) {

					return base.getFactorHeight() * pageHeight + (base.isSubtractHeight() ? -y : y);
				}
			};
			BASE_CONVERTER_MAP.put(base, baseConverter);
		}
		//
		return BASE_CONVERTER_MAP.get(base);
	}
}
