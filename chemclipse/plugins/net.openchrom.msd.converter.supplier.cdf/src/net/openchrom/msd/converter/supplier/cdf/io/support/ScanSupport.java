/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

public class ScanSupport implements IScanSupport {

	private int counter;
	private int[] scanIndex;
	private int[] pointCount;
	private double[] maxIon;
	private double[] minIon;

	public ScanSupport(IChromatogramMSD chromatogram) {

		IScanMSD massSpectrum;
		int numberOfScans = chromatogram.getNumberOfScans();
		counter = 0;
		/*
		 * Increment the number of scans to have space at the first position for
		 * the scan index which is always zero.
		 */
		numberOfScans++;
		scanIndex = new int[numberOfScans];
		pointCount = new int[numberOfScans];
		minIon = new double[numberOfScans];
		maxIon = new double[numberOfScans];
		for(int i = 1; i <= chromatogram.getNumberOfScans(); i++) {
			massSpectrum = chromatogram.getSupplierScan(i);
			addNumberOfIons(i, massSpectrum.getNumberOfIons());
			if(massSpectrum.getNumberOfIons() > 0) {
				addMinIon(i, massSpectrum.getLowestIon().getIon());
				addMaxIon(i, massSpectrum.getHighestIon().getIon());
			} else {
				addMinIon(i, 0);
				addMaxIon(i, 0);
			}
		}
	}

	/**
	 * Adds the number of ions for the given scan to the internal
	 * list.<br/>
	 * The information is used to calculate the scan index.
	 * 
	 * @param scan
	 * @param numberOfPeaks
	 */
	private void addNumberOfIons(int scan, int numberOfIons) {

		counter += numberOfIons;
		scanIndex[scan] = counter;
		pointCount[scan] = numberOfIons;
	}

	/**
	 * Adds the max ion value to the given scan.
	 * 
	 * @param scan
	 * @param ion
	 */
	private void addMaxIon(int scan, double ion) {

		maxIon[scan] = ion;
	}

	/**
	 * Adds the min ion value to the given scan.
	 * 
	 * @param scan
	 * @param ion
	 */
	private void addMinIon(int scan, double ion) {

		minIon[scan] = ion;
	}

	// --------------------------------------IScanSupport
	@Override
	public int getScanIndex(int scan) {

		// TODO ein scan startet immer mit eins
		return scanIndex[--scan];
	}

	@Override
	public int getPointCount(int scan) {

		return pointCount[scan];
	}

	@Override
	public double getMinIon(int scan) {

		return minIon[scan];
	}

	@Override
	public double getMaxIon(int scan) {

		return maxIon[scan];
	}
	// --------------------------------------IScanSupport
}
