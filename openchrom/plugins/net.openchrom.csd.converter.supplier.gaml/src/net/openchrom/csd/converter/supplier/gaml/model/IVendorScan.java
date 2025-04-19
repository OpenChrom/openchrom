/*******************************************************************************
 * Copyright (c) 2008, 2025 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.gaml.model;

import org.eclipse.chemclipse.csd.model.core.IScanCSD;

public interface IVendorScan extends IScanCSD {

	/**
	 * Stores the total signal.
	 * 
	 * @param totalSignal
	 */
	void setTotalSignal(float totalSignal);
}
