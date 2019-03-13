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
package net.openchrom.xxd.classifier.supplier.ratios.util.time;

import net.openchrom.xxd.classifier.supplier.ratios.util.AbstractRatioListUtil;

public class TimeRatioListUtil extends AbstractRatioListUtil<TimeRatioValidator> {

	public static final String EXAMPLE_SINGLE = "Naphthalin | 3.45 | 5.0 | 15.0";
	public static final String EXAMPLE_MULTIPLE = "Naphthalin | 3.45 | 5.0 | 15.0; Styrene | 4.05 | 5.0 | 15.0";

	public TimeRatioListUtil() {
		super(new TimeRatioValidator());
	}
}
