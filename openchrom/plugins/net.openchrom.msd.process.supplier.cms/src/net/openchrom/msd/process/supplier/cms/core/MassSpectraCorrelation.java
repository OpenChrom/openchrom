/*******************************************************************************
 * Copyright (c) 2017, 2023 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - reduced compiler warnings
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class MassSpectraCorrelation {

	private double getCorrelation(ICalibratedVendorMassSpectrum testSpectrum, ICalibratedVendorLibraryMassSpectrum libSpectrum, double massTol) {

		int i, j;
		int libIonsCount = libSpectrum.getNumberOfIons();
		int scanIonsCount = testSpectrum.getIonMeasurements().size();
		if(0 >= libSpectrum.getNumberOfIons()) {
			return 0d;
		}
		if(0 >= testSpectrum.getIonMeasurements().size()) {
			return 0d;
		}
		i = 0; // index into testSpectrum measurements
		double correlation = 0d;
		for(j = 0; j < libIonsCount; j++) { // index into library spectrum ions
			while((i < scanIonsCount) && (testSpectrum.getIonMeasurement(i).massLess(libSpectrum.getIons().get(j).getIon(), massTol))) {
				i++; // skip over testSpectrum ion masses that are less than current library ion mass
			}
			if((i < scanIonsCount) && (testSpectrum.getIonMeasurement(i).massEqual(libSpectrum.getIons().get(j).getIon(), massTol))) { // test if library ion mass == current scan ion mass
				do { // mark ions having equal masses
					correlation += testSpectrum.getIonMeasurement(i).getSignal() * libSpectrum.getIons().get(j).getAbundance();
					i++;
				} while((i < scanIonsCount) && (testSpectrum.getIonMeasurement(i).massEqual(libSpectrum.getIons().get(j).getIon(), massTol)));
			} // if
		} // for
		correlation /= libSpectrum.get2Norm();
		correlation /= testSpectrum.get2Norm();
		return correlation;
	}

	public CorrelationResult correlate(ICalibratedVendorMassSpectrum scanSpectrum, IMassSpectra libSpectra, double massTol, IProgressMonitor monitor) {

		CorrelationResult result = new CorrelationResult(libSpectra.getList().size(), scanSpectrum);
		for(IScanMSD libSpectrum : libSpectra.getList()) {
			if(libSpectrum instanceof ICalibratedVendorLibraryMassSpectrum libraryMassSpectrum) {
				double correlation = getCorrelation(scanSpectrum, libraryMassSpectrum, massTol);
				result.addResult(correlation, libraryMassSpectrum);
			} // if
		} // for
		result.reverseSort();
		return result;
	}

	public CorrelationResults correlate(IMassSpectra testSpectra, IMassSpectra libSpectra, IProgressMonitor monitor) {

		// parameter testSpectra has all the scans we wish to correlate with library component spectra
		// parameter libSpectra has the set of library component cracking patterns we want to correlate with
		// generates a list of ions from the unknown mass spectrum (scanIons) having mass ~= at least one library component ion mass
		// and then generates a new, smaller, list of library ions (usedLibIons) having mass ~= at least one ion in the unknown mass spectrum
		double massTol = 0.2;
		CorrelationResults results = new CorrelationResults(testSpectra.getName());
		for(IScanMSD scan : testSpectra.getList()) {
			if(scan instanceof ICalibratedVendorMassSpectrum scanSpectrum) {
				//
				// CorrelationResult result = new CorrelationResult(libSpectra.getList().size(), scanSpectrum);
				// for(IScanMSD libSpectrum : libSpectra.getList()) {
				// if(libSpectrum instanceof ICalibratedVendorLibraryMassSpectrum) {
				// ICalibratedVendorLibraryMassSpectrum libraryMassSpectrum = (ICalibratedVendorLibraryMassSpectrum)libSpectrum;
				// double correlation = getCorrelation(scanSpectrum, libraryMassSpectrum, massTol);
				// result.addResult(correlation, libraryMassSpectrum);
				// } // if
				// System.out.println();
				// } // for
				//
				// results.addCorrelationResult(result);
				results.addCorrelationResult(correlate(scanSpectrum, libSpectra, massTol, monitor));
			} // if
		} // for
		return results;
	}
}
