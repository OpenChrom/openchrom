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
package net.openchrom.msd.converter.supplier.mzdb.model;

import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.core.IIonTransition;

public class VendorIon extends AbstractIon implements IVendorIon {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 7048549368402063030L;
	// A max value for abundance
	public static final float MIN_ABUNDANCE = Float.MIN_VALUE;
	public static final float MAX_ABUNDANCE = Float.MAX_VALUE;
	// A max value for m/z
	public static final double MIN_ION = 1.0d;
	public static final double MAX_ION = 65535.0d;

	public VendorIon(double ion, float abundance) {

		super(ion, abundance);
	}

	public VendorIon(double ion, float abundance, IIonTransition ionTransition) {

		super(ion, abundance, ionTransition);
	}
}