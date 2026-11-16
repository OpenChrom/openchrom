/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mzmlb.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mzmlb.model.IVendorScanProxy;

public interface IReaderProxy {

	void setMzDataset(String mzDataset);

	void setIntensityDataset(String intensityDataset);

	/**
	 * Reads the ions at the given locations. If combine is true, the
	 * intensities of identical m/z values are summed up.
	 */
	void readMassSpectrum(List<IScanMarker> scanMarkers, boolean combine, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException;
}
