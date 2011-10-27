/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support;

import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;

public class ScanSupport implements IScanSupport {

	private int counter;
	private int[] scanIndex;
	private int[] pointCount;
	private float[] maxIon;
	private float[] minIon;

	public ScanSupport(IChromatogram chromatogram) {

		IMassSpectrum massSpectrum;
		int numberOfScans = chromatogram.getNumberOfScans();
		counter = 0;
		/*
		 * Increment the number of scans to have space at the first position for
		 * the scan index which is always zero.
		 */
		numberOfScans++;
		scanIndex = new int[numberOfScans];
		pointCount = new int[numberOfScans];
		minIon = new float[numberOfScans];
		maxIon = new float[numberOfScans];
		for(int i = 1; i <= chromatogram.getNumberOfScans(); i++) {
			massSpectrum = chromatogram.getScan(i);
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
	private void addMaxIon(int scan, float ion) {

		maxIon[scan] = ion;
	}

	/**
	 * Adds the min ion value to the given scan.
	 * 
	 * @param scan
	 * @param ion
	 */
	private void addMinIon(int scan, float ion) {

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
	public float getMinIon(int scan) {

		return minIon[scan];
	}

	@Override
	public float getMaxIon(int scan) {

		return maxIon[scan];
	}
	// --------------------------------------IScanSupport
}
