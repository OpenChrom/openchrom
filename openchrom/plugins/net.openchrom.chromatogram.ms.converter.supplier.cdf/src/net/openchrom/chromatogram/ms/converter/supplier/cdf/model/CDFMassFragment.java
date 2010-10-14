/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
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
package net.openchrom.chromatogram.ms.converter.supplier.cdf.model;

import net.openchrom.chromatogram.ms.model.core.AbstractSupplierMassFragment;
import net.openchrom.chromatogram.ms.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.ms.model.exceptions.MZLimitExceededException;

public class CDFMassFragment extends AbstractSupplierMassFragment implements ICDFMassFragment {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -1760153607293050017L;
	// A max value for abundance
	public static final float MIN_ABUNDANCE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	// A max value for m/z
	public static final float MIN_MZ = 1.0f;
	public static final float MAX_MZ = 65535.0f;

	public CDFMassFragment(float mz) throws MZLimitExceededException {

		super(mz);
	}

	public CDFMassFragment(float mz, boolean ignoreAbundanceLimit) throws MZLimitExceededException {

		super(mz);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public CDFMassFragment(float mz, float abundance) throws AbundanceLimitExceededException, MZLimitExceededException {

		super(mz, abundance);
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
	public float getMinPossibleMZValue() {

		return MIN_MZ;
	}

	@Override
	public float getMaxPossibleMZValue() {

		return MAX_MZ;
	}
}
