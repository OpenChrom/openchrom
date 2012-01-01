/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.model;

import net.openchrom.chromatogram.msd.model.core.AbstractSupplierIon;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;

public class CDFIon extends AbstractSupplierIon implements ICDFIon {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -1760153607293050017L;
	// A max value for abundance
	public static final float MIN_ABUNDANCE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	// A max value for ion
	public static final float MIN_Ion = 1.0f;
	public static final float MAX_Ion = 65535.0f;

	public CDFIon(float ion) throws IonLimitExceededException {

		super(ion);
	}

	public CDFIon(float ion, boolean ignoreAbundanceLimit) throws IonLimitExceededException {

		super(ion);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public CDFIon(float ion, float abundance) throws AbundanceLimitExceededException, IonLimitExceededException {

		super(ion, abundance);
	}

	@Override
	public float getMinPossibleAbundanceValue() {

		return MIN_ABUNDANCE;
	}

	@Override
	public float getMaxPossibleAbundanceValue() {

		return MAX_ABUNDANCE;
	}

	@Override
	public float getMinPossibleIonValue() {

		return MIN_Ion;
	}

	@Override
	public float getMaxPossibleIonValue() {

		return MAX_Ion;
	}
}
