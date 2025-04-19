/*******************************************************************************
 * Copyright (c) 2016, 2025 Matthias Mailänder, Philip Wenig.
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

import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;

public interface IVendorChromatogram extends IChromatogramWSD {

	short getVersion();

	void setVersion(short version);
}
