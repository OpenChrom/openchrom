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

/**
 * Class that tries to assign the Similarity between two peaks that are represented as
 * instances of XYData<Double>. The class
 * <ul>
 * <li>	<em>Normalizes</em> both XYData<Double> instances. </li>
 * <li> It then <em>integrates</em> them in a discrete manner. </li>
 * <li> The area of the peak-difference is calculated. </li>
 * <li> Finally, the Similarity is calculated via <em> Similarity = Area(PeakDifference)/Area(PeakWithBiggestArea)</em></li>
 * </ul>
 * @author administrator_marwin
 *
 */

public class PeakSimilarityDescriptor 
{
	XYData<Double> first;
	XYData<Double> second;
	// Data of first peak
	int startIndexOfFirst = -1;
	int endIndexOfFirst = -1;
	double maxOfFirst = -1;
	double areaOfFirst = -1.0;
	
	// Data of second peak
	int startIndexOfSecond = -1;
	int endIndexOfSecond = -1;
	double maxOfSecond = -1;
	double areaOfSecond = -1.0;
	
	// Area of difference peak
	int startIndexOfDifference = -1;
	int endIndexOfDifference = -1;
	double areaOfDifferencePeak = -1.0;
	
	
	public void setFirst(XYData<Double> first){
		this.first = first;
	}
	public void setSecond(XYData<Double> second){
		this.second = second;
	}
	public double getSimilarity()
	{
		PeakHelper peakHelper = new PeakHelper();
		peakHelper.setMZ(first);
		maxOfFirst = peakHelper.max();
		peakHelper.setThreshold(maxOfFirst/10.0);
		startIndexOfFirst = peakHelper.startIndex(maxOfFirst);
		endIndexOfFirst = peakHelper.endIndex(startIndexOfFirst);
		
		peakHelper.normalizeMz();
		areaOfFirst = peakHelper.peakArea(true, startIndexOfFirst, endIndexOfFirst);// ???
		first = peakHelper.getMz();
		// First now holds the first peak in a normalized form.
		// The area of the first peak is now stored in the variable areaOfFirst.
		
		// Now do the same thing with the second peak
		peakHelper = new PeakHelper();// clear peakHelper data members
		peakHelper.setMZ(second);
		maxOfSecond = peakHelper.max();
		peakHelper.setThreshold(maxOfSecond/10.0);
		startIndexOfSecond = peakHelper.startIndex(maxOfSecond);
		endIndexOfSecond = peakHelper.endIndex(startIndexOfSecond);
		
		peakHelper.normalizeMz();
		areaOfSecond = peakHelper.peakArea(true, startIndexOfSecond, endIndexOfSecond);// ???
		second = peakHelper.getMz();
		// Second now holds the second peak in a normalized form.
		// The area of the second peak is now stored in the variable areaOfSecond.
		
		// Now it is time to calculate the difference function, e.g. the variable Y, an instance of List<Double>
		// becomes y1i - y2j for all i,j in X.
		// It is first necessary to calculate the bounds via
		startIndexOfDifference = startIndexOfFirst < startIndexOfSecond ? startIndexOfFirst : startIndexOfSecond;
		endIndexOfDifference = endIndexOfFirst > endIndexOfSecond ? endIndexOfFirst : endIndexOfSecond;
		
		List<Double> X = new ArrayList<Double>( );
		List<Double> Y = new ArrayList<Double>( );
		for(int i = startIndexOfDifference; i < endIndexOfDifference; i++)
		{
			X.add(first.X.get(i));
			Y.add(Math.abs(first.Y.get(i)-second.Y.get(i)));
			System.out.println("Difference at index " + i + " is: " +Math.abs(first.Y.get(i)-second.Y.get(i)));
			
		}
		peakHelper = new PeakHelper();// clear data members in peak helper
		
		XYData<Double> differencePeak = new XYData<Double>(X,Y);
		peakHelper.setMZ(differencePeak);
		areaOfDifferencePeak = peakHelper.peakArea(true, 0, differencePeak.X.size());
		System.err.println("So the difference peak area is: " + areaOfDifferencePeak);
		if(areaOfFirst > areaOfSecond)
		{
			return (1.0-Math.abs(areaOfDifferencePeak/(areaOfFirst+areaOfSecond)));
		}
		else
		{
			return (1.0-Math.abs(areaOfDifferencePeak/(areaOfSecond+areaOfFirst)));
		}
	}
	
}
