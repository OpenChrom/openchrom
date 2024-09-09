/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.model;

import org.eclipse.chemclipse.msd.model.core.AbstractRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;

public class VendorScan extends AbstractRegularMassSpectrum implements IVendorScan {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -2866464076245364051L;

	public VendorScan() {

		super();
	}

	@Override
	public IVendorScan makeDeepCopy() throws CloneNotSupportedException {

		IVendorScan massSpectrum = (IVendorScan)super.clone();
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			IVendorIon mz5Ion = new VendorIon(ion.getIon(), ion.getAbundance());
			massSpectrum.addIon(mz5Ion);
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
}