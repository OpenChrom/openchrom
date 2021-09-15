/*******************************************************************************
 * Copyright (c) 2017, 2021 Walter Whitlock, Philip Wenig.
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

import java.util.Arrays;
import java.util.Collections;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class CorrelationResult {

	private class CorrelationItem implements Comparable<CorrelationItem> {

		private double correlationValue;
		private ICalibratedVendorLibraryMassSpectrum library;
		// private String libName; // temporary for devlopment

		public double getCorrelationValue() {

			return correlationValue;
		}

		@Override
		public int compareTo(CorrelationItem item) { // sort

			if(this.correlationValue > item.getCorrelationValue()) {
				return 1;
			} else if(this.correlationValue < item.getCorrelationValue()) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	private int resultsCount;
	private CorrelationItem results[];

	CorrelationResult(int size, ICalibratedVendorMassSpectrum spectrum) {

		resultsCount = 0;
		results = new CorrelationItem[size];
	}

	public void addResult(double value, ICalibratedVendorLibraryMassSpectrum libSpec) {

		CorrelationItem result = new CorrelationItem();
		result.correlationValue = value;
		result.library = libSpec;
		// result.libName = libSpec.getLibraryInformation().getName(); // temporary
		results[resultsCount] = result;
		resultsCount++;
	}

	public double getCorrelationValue(int index) {

		if((null != results) && (null != results[index])) {
			return results[index].correlationValue;
		} else {
			return 0d;
		}
	}

	public boolean libraryIsSelected(int index) {

		if((null != results) && (null != results[index]) && (null != results[index].library)) {
			return results[index].library.isSelected();
		} else {
			return false;
		}
	}

	public String getCorrelationLibName(int index) {

		if((null != results) && (null != results[index]) && (null != results[index].library) && (null != results[index].library.getLibraryInformation()) && (null != results[index].library.getLibraryInformation().getName())) {
			return results[index].library.getLibraryInformation().getName();
		} else {
			return null;
		}
	}

	public int getResultsCount() {

		return resultsCount;
	}

	public void reverseSort() {

		Arrays.sort(this.results, 0, resultsCount, Collections.reverseOrder());
	}
}
