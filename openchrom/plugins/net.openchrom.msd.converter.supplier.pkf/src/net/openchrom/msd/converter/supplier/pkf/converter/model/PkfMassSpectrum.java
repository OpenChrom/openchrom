/*******************************************************************************
 * Copyright (c) 2015, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter.model;

import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class PkfMassSpectrum extends AbstractRegularLibraryMassSpectrum implements IPkfMassSpectrum { // TODO rename

	private static final long serialVersionUID = -6114659189119981686L;

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}

	@Override
	public PkfMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		final PkfMassSpectrum massSpectrum = (PkfMassSpectrum)super.clone();
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(final IIon ion : getIons()) {
			IIon mz = new Ion(ion.getIon(), ion.getAbundance());
			massSpectrum.addIon(mz);
		}
		return massSpectrum;
	}
}