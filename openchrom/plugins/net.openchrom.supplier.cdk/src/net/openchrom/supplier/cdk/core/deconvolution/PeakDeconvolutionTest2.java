/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core.deconvolution;

import java.util.ArrayList;
import java.util.List;

public class PeakDeconvolutionTest2 {

	public static void main(String[] args) {

		List<Double> X1 = new ArrayList<Double>();
		X1.add(1.0);
		X1.add(2.0);
		X1.add(3.0);
		X1.add(4.0);
		X1.add(5.0);
		X1.add(6.0);
		X1.add(7.0);
		X1.add(8.0);
		X1.add(9.0);
		X1.add(10.0);
		X1.add(11.0);
		X1.add(12.0);
		X1.add(13.0);
		X1.add(14.0);
		X1.add(15.0);
		List<Double> Y1 = new ArrayList<Double>();
		Y1.add(0.0);// 1
		Y1.add(1.0);
		Y1.add(1.0);
		Y1.add(3.0);
		Y1.add(4.0);// 5
		Y1.add(5.0);
		Y1.add(6.0);
		Y1.add(7.0);
		Y1.add(5.0);
		Y1.add(4.0);// 10
		Y1.add(3.0);
		Y1.add(2.0);
		Y1.add(1.0);
		Y1.add(1.0);
		Y1.add(0.0);// 15
		List<Double> X2 = new ArrayList<Double>();
		X2.add(1.0);// 1
		X2.add(2.0);
		X2.add(3.0);
		X2.add(4.0);
		X2.add(5.0);// 5
		X2.add(6.0);
		X2.add(7.0);
		X2.add(8.0);
		X2.add(9.0);
		X2.add(10.0);// 10
		X2.add(11.0);
		X2.add(12.0);
		X2.add(13.0);
		X2.add(14.0);
		X2.add(15.0);// 15
		List<Double> Y2 = new ArrayList<Double>();
		Y2.add(0.0);// 1
		Y2.add(1.0);
		Y2.add(1.0);
		Y2.add(3.0);
		Y2.add(4.0);// 5
		Y2.add(5.0);
		Y2.add(6.0);
		Y2.add(7.0);
		Y2.add(5.0);
		Y2.add(4.0);// 10
		Y2.add(3.0);
		Y2.add(2.0);
		Y2.add(5.0);
		Y2.add(0.0);
		Y2.add(0.0);// 15
		XYData<Double> peak1 = new XYData<Double>(X1, Y1);
		XYData<Double> peak2 = new XYData<Double>(X2, Y2);
		PeakSimilarityDescriptor desc = new PeakSimilarityDescriptor();
		PeakHelper helper = new PeakHelper();
		helper.setMZ(peak2);
		double secondMax = helper.max();
		System.out.println("secondMax: " + secondMax);
		helper.setThreshold(secondMax / 10.0);
		int peakStart = helper.startIndex(secondMax);
		System.out.println("peakStart: " + peakStart);
		int peakEnd = helper.endIndex(peakStart);
		System.out.println("peakEnd: " + peakEnd);
		helper.normalizeMz();
		System.out.println("area of peak2 : " + helper.peakArea(true, peakStart, peakEnd));
		helper = new PeakHelper();
		helper.setMZ(peak1);
		double firstMax = helper.max();
		helper.setThreshold(firstMax / 10.0);
		peakStart = helper.startIndex(firstMax);
		peakEnd = helper.endIndex(peakStart);
		helper.normalizeMz();
		System.out.println("area of peak1 : " + helper.peakArea(true, peakStart, peakEnd));
		desc.setFirst(peak1);
		desc.setSecond(peak2);
		System.err.println(desc.getSimilarity());
	}
}
