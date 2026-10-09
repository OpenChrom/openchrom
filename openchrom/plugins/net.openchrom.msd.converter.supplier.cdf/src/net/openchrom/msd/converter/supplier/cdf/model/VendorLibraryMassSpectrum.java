/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.cdf.model;

import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;

public class VendorLibraryMassSpectrum extends AbstractRegularLibraryMassSpectrum implements IVendorLibraryMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 6391337399259985051L;

	@Override
	public IRegularLibraryMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		IRegularLibraryMassSpectrum massSpectrum = (IRegularLibraryMassSpectrum)super.clone();
		/*
		 * The abstract super class does not know the vendor specific ion type,
		 * hence the ion list has to be filled again.
		 */
		for(IIon ion : getIons()) {
			massSpectrum.addIon(new VendorIon(ion.getIon(), ion.getAbundance()));
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
}
