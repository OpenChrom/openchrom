/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzmlb.io;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.internal.io.ReaderProxy;
import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;

import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ProxyReader {

	public void readMassSpectrum(IHDF5SimpleReader reader, IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException {

		IReaderProxy scanReaderProxy = new ReaderProxy();
		scanReaderProxy.readMassSpectrum(reader, scanMarker, massSpectrum, monitor);
	}
}
