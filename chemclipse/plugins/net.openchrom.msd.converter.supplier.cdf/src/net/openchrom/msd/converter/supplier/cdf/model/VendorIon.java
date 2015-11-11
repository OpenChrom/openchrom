/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.model;

import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractScanIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;

public class VendorIon extends AbstractScanIon implements IVendorIon {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -1760153607293050017L;
	// A max value for abundance
	public static final float LOWEST_INVALID_ABUNDANCE_VALUE = 0.0f;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	// A max value for ion
	public static final double MIN_ION = 1.0d;
	public static final double MAX_ION = 65535.0d;

	public VendorIon(float ion) throws IonLimitExceededException {

		super(ion);
	}

	public VendorIon(double ion, boolean ignoreAbundanceLimit) throws IonLimitExceededException {

		super(ion);
		setIgnoreAbundanceLimit(ignoreAbundanceLimit);
	}

	public VendorIon(double ion, float abundance) throws AbundanceLimitExceededException, IonLimitExceededException {

		super(ion, abundance);
	}

	@Override
	public float getLowestInvalidAbundanceValue() {

		return LOWEST_INVALID_ABUNDANCE_VALUE;
	}

	@Override
	public float getMaxPossibleAbundanceValue() {

		return MAX_ABUNDANCE;
	}

	@Override
	public double getMinPossibleIonValue() {

		return MIN_ION;
	}

	@Override
	public double getMaxPossibleIonValue() {

		return MAX_ION;
	}
}
