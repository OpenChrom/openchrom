/*******************************************************************************
 * Copyright (c) 2013 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.model;

import net.openchrom.chromatogram.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.core.AbstractSupplierScanMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.openchrom.logging.core.Logger;

public class CDFMassSpectrum extends AbstractSupplierScanMassSpectrum implements ICDFMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 251851545112617476L;
	private static final Logger logger = Logger.getLogger(CDFMassSpectrum.class);
	/**
	 * MAX_IONS The total amount of ions to be stored in the
	 * cdf chromatogram.<br/>
	 * It does not mean, that ion 65535 is the upper bound, but only 65535 mass
	 * fragments can be stored in a mass spectrum.
	 */
	public static final int MAX_IONS = 65535;
	public static final int MIN_RETENTION_TIME = 0;
	public static final int MAX_RETENTION_TIME = Integer.MAX_VALUE;

	public CDFMassSpectrum() {

		super();
	}

	@Override
	public int getMaxPossibleIons() {

		return MAX_IONS;
	}

	@Override
	public int getMaxPossibleRetentionTime() {

		return MAX_RETENTION_TIME;
	}

	@Override
	public int getMinPossibleRetentionTime() {

		return MIN_RETENTION_TIME;
	}

	// -------------------------------IMassSpectrumCloneable
	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. ICDFMassSpectrum is a subtype of
	 * ISupplierMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public ICDFMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		ICDFMassSpectrum massSpectrum = (ICDFMassSpectrum)super.clone();
		ICDFIon cdfIon;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			try {
				cdfIon = new CDFIon(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(cdfIon);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
	// -------------------------------IMassSpectrumCloneable
}
