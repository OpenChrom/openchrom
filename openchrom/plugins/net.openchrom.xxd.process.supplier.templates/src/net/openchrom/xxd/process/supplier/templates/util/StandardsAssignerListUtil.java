/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
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

public class StandardsAssignerListUtil extends AbstractTemplateListUtil<StandardsAssignerValidator> {

	public static final String EXAMPLE_SINGLE = "10.52 | 10.63 | Styrene | 10.5 | mg/L | 1.0";
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | Styrene | 10.5 | mg/L | 1.0 ; 10.71 | 10.76 | Benzene | 5.6 | g/kg | 0.7";

	public StandardsAssignerListUtil() {
		super(new StandardsAssignerValidator());
	}
}
