/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.converter.supplier.cdf.io.support;

import net.chemclipse.chromatogram.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.chemclipse.chromatogram.msd.converter.supplier.cdf.model.CDFMassSpectrum;

public interface ICDFChromatogramArrayReader extends IAbstractCDFChromatogramArrayReader {

	/**
	 * Returns a valid mass spectrum of the given scan.
	 * 
	 * @param scan
	 * @param precision
	 * @return CDFMassSpectrum
	 * @throws NoSuchScanStored
	 */
	public CDFMassSpectrum getMassSpectrum(int scan, int precision) throws NoSuchScanStored;
}
