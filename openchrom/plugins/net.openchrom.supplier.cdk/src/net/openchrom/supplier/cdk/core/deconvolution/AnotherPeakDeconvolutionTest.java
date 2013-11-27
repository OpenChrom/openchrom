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
 * Test class for the Deconvolution algorithm.
 * Uses simple List<Double> arguments for the m/z data.
 * @author administrator_marwin
 *
 */

public class AnotherPeakDeconvolutionTest {
	
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
		PeakSimilarityDescriptor desc = new PeakSimilarityDescriptor();
		desc.setFirst(mz1); desc.setSecond(mz2);
		System.out.println(desc.getSimilarity());
		desc = new PeakSimilarityDescriptor();
		desc.setFirst(mz2); desc.setSecond(mz3);
		System.out.println(desc.getSimilarity());
		
	}
}
