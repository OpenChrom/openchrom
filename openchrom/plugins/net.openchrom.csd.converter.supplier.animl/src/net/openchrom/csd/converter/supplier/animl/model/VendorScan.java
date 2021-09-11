/*******************************************************************************
 * Copyright (c) 2008, 2021 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.animl.model;

import org.eclipse.chemclipse.csd.model.core.AbstractScanCSD;

public class VendorScan extends AbstractScanCSD implements IVendorScan {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -8249308239772553688L;
	private float totalSignal = 0.0f;

	public VendorScan() {

	}

	public VendorScan(float totalSignal) {

		this.totalSignal = totalSignal;
	}

	@Override
	public float getTotalSignal() {

		return totalSignal;
	}

	@Override
	public void setTotalSignal(float totalSignal) {

		this.totalSignal = totalSignal;
	}

	@Override
	public void adjustTotalSignal(float totalSignal) {

		this.totalSignal = totalSignal;
	}
}
