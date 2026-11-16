/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.model.core.AbstractRegularMassSpectrumProxy;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.io.IReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.ScanMarker;

public class VendorScanProxy extends AbstractRegularMassSpectrumProxy implements IVendorScanProxy {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 7247916814647121134L;
	private static final Logger logger = Logger.getLogger(VendorScanProxy.class);

	private final List<IScanMarker> scanMarkers = new ArrayList<>(1);
	private int combinedSpectra;
	private IReaderProxy readerProxy;

	public VendorScanProxy(IReaderProxy readerProxy, IProgressMonitor monitor) {

		super(monitor);
		this.readerProxy = readerProxy;
	}

	@Override
	public void addScanMarker(IScanMarker scanMarker) {

		combinedSpectra++;
		if(scanMarker.getLength() <= 0) {
			return;
		}
		/*
		 * Spectra of a frame are usually stored back to back,
		 * so a single block of the dataset covers all of them.
		 */
		if(!scanMarkers.isEmpty()) {
			IScanMarker lastMarker = scanMarkers.get(scanMarkers.size() - 1);
			if(lastMarker.getOffset() + lastMarker.getLength() == scanMarker.getOffset()) {
				lastMarker.setLength(lastMarker.getLength() + scanMarker.getLength());
				return;
			}
		}
		IScanMarker marker = new ScanMarker();
		marker.setOffset(scanMarker.getOffset());
		marker.setLength(scanMarker.getLength());
		scanMarkers.add(marker);
	}

	@Override
	public void importIons(IProgressMonitor monitor) {

		try {
			readerProxy.readMassSpectrum(scanMarkers, combinedSpectra > 1, this, monitor);
		} catch(IOException e) {
			logger.error(e);
		}
	}

	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. IMassSpectrum is a subtype of
	 * ISupplierMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public IVendorScan makeDeepCopy() throws CloneNotSupportedException {

		IVendorScanProxy massSpectrum = (IVendorScanProxy)super.clone();
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			IVendorIon vendorIon = new VendorIon(ion.getIon(), ion.getAbundance());
			massSpectrum.addIon(vendorIon);
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
}