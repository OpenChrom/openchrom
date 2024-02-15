/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/

package net.openchrom.xxd.converter.supplier.animl.l10n;

import org.eclipse.osgi.util.NLS; 

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "net.openchrom.xxd.converter.supplier.animl.l10n.messages"; //$NON-NLS-1$ //
	//
	public static String importChromatogram;
	public static String exportChromatogram;
	//
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {

	}
}
