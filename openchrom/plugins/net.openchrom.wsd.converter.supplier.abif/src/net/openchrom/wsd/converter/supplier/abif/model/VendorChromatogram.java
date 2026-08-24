/*******************************************************************************
 * Copyright (c) 2016, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.model;

import org.eclipse.chemclipse.dsd.model.core.AbstractChromatogramDSD;

public class VendorChromatogram extends AbstractChromatogramDSD implements IVendorChromatogram {

	private static final long serialVersionUID = 99309012496579942L;
	private short version;

	@Override
	public short getVersion() {

		return version;
	}

	@Override
	public void setVersion(short version) {

		this.version = version;
	}
}