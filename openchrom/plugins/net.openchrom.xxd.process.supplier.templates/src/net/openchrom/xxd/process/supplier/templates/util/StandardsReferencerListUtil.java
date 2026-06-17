/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
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

public class StandardsReferencerListUtil extends AbstractTemplateListUtil<StandardsReferencerValidator> {

	public static final String EXAMPLE = "10.52 | 10.63 | Toluene (ISTD) | Styrene (Target or Empty)";

	public StandardsReferencerListUtil() {

		super(new StandardsReferencerValidator());
	}
}