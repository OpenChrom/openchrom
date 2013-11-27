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

import java.util.List;

/**
 * Class that finds <em>basic properties of peaks</em>, e.g. their <em>start</em>,<em>end</em> and <em>max</em>
 * properties, assuming that an m/z curve is given as an XYData<Double> instance.
 * 
 * => Start,End and Max can be calculated.
 * => m/z values can be normalized.
 * @author administrator_marwin
 *
 */

public class PeakHelper {
	private XYData<Double> mz;
	
	double threshold = -1.0;
	
	public XYData<Double> getMz()
	{
		return mz;
	}
	
	public double peakArea(boolean invariantDx,int peakStart,int peakEnd)
	{
		if(invariantDx){
		Pair<Double> p1 = mz.getPairAt(0);
		Pair<Double> p2 = mz.getPairAt(1);
		double area = 0.0;
		double dx = Math.abs(p1.x - p2.x);
		double y1 = 0.0; double y2 = 0.0;
		double min = 0.0; double max = 0.0;
		
		for( int i = peakStart; i < peakEnd-1; i++ )
		{
			y1 = mz.getPairAt(i).y;
			y2 = mz.getPairAt(i+1).y;
			if(y1 < y2)
			{
				min = y1; max = y2;
			}else{
				min = y2; max = y1;
			}
			area += min * dx;
			area += (max-min) * dx/ 2.0;
		}
			return area;
		}
		double area = 0.0;
		for( int i = peakStart; i < peakEnd; i++ )
		{
			Pair<Double> p1 = mz.getPairAt(i);
			Pair<Double> p2 = mz.getPairAt(1+1);
		
			double dx = p1.x - p2.x;
			double y1 = p1.y; double y2 = p2.y;
			double min = 0.0; double max = 0.0;
			
			if(y1 < y2)
			{
				min = y1; max = y2;
			}else{
				min = y2; max = y1;
			}
			area += min * dx;
			area += (max-min) * dx/ 2.0;
			
		}
		return area;
	}
	
	public void normalizeMz()
	{
		double max = max();
		for( int i = 0; i < mz.Y.size(); i++ )
		{
			mz.Y.set(i, mz.Y.get(i)/max);
		}
	}
	
	public void setThreshold(double threshold)
	{
		this.threshold = threshold;
	}
	
	
	public void setMZ(XYData<Double> mz)
	{
		this.mz = mz;
	}
	
	public double max()
	{
		double max = -1.0;
		double current = 0.0;
		for(int i = 0; i < mz.X.size(); i++)
		{
			if((current = mz.Y.get(i)) > max)
			{
				max = current;
			}
		}
		return max;
	}
	
	public int endIndex(int startIndex)
	{
		int endIndex = -1;
		if( startIndex == -1 )
		{
			System.err.println("No start value was found, so i cannot calculate an end!");
			return endIndex;
		}
		
		loop:
		for(int i=startIndex; i<mz.X.size();i++)
		{
			if(mz.Y.get(i) < threshold)
			{
				endIndex = i;
				break loop;
			}
		}
		return endIndex;
	}
	
	public int startIndex(double max)
	{
		int startIndex = -1;
		
		loop:
		for(int i =0; i< mz.X.size(); i++)
		{
			if((mz.Y.get(i)) >= threshold)
			{
				startIndex = i;
				break loop;
			}
		}
		return startIndex;
	}
	
}
