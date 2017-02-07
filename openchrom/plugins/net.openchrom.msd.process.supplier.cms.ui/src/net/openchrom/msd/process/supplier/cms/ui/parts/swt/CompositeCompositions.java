/*******************************************************************************
 * Copyright (c) 2017 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * whitlow - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.util.ArrayList;
import java.util.TreeMap;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeCompositions extends Composite {

	private TreeMap<String, Trace> traceCompositionsMap; // key is composition name string, value is Trace for that composition, needed so trace can be removed
	private XYGraph xyGraphComposition;
	private int xyGraphCompositionNumberOfPoints = 0; // if xyGraphCompositionNumberOfPoints > 0, then remainder of xyGraphComposition data items are valid

	public CompositeCompositions(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	private void initialize() {

		/*
		 * XY Graph
		 */
		this.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(this, SWT.NONE));
		xyGraphComposition = new XYGraph();
		traceCompositionsMap = new TreeMap<String, Trace>();
		xyGraphComposition.setTitle("Composition");
		xyGraphComposition.getPrimaryXAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphComposition.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraphComposition);
	}

	public void clearXYGraph() {

		if(null != traceCompositionsMap) {
			for(Trace traceTemp : traceCompositionsMap.values()) {
				xyGraphComposition.removeTrace(traceTemp);
			}
		}
		xyGraphComposition.setTitle("Composition");
		xyGraphComposition.getPrimaryXAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
		xyGraphComposition.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphComposition.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphComposition.getPrimaryYAxis().setAutoScaleThreshold(0);
		xyGraphCompositionNumberOfPoints = 0; // invalidate current XYGraph composition plots
	}

	public void updateXYGraph(DecompositionResults results, boolean usingETimes) {

		if(null != results) {
			System.out.println("Update Composition XYGraph for " + results.getName());
			// if(0 == xyGraphNumberOfPoints) {
			String newTitle = results.getName();
			String ppUnits = results.getResults().get(0).getSourcePressureUnits();
			newTitle = "Composition: " + newTitle;
			xyGraphComposition.setTitle(newTitle);
			if(usingETimes) {
				xyGraphComposition.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphComposition.getPrimaryXAxis().setTitle("Scan Number");
			}
			TreeMap<String, ArrayList<Double>> lookup = new TreeMap<String, ArrayList<Double>>();
			xyGraphCompositionNumberOfPoints = results.getResults().size();
			double[] xDataTraceComposition = new double[xyGraphCompositionNumberOfPoints];
			String componentName;
			DecompositionResult result;
			for(int i = 0; i < xyGraphCompositionNumberOfPoints; i++) {
				result = results.getResults().get(i);
				for(int j = 0; j < result.getNumberOfComponents(); j++) {
					componentName = result.getLibCompName(j);
					if(null == lookup.get(componentName)) {
						lookup.put(componentName, new ArrayList<Double>());
					}
					if(results.isCalibrated()) {
						lookup.get(componentName).add(i, result.getPartialPressure(j, ppUnits));
					} else {
						lookup.get(componentName).add(i, result.getFraction(j));
					}
				}
				if(usingETimes) {
					xDataTraceComposition[i] = result.getETimeS();
				} else {
					xDataTraceComposition[i] = result.getResidualSpectrum().getSpectrumNumber();
				}
			}
			if(results.isCalibrated()) {
				xyGraphComposition.getPrimaryYAxis().setTitle("Partial Pressure, " + ppUnits);
			} else {
				xyGraphComposition.getPrimaryYAxis().setTitle("Library Contribution, uncalibrated");
			}
			if(0 >= lookup.size()) {
				return; // no composition results
			}
			double minY = 0;
			for(String strName : lookup.keySet()) {
				ArrayList<Double> templist;
				Color traceColor;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				double[] ydata = new double[tempdata.length];
				for(int ii = 0; ii < tempdata.length; ii++) {
					ydata[ii] = tempdata[ii].doubleValue();
					if(minY > ydata[ii]) {
						minY = ydata[ii];
					}
				}
				// create a trace data provider, which will provide the data to the trace.
				CircularBufferDataProvider dataProviderTraceComposition = new CircularBufferDataProvider(false); // XYGraph data item
				dataProviderTraceComposition.setBufferSize(xyGraphCompositionNumberOfPoints);
				dataProviderTraceComposition.setCurrentXDataArray(xDataTraceComposition);
				dataProviderTraceComposition.setCurrentYDataArray(ydata);
				Trace traceTemp = traceCompositionsMap.get(strName);
				traceColor = null;
				if(null != traceTemp) {
					traceColor = traceTemp.getTraceColor();
					xyGraphComposition.removeTrace(traceTemp);
				}
				Trace traceComposition = new Trace(strName, xyGraphComposition.getPrimaryXAxis(), xyGraphComposition.getPrimaryYAxis(), dataProviderTraceComposition);
				traceCompositionsMap.put(strName, traceComposition);
				if(null != traceColor) {
					traceComposition.setTraceColor(traceColor);
				}
				// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
				xyGraphComposition.addTrace(traceComposition);
			}
			if(0 > minY) {
				xyGraphComposition.getPrimaryYAxis().setAutoScale(true);
			} else {
				Range range;
				xyGraphComposition.getPrimaryYAxis().setAutoScale(false);
				range = xyGraphComposition.getPrimaryYAxis().getRange();
				xyGraphComposition.getPrimaryYAxis().setRange(minY, range.getUpper());
			}
			// if(hasETimes) {
			// double xygraphMinETimes, xygraphMaxETimes;
			// xygraphMinETimes = results.getResults().get(0).getETimeS();
			// xygraphMaxETimes = results.getResults().get(results.getResults().size()-1).getETimeS();
			// xyGraphComposition.getPrimaryXAxis().setRange(new Range(xygraphMinETimes, xygraphMaxETimes));
			// } else {
			// xyGraphComposition.getPrimaryXAxis().setRange(new Range(results.getResults().get(0).getResidualSpectrum().getSpectrumNumber(),
			// results.getResults().get(results.getResults().size()-1).getResidualSpectrum().getSpectrumNumber()));
			// }
		}
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
		// Display display = Display.getDefault();
	}
}
