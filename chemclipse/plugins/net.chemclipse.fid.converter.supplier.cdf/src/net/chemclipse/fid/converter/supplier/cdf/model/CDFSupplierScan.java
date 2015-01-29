/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.fid.converter.supplier.cdf.model;

import net.chemclipse.fid.model.core.AbstractScanFID;

public class CDFSupplierScan extends AbstractScanFID implements ICDFSupplierScan {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -503693160228483226L;
	private float totalSignal = 0.0f;

	public CDFSupplierScan(int retentionTime, float totalSignal) {

		super();
		setRetentionTime(retentionTime);
		setTotalSignal(totalSignal);
	}

	public CDFSupplierScan(float totalSignal) {

		this.totalSignal = totalSignal;
	}

	@Override
	public float getTotalSignal() {

		return totalSignal;
	}

	@Override
	public void adjustTotalSignal(float totalSignal) {

		this.totalSignal = totalSignal;
	}

	@Override
	public void setTotalSignal(float totalSignal) {

		this.totalSignal = totalSignal;
	}
}
