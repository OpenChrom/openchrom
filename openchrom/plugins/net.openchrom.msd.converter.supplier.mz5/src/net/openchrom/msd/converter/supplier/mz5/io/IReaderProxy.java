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
package net.openchrom.msd.converter.supplier.mz5.io;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mz5.io.support.IScanMarker;
import net.openchrom.msd.converter.supplier.mz5.model.IVendorScanProxy;

public interface IReaderProxy {

	void readMassSpectrum(double[] mzs, float[] spectrumIntensity, IScanMarker scanMarker, IVendorScanProxy massSpectrum, IProgressMonitor monitor) throws IOException;
}
