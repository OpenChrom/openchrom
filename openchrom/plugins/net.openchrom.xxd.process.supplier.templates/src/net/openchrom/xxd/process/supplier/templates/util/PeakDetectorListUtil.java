/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
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

public class PeakDetectorListUtil extends AbstractTemplateListUtil<PeakDetectorValidator> {

	public static final String EXAMPLE_SINGLE = "10.52 | 10.63 | VV | 103, 104, 108-110 | true";
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | VV | 103, 104, 108-110 | true; 10.71 | 10.76 | BB | 105, 106 | false";

	public PeakDetectorListUtil() {
		super(new PeakDetectorValidator());
	}
}
