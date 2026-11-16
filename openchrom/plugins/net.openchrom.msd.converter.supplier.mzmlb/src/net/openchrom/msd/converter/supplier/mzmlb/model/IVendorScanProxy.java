/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mzmlb.model;

import org.eclipse.chemclipse.msd.model.core.IMassSpectrumProxy;

import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;

public interface IVendorScanProxy extends IVendorScan, IMassSpectrumProxy {

	/**
	 * Adds the location of the binary data of a spectrum.
	 * Several spectra may be combined into this scan.
	 */
	void addScanMarker(IScanMarker scanMarker);
}
