/*******************************************************************************
 * Copyright (c) 2017, 2021 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.model;

import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractVendorMassSpectrumProxy;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.core.runtime.NullProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.internal.io.ProxyReader;
import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;

public class VendorScanProxy extends AbstractVendorMassSpectrumProxy implements IVendorScanProxy {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 6033054781323948805L;
	private static final Logger logger = Logger.getLogger(VendorScanProxy.class);
	//
	private double[] mzs;
	private float[] spectrumIntensity;
	private IScanMarker scanMarker;
	//
	public static final int MAX_IONS = 200000;
	public static final int MIN_RETENTION_TIME = 0;
	public static final int MAX_RETENTION_TIME = Integer.MAX_VALUE;

	public VendorScanProxy(double[] mzs, float[] spectrumIntensity, IScanMarker scanMarker) {

		this.mzs = mzs;
		this.spectrumIntensity = spectrumIntensity;
		this.scanMarker = scanMarker;
	}

	@Override
	public int getMaxPossibleIons() {

		return MAX_IONS;
	}

	@Override
	public int getMinPossibleRetentionTime() {

		return MIN_RETENTION_TIME;
	}

	@Override
	public int getMaxPossibleRetentionTime() {

		return MAX_RETENTION_TIME;
	}

	@Override
	public void importIons() {

		try {
			ProxyReader scanProxyReader = new ProxyReader();
			scanProxyReader.readMassSpectrum(mzs, spectrumIntensity, scanMarker, this, new NullProgressMonitor());
		} catch(IOException e) {
			logger.warn(e);
		}
	}

	// -------------------------------IMassSpectrumCloneable
	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. IMassSpectrum is a subtype of
	 * ISupplierMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public IVendorScan makeDeepCopy() throws CloneNotSupportedException {

		IVendorScanProxy massSpectrum = (IVendorScanProxy)super.clone();
		IVendorIon vendorIon;
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
				vendorIon = new VendorIon(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(vendorIon);
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
