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
package net.openchrom.xxd.classifier.supplier.ratios.util.time;

import net.openchrom.xxd.classifier.supplier.ratios.util.AbstractRatioListUtil;

public class TimeRatioListUtil extends AbstractRatioListUtil<TimeRatioValidator> {

	public static final String EXAMPLE_SINGLE = "Naphthalin | 3.45 | 5.0 | 15.0";
	public static final String EXAMPLE_MULTIPLE = "Naphthalin | 3.45 | 5.0 | 15.0; Styrene | 4.05 | 5.0 | 15.0";

	public TimeRatioListUtil() {
		super(new TimeRatioValidator());
	}
}
