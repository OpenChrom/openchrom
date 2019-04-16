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
package net.openchrom.xxd.process.supplier.templates.util;

public class CompensationQuantListUtil extends AbstractTemplateListUtil<CompensationQuantValidator> {

	public static final String EXAMPLE_SINGLE = "Substance A | Styrene | 1.0 | mg/L";
	public static final String EXAMPLE_MULTIPLE = "Substance A | Styrene | 1.0 | mg/L ; Substance B | Benzene | 1.2 | g/kg";

	public CompensationQuantListUtil() {
		super(new CompensationQuantValidator());
	}
}
