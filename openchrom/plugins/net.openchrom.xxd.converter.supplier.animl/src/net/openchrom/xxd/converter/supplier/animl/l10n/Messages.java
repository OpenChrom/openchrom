/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/

package net.openchrom.xxd.converter.supplier.animl.l10n;

import org.eclipse.osgi.util.NLS; 

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "net.openchrom.xxd.converter.supplier.animl.l10n.messages"; //$NON-NLS-1$ //

	public static String importChromatogram;
	public static String exportChromatogram;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {

	}
}
