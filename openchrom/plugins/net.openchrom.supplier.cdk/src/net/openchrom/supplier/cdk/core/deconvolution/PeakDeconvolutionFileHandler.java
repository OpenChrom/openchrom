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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.hp.hpl.jena.graph.query.Matcher;

/**
 * Utility class for reading files with comma separated values from disk and 
 * gives them back as XYData<Double> instances in an easy-to-use 
 * List<XYData<Double>> form, so that it can be used directly by the other
 * Deconvolution classes.
 * @author administrator_marwin
 *
 */

public class PeakDeconvolutionFileHandler {
	private String dataString;
	
	
	public List<XYData<Double>> getPeakData(int modulus)
	{
		Pattern decimal = Pattern.compile("[0-9]+\\.?[0-9]*");
		String substitutionString = "";
		for( int i = 0; i < dataString.length(); i++ )
		{
			String currentLookUp = dataString.substring(i,i+1);
			if (currentLookUp.equals("\n")){
				substitutionString += " ";
			}else
			{
				substitutionString += currentLookUp;
			}
		}
		dataString = substitutionString;
		
		java.util.regex.Matcher matcher = decimal.matcher(dataString);
		List<String> results = new ArrayList<String>( );
		while(matcher.find())
		{
			results.add(dataString.substring(matcher.start(),matcher.end()));
		}
		
		for(String string : results)
		{
			System.out.println(string);
		}
		
		List<Double> RT = new ArrayList<Double>( );
		List<List<Double>> yData = new ArrayList<List<Double>>( );
		for(int i = 0; i < modulus-1; i++ )
		{
			yData.add(new ArrayList<Double>());
			
		}
		
		
		
		for( int i = 0; i < results.size(); i++ )
		{
			if(i == 0 || modulus % i == 0)
			{
				RT.add(Double.parseDouble(results.get(i)));
			}
			for( int j = 1; j < modulus-1; j++ )
			{
				if( i!= 0 && modulus % i == j )
				{
					yData.get(j-1).add(Double.parseDouble(results.get(i)));
				}
			}
		}
		
		List<XYData<Double>> result = new ArrayList<XYData<Double>>( );
		
		for( int q = 0; q < yData.size(); q++ )
		{
			
			result.add(new XYData<Double>(RT,yData.get(q)));
		}
		
		return result;
	}
	
	
	
	
	
	
	/**
	 * The read method reads in the File as a String that is then stored in dataString
	 * so it can later be processed.
	 * @param file
	 */
	private void read(String fileName)
	{
		read(new File(fileName));
	}
	private void read(File file)
	{
		BufferedReader in;
		try {
			in = new BufferedReader(
					new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String str;
			
				while ((str = in.readLine()) != null) {
				    dataString += str;
				}
		
		} catch(UnsupportedEncodingException e) {
			System.err.println(e);
		} catch(FileNotFoundException e) {
			System.err.println(e);
		}
		catch(IOException e){
			System.err.println(e);
		}
	}
	
	public List<XYData<Double>> readOutXYData(String fileString,int modulus)
	{
		read(fileString);
		return getPeakData(modulus);
	}
	
	public static void main(String[] args)
	{
		PeakDeconvolutionFileHandler handler = new PeakDeconvolutionFileHandler();
		String fileString = "/home/administrator_marwin/Dokumente/test/deconvolutionReadingTest.txt";
		int modulus =4;
		List<XYData<Double>> result = handler.readOutXYData(fileString, modulus);
		for(XYData<Double> xyDat : result)
		{
			for(int i= 0; i< xyDat.X.size(); i++)
			{
				double x = xyDat.X.get(i);
				//double y = xyDat.Y.get(i);
				System.out.println("[ x: " + x + " , y: "+ " ]");
			}
			System.err.println(6%3);
		}
	}
	
	
	
	
	
}
