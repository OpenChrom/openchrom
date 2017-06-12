/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;

public class SeriesConverter {

	public static final String LINE_SERIES_1 = "LineSeries1";

	public static ISeriesData getSeriesXY(String fileName) {

		int size = getNumberOfLines(fileName);
		double[] xSeries = new double[size];
		double[] ySeries = new double[size];
		//
		BufferedReader bufferedReader = null;
		try {
			String line;
			int i = 0;
			bufferedReader = new BufferedReader(new InputStreamReader(SeriesConverter.class.getResourceAsStream(fileName)));
			while((line = bufferedReader.readLine()) != null) {
				String[] values = line.split("\t");
				xSeries[i] = Double.parseDouble(values[0].trim());
				ySeries[i] = Double.parseDouble(values[1].trim());
				i++;
			}
		} catch(Exception e) {
			//
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch(IOException e) {
					//
				}
			}
		}
		//
		ISeriesData seriesData = new SeriesData(xSeries, ySeries, fileName);
		return seriesData;
	}

	public static ISeriesData getSeriesFromY(String fileName) {

		int size = getNumberOfLines(fileName);
		double[] ySeries = new double[size];
		//
		BufferedReader bufferedReader = null;
		try {
			String line;
			int i = 0;
			bufferedReader = new BufferedReader(new InputStreamReader(SeriesConverter.class.getResourceAsStream(fileName)));
			while((line = bufferedReader.readLine()) != null) {
				ySeries[i++] = Double.parseDouble(line.trim());
			}
		} catch(Exception e) {
			//
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch(IOException e) {
					//
				}
			}
		}
		//
		ISeriesData seriesData = new SeriesData(ySeries, fileName);
		return seriesData;
	}

	public static List<ISeriesData> getSeriesScatter(String fileName) {

		List<ISeriesData> scatterSeriesList = new ArrayList<ISeriesData>();
		//
		BufferedReader bufferedReader = null;
		try {
			String line;
			bufferedReader = new BufferedReader(new InputStreamReader(SeriesConverter.class.getResourceAsStream(fileName)));
			while((line = bufferedReader.readLine()) != null) {
				String[] values = line.split("\t");
				String id = values[0].trim();
				double[] xSeries = new double[]{Double.parseDouble(values[1].trim())};
				double[] ySeries = new double[]{Double.parseDouble(values[2].trim())};
				ISeriesData seriesData = new SeriesData(xSeries, ySeries, id);
				scatterSeriesList.add(seriesData);
			}
		} catch(Exception e) {
			//
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch(IOException e) {
					//
				}
			}
		}
		return scatterSeriesList;
	}

	private static int getNumberOfLines(String fileName) {

		int i = 0;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(SeriesConverter.class.getResourceAsStream(fileName)));
			while((bufferedReader.readLine()) != null) {
				i++;
			}
		} catch(Exception e) {
			//
		} finally {
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch(IOException e) {
					//
				}
			}
		}
		return i;
	}
}
