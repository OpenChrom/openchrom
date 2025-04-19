/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

public class CompensationQuantListUtil extends AbstractTemplateListUtil<CompensationQuantValidator> {

	public static final String EXAMPLE_SINGLE = "Substance A | Styrene | 1.0 | mg/L | false | ppm";
	public static final String EXAMPLE_MULTIPLE = "Substance A | Styrene | 1.0 | mg/L | false | ppm ; Substance B | Benzene | 1.2 | g/kg | true | ";

	public CompensationQuantListUtil() {

		super(new CompensationQuantValidator());
	}
}