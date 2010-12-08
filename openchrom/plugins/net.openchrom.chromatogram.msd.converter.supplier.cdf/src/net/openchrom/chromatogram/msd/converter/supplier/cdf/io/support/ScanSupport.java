/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
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
	private float[] maxMassFragment;
	private float[] minMassFragment;

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
		minMassFragment = new float[numberOfScans];
		maxMassFragment = new float[numberOfScans];
		for(int i = 1; i <= chromatogram.getNumberOfScans(); i++) {
			massSpectrum = chromatogram.getScan(i);
			addNumberOfMassFragments(i, massSpectrum.getNumberOfMassFragments());
			if(massSpectrum.getNumberOfMassFragments() > 0) {
				addMinMassFragment(i, massSpectrum.getLowestMZ().getMZ());
				addMaxMassFragment(i, massSpectrum.getHighestMZ().getMZ());
			} else {
				addMinMassFragment(i, 0);
				addMaxMassFragment(i, 0);
			}
		}
	}

	/**
	 * Adds the number of mass fragments for the given scan to the internal
	 * list.<br/>
	 * The information is used to calculate the scan index.
	 * 
	 * @param scan
	 * @param numberOfPeaks
	 */
	private void addNumberOfMassFragments(int scan, int numberOfMassFragments) {

		counter += numberOfMassFragments;
		scanIndex[scan] = counter;
		pointCount[scan] = numberOfMassFragments;
	}

	/**
	 * Adds the max mass fragment value to the given scan.
	 * 
	 * @param scan
	 * @param mz
	 */
	private void addMaxMassFragment(int scan, float mz) {

		maxMassFragment[scan] = mz;
	}

	/**
	 * Adds the min mass fragment value to the given scan.
	 * 
	 * @param scan
	 * @param mz
	 */
	private void addMinMassFragment(int scan, float mz) {

		minMassFragment[scan] = mz;
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
	public float getMinMassFragment(int scan) {

		return minMassFragment[scan];
	}

	@Override
	public float getMaxMassFragment(int scan) {

		return maxMassFragment[scan];
	}
	// --------------------------------------IScanSupport
}
