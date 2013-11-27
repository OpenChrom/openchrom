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
import java.util.Arrays;
import java.util.List;

/**
 * A class that gets several <em>m/z curves as input</em>. From this input, the PeakDeconvolution
 * class calculates the similarity of pairwise different m/z curve instances.
 * These mutually calculated similarities are then used to decide, whether both peaks are part of 
 * one peak or not. 
 * @author administrator_marwin
 *
 */

public class PeakDeconvolution {
	// mzCurves is a collection of all mz curves to check
	List<XYData> mzCurves;
	double THRESHOLD = 0.5;// threshold when two mz curves belong to the same peak
	
	
	public void setMzCurves(List<XYData> mzCurves)
	{
		this.mzCurves = mzCurves;
	}
	
	//
	public List<List<XYData>> doDeconvolution()
	{
		// createPeakForEachMzCurve method works as if all mz curves would belong to their own peak
		List<List<XYData>> allMzAsPeak = createPeakForEachMzCurve(mzCurves);
		
		// The similarityDescriptor is used to check the similarity between two ms curves
		PeakSimilarityDescriptor similarityDesc = new PeakSimilarityDescriptor();
		
		// Iterate over all mz curves
		for(int i = 0; i < allMzAsPeak.size(); i++)
		{
			// Make sure that mz curve was not cleared already
			if(allMzAsPeak.get(i) != null){
				
			// now iterate over all other mz curves, e.g. mz curves with an index higher than the current one 
			for(int j = i+1; j < allMzAsPeak.size(); j++)
			{
				
				if(allMzAsPeak.get(j)!=null&&allMzAsPeak.get(i)!=null){
				// so the two mz Curves were not cleared until now
				similarityDesc = new PeakSimilarityDescriptor();
				similarityDesc.setFirst(allMzAsPeak.get(i).get(0));
				similarityDesc.setSecond(allMzAsPeak.get(j).get(0));
				
				double similarity = similarityDesc.getSimilarity();
				System.err.println("Similarity is: " + similarity);
				if(similarity>THRESHOLD)
				{
					System.out.println("Similarity is over Threshold!");
					List<XYData> newMZCurve = new ArrayList<XYData>( );
					for(XYData xyData: allMzAsPeak.get(i))
					{
						newMZCurve.add(xyData);
					}
					for(XYData xyData: allMzAsPeak.get(j))
					{
						newMZCurve.add(xyData);
					}
					
					allMzAsPeak.set(i,newMZCurve);
					allMzAsPeak.set(j,null);
				}
				}
			}
			}
		}
		return allMzAsPeak;
	}
	private List<List<XYData>> createPeakForEachMzCurve(List<XYData> xyDat)
	{
		List<List<XYData>> peaks = new ArrayList<List<XYData>>( );
		
		for(XYData mzCurve : xyDat)
		{
			List<XYData> toAdd = new ArrayList<XYData>( );
			toAdd.add(mzCurve);
			peaks.add(toAdd);
		}
		
		
		return peaks;
	}
	
	
	
	
	
	/**
	 * Test routine
	 * @param args
	 */
	
	public static void main(String[] args)
	{
		List<Double> X1 = new ArrayList<Double>( );
		List<Double> X2 = new ArrayList<Double>( );
		List<Double> X3 = new ArrayList<Double>( );
		
		List<Double> Y1 = new ArrayList<Double>( );
		List<Double> Y2 = new ArrayList<Double>( );
		List<Double> Y3 = new ArrayList<Double>( );
		
		//  Add a little tail ...  //
		X1.add(0.0);
		X2.add(0.0);
		X3.add(0.0);
		
		Y1.add(0.0);
		Y2.add(0.0);
		Y3.add(0.0);
		
		// Add the actual data //
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
		
		X2.add(1.0);
		X2.add(2.0);
		X2.add(3.0);
		X2.add(4.0);
		X2.add(5.0);
		X2.add(6.0);
		X2.add(7.0);
		X2.add(8.0);
		X2.add(9.0);
		X2.add(10.0);
		
		X3.add(1.0);
		X3.add(2.0);
		X3.add(3.0);
		X3.add(4.0);
		X3.add(5.0);
		X3.add(6.0);
		X3.add(7.0);
		X3.add(8.0);
		X3.add(9.0);
		X3.add(10.0);
		
		Y1.add(1.0);
		Y1.add(2.0);
		Y1.add(3.0);
		Y1.add(4.0);
		Y1.add(3.0);
		Y1.add(2.0);
		Y1.add(0.0);
		Y1.add(0.0);
		Y1.add(0.0);
		Y1.add(0.0);
		
		Y2.add(1.0);
		Y2.add(4.0);
		Y2.add(6.0);
		Y2.add(8.0);
		Y2.add(3.0);
		Y2.add(2.0);
		Y2.add(0.0);
		Y2.add(0.0);
		Y2.add(0.0);
		Y2.add(0.0);
		
		Y3.add(0.0);
		Y3.add(0.0);
		Y3.add(0.0);
		Y3.add(0.0);
		Y3.add(0.0);
		Y3.add(0.0);
		Y3.add(1.0);
		Y3.add(1.0);
		Y3.add(1.0);
		Y3.add(0.0);
		
		
		XYData mz1 = new XYData( X1, Y1 );
		XYData mz2 = new XYData( X2, Y2 );
		XYData mz3 = new XYData( X3, Y3 );
		
		PeakDeconvolution peakDec = new PeakDeconvolution();
		peakDec.setMzCurves(Arrays.asList(mz1,mz2,mz3));
		List<List<XYData>> result = peakDec.doDeconvolution();
		System.out.println(result.size());
		for(List<XYData> dat : result)
		{
			System.out.println("Non-Null instance was found!");
			System.out.println(dat);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
