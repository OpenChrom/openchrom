/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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

public class ReviewListUtil extends AbstractTemplateListUtil<ReviewValidator> {

	public static final String EXAMPLE_SINGLE = "10.52 | 10.63 | Styrene | 100-42-5 | 103, 104 | VV | true";
	public static final String EXAMPLE_MULTIPLE = "10.52 | 10.63 | Styrene | 100-42-5 | 103, 104 | VV | true; 10.71 | 10.76 | Benzene | 71-43-2 | | VV | true";

	public ReviewListUtil() {
		super(new ReviewValidator());
	}
}
