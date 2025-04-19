/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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

public class StandardsAssignerListUtil extends AbstractTemplateListUtil<StandardsAssignerValidator> {

	public static final String EXAMPLE_SINGLE = "10.52 | 10.63 | Styrene | 10.5 | mg/L | 1.0 | 104 103";
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | Styrene | 10.5 | mg/L | 1.0 | 104 103; 10.71 | 10.76 | Benzene | 5.6 | g/kg | 0.7 | ";

	public StandardsAssignerListUtil() {

		super(new StandardsAssignerValidator());
	}
}