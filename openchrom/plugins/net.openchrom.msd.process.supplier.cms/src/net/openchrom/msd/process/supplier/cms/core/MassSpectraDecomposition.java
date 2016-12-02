/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import org.eclipse.chemclipse.chromatogram.msd.identifier.massspectrum.MassSpectrumIdentifier;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IMassSpectrumTarget;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraDecomposition {

	private static final String MS_IDENTIFIER_ID = "org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.file.massSpectrum";

	public void decompose(IMassSpectra massSpectra, IProgressMonitor monitor) {

		for(IScanMSD massSpectrum : massSpectra.getList()) {
			System.out.println("SCAN: " + massSpectrum.getTotalSignal() + "\t" + massSpectrum.getNumberOfIons());
			if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
				IRegularLibraryMassSpectrum libraryMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
				System.out.println("\t" + libraryMassSpectrum.getLibraryInformation().getName());
			}
			/*
			 * Use the scan identifier:
			 * org.eclipse.chemclipse.chromatogram.msd.identifier.supplier.file
			 * Works only properly if a library has been set.
			 */
			MassSpectrumIdentifier.identify(massSpectrum, MS_IDENTIFIER_ID, monitor);
			for(IMassSpectrumTarget massSpectrumTarget : massSpectrum.getTargets()) {
				System.out.println("\t" + massSpectrumTarget.getLibraryInformation().getName());
			}
		}
	}
}