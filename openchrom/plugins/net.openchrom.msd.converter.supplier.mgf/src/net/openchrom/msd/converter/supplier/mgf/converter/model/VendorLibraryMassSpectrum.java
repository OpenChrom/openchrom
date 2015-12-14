/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.model;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class VendorLibraryMassSpectrum extends AbstractRegularLibraryMassSpectrum implements IVendorLibraryMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -3213772728386428206L;
	private static final Logger logger = Logger.getLogger(VendorLibraryMassSpectrum.class);

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}

	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. IAmdisMassSpectrum is a subtype of
	 * ILibraryMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public IVendorLibraryMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		final IVendorLibraryMassSpectrum massSpectrum = (IVendorLibraryMassSpectrum)super.clone();
		IIon mz;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(final IIon ion : getIons()) {
			try {
				mz = new Ion(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(mz);
			} catch(final AbundanceLimitExceededException e) {
				logger.warn(e.getLocalizedMessage(), e);
			} catch(final IonLimitExceededException e) {
				logger.warn(e.getLocalizedMessage(), e);
			}
		}
		return massSpectrum;
	}
}
