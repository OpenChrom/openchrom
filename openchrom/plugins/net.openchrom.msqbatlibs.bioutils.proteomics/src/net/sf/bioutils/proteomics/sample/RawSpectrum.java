/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.sample;

import java.util.Arrays;
import net.sf.kerner.utils.UtilArray;

public class RawSpectrum implements Comparable<RawSpectrum> {

	private final String id;
	private final int index;
	private int msLevel;
	private final Number[] numbersInt;
	private final Number[] numbersMz;
	private int precursorChargeState;
	private double precursorInt;
	private double precursorMz;
	private RawSpectrum precursorSpectrum;

	public RawSpectrum(String id, int index, Number[] numbersMz, Number[] numbersInt) {
		super();
		this.id = id;
		this.index = index;
		this.numbersMz = numbersMz;
		this.numbersInt = numbersInt;
		if((numbersInt != null && numbersMz != null) && (numbersInt.length != numbersMz.length)) {
			throw new IllegalArgumentException("Array length differs " + numbersInt.length + " " + numbersMz.length);
		}
	}

	public synchronized int compareTo(RawSpectrum o) {

		return Integer.valueOf(getIndex()).compareTo(o.getIndex());
	}

	public synchronized boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof RawSpectrum)) {
			return false;
		}
		RawSpectrum other = (RawSpectrum)obj;
		if(index != other.index) {
			return false;
		}
		if(!Arrays.equals(numbersInt, other.numbersInt)) {
			return false;
		}
		if(!Arrays.equals(numbersMz, other.numbersMz)) {
			return false;
		}
		return true;
	}

	public synchronized String getId() {

		return id;
	}

	public synchronized int getIndex() {

		return index;
	}

	public synchronized double[] getInt() {

		return UtilArray.toPrimitive(getNumbersInt());
	}

	public synchronized double getIntSum() {

		double result = 0;
		if(numbersInt != null) {
			for(Number n : numbersInt) {
				result += n.doubleValue();
			}
		}
		return result;
	}

	public synchronized int getLength() {

		return numbersInt.length;
	}

	public synchronized int getMsLevel() {

		return msLevel;
	}

	public synchronized void setMsLevel(int msLevel) {

		this.msLevel = msLevel;
	}

	public synchronized double[] getMz() {

		return UtilArray.toPrimitive(getNumbersMz());
	}

	public synchronized Number[] getNumbersInt() {

		return numbersInt;
	}

	public synchronized Number[] getNumbersMz() {

		return numbersMz;
	}

	public synchronized int getPrecursorChargeState() {

		return precursorChargeState;
	}

	public synchronized void setPrecursorChargeState(int precursorChargeState) {

		this.precursorChargeState = precursorChargeState;
	}

	public synchronized double getPrecursorInt() {

		return precursorInt;
	}

	public synchronized void setPrecursorInt(double precursorInt) {

		this.precursorInt = precursorInt;
	}

	public synchronized double getPrecursorMz() {

		return precursorMz;
	}

	public synchronized void setPrecursorMz(double precursorMz) {

		this.precursorMz = precursorMz;
	}

	public synchronized RawSpectrum getPrecursorSpectrum() {

		return precursorSpectrum;
	}

	public synchronized void setPrecursorSpectrum(RawSpectrum precursorSpectrum) {

		this.precursorSpectrum = precursorSpectrum;
	}

	public synchronized int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + Arrays.hashCode(numbersInt);
		result = prime * result + Arrays.hashCode(numbersMz);
		return result;
	}

	public synchronized String toString() {

		int length = 0;
		if(numbersInt != null) {
			length = numbersInt.length;
		}
		return "Index:" + getIndex() + ", signals:" + length;
	}
}
