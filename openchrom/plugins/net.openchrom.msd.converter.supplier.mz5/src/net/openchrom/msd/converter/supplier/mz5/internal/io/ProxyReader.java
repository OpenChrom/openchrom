/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.internal.io;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.io.IReaderProxy;
import net.openchrom.msd.converter.supplier.mz5.io.ReaderProxy;
import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorScanProxy;

public class ProxyReader {

	public void readMassSpectrum(double[] mzs, float[] spectrumIntensity, IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		IReaderProxy scanReaderProxy = new ReaderProxy();
		scanReaderProxy.readMassSpectrum(mzs, spectrumIntensity, scanMarker, massSpectrum, monitor);
	}
}
