/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - refactor m/z and abundance limit
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.io;

import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorIon;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorScanProxy;
import net.openchrom.msd.converter.supplier.mz5.model.VendorIon;

public class ReaderProxy implements IReaderProxy {

	private static final Logger logger = Logger.getLogger(ReaderProxy.class);

	@Override
	public void readMassSpectrum(double[] mzs, float[] spectrumIntensity, IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		try {
			double mz = 0;
			int start = scanMarker.getStart();
			int offset = scanMarker.getOffset();
			for(int o = start; o < offset; o++) {
				float intensity = spectrumIntensity[o];
				mz += mzs[o]; // first m/z value and then deltas
				IVendorIon ion = new VendorIon(mz, intensity);
				massSpectrum.addIon(ion);
			}
		} catch(OutOfMemoryError e) {
			logger.error(e);
		}
	}
}