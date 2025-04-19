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
package net.openchrom.xxd.classifier.supplier.ratios.util.trace;

import net.openchrom.xxd.classifier.supplier.ratios.util.AbstractRatioListUtil;

public class TraceRatioListUtil extends AbstractRatioListUtil<TraceRatioValidator> {

	public static final String EXAMPLE_SINGLE = "Naphthalin | 128:127 | 14.6 | 5.0 | 15.0";
	public static final String EXAMPLE_MULTIPLE = "Naphthalin | 128:127 | 14.6 | 5.0 | 15.0; Styrene | 104:103 | 3.5 | 5.0 | 15.0";

	public TraceRatioListUtil() {
		super(new TraceRatioValidator());
	}
}
