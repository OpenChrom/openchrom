/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.cdf.model;

import org.eclipse.chemclipse.wsd.model.core.AbstractChromatogramWSD;

public class VendorChromatogramWSD extends AbstractChromatogramWSD implements IVendorChromatogram {

	private static final long serialVersionUID = -563182237151954683L;

	@Override
	public String getName() {

		return extractNameFromFile("XYChromatogramDAD");
	}
}
