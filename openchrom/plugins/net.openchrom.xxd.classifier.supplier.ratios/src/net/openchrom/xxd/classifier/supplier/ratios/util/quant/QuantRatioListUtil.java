/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.util.quant;

import net.openchrom.xxd.classifier.supplier.ratios.util.AbstractRatioListUtil;

public class QuantRatioListUtil extends AbstractRatioListUtil<QuantRatioValidator> {

	public static final String EXAMPLE_SINGLE = "Naphthalin | Naphthalin-D8 | 1.0 | mg/L | 5.0 | 15.0";
	public static final String EXAMPLE_MULTIPLE = "Naphthalin | Naphthalin-D8 | 1.0 | mg/L | 5.0 | 15.0; Styrene | Toluene | 12.0 | g/L | 5.0 | 15.0";

	public QuantRatioListUtil() {
		super(new QuantRatioValidator());
	}
}
