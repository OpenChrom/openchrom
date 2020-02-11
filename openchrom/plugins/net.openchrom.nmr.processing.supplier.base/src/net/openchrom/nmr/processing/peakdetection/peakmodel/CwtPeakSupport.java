/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.peakdetection.peakmodel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ejml.simple.SimpleMatrix;

public class CwtPeakSupport {

	private Map<String, CwtPeak> cwtPeakList;

	public CwtPeakSupport(){
		cwtPeakList = new HashMap<String, CwtPeak>();
	}

	public Map<String, CwtPeak> getCwtPeakList() {

		return cwtPeakList;
	}

	private SimpleMatrix waveletCoefficients;
	private SimpleMatrix localMaxima;
	LinkedHashMap<String, List<Integer>> ridgeList;

	public SimpleMatrix getWaveletCoefficients() {
		return waveletCoefficients;
	}

	public void setWaveletCoefficients(SimpleMatrix waveletCoefficients) {
		this.waveletCoefficients = waveletCoefficients;
	}

	public SimpleMatrix getLocalMaxima() {
		return localMaxima;
	}

	public void setLocalMaxima(SimpleMatrix localMaxima) {
		this.localMaxima = localMaxima;
	}

	public LinkedHashMap<String, List<Integer>> getRidgeList() {
		return ridgeList;
	}

	public void setRidgeList(LinkedHashMap<String, List<Integer>> ridgeList) {
		this.ridgeList = ridgeList;
	}
}
