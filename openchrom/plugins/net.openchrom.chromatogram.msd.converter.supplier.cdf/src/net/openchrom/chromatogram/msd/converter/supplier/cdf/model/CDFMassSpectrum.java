/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
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

import net.openchrom.chromatogram.msd.model.core.AbstractSupplierMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.IMassFragment;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.MZLimitExceededException;
import net.openchrom.logging.core.Logger;

public class CDFMassSpectrum extends AbstractSupplierMassSpectrum implements ICDFMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 251851545112617476L;
	private static final Logger logger = Logger.getLogger(CDFMassSpectrum.class);
	/**
	 * MAX_MASSFRAGMENTS The total amount of mass fragments to be stored in the
	 * cdf chromatogram.<br/>
	 * It does not mean, that m/z 65535 is the upper bound, but only 65535 mass
	 * fragments can be stored in a mass spectrum.
	 */
	public static final int MAX_MASSFRAGMENTS = 65535;
	public static final int MIN_RETENTION_TIME = 0;
	public static final int MAX_RETENTION_TIME = Integer.MAX_VALUE;

	public CDFMassSpectrum() {

		super();
	}

	@Override
	public int getMaxPossibleMassFragments() {

		return MAX_MASSFRAGMENTS;
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
		ICDFMassFragment massFragment;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * mass fragments in the mass fragment list need not to be removed via
		 * removeAllMassFragments as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of mass fragment.<br/>
		 * Make a deep copy of all mass fragments.
		 */
		for(IMassFragment mf : getMassFragments()) {
			try {
				massFragment = new CDFMassFragment(mf.getMZ(), mf.getAbundance());
				massSpectrum.addMassFragment(massFragment);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(MZLimitExceededException e) {
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
