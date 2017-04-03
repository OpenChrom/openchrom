/*******************************************************************************
 * Copyright (c) 2017 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.ui.parts.swt;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import net.openchrom.msd.process.supplier.cms.core.CorrelationResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeCorrelationsUI extends Composite {

	private static final Logger logger = Logger.getLogger(CompositeCorrelationsUI.class);
	//
	private DecimalFormat decimalFormatscaleOffset = ValueFormat.getDecimalFormatEnglish("0.0##E00");
	private TreeMap<String, Trace> traceCompositionsMap; // key is composition name string, value is Trace for that composition, needed so trace can be removed
	private XYGraph xyGraphComposition;
	private int xyGraphCompositionNumberOfPoints = 0; // if xyGraphCompositionNumberOfPoints > 0, then remainder of xyGraphComposition data items are valid
	private DecompositionResults results = null;
	private boolean usingETimes = false;
	private boolean usingOffsetLogScale = false;
	private double scaleOffset;
	private Trace traceScaleOffset = null;

	public CompositeCorrelationsUI(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	private void initialize() {

		GridLayout thisGridLayout = new GridLayout(1, false);
		this.setLayout(thisGridLayout);
		GridData thisGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(thisGridData);
		//
		Composite compositeTopRow = new Composite(this, SWT.NONE);
		GridLayout topRowCompositeGridLayout = new GridLayout(2, false);
		compositeTopRow.setLayout(topRowCompositeGridLayout);
		GridData topRowCompositeGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeTopRow.setLayoutData(topRowCompositeGridData);
		//
		Group compositGroup = new Group(compositeTopRow, SWT.NONE);
		GridLayout compositGroupGridLayout = new GridLayout(4, false);
		compositGroup.setLayout(compositGroupGridLayout);
		GridData compositGroupGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		compositGroup.setLayoutData(compositGroupGridData);
		//
		Composite compositeGraph = new Composite(this, SWT.NONE);
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayout(new FillLayout());
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
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

	private void clearXYGraph() {

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

	public void updateXYGraph(DecompositionResults results) {

		this.results = results;
		if(null == results) {
			clearXYGraph();
			return;
		}
		this.usingETimes = results.isUsingETimes();
		updateXYGraph();
	}

	private void updateXYGraph() {

		if(null != results) {
			System.out.println("Update Correlation XYGraph for " + results.getName());
			String newTitle = results.getName();
			results.getDecompositionResultsList().get(0).getSourcePressureUnits();
			newTitle = "Correlation: " + newTitle;
			xyGraphComposition.setTitle(newTitle);
			if(usingETimes) {
				xyGraphComposition.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphComposition.getPrimaryXAxis().setTitle("Scan Number");
			}
			xyGraphComposition.getPrimaryYAxis().setTitle("Correlation Value");
			xyGraphCompositionNumberOfPoints = results.getDecompositionResultsList().size();
			//
			double[] xDataTraceComposition = new double[xyGraphCompositionNumberOfPoints];
			TreeMap<String, ArrayList<Double>> lookup = new TreeMap<String, ArrayList<Double>>();
			ConcurrentSkipListSet<String> topNamesList = new ConcurrentSkipListSet<String>();
			//
			String libraryName;
			CorrelationResult correlationResult;
			int maxTop = 1; // whw
			for(int i = 0; i < xyGraphCompositionNumberOfPoints; i++) {
				correlationResult = results.getDecompositionResultsList().get(i).getCorrelationResult();
				for(int j = 0; j < correlationResult.getResultsCount() && j < maxTop; j++) {
					topNamesList.add(correlationResult.getCorrelationLibName(j));
				}
				for(int j = 0; j < correlationResult.getResultsCount(); j++) {
					libraryName = correlationResult.getCorrelationLibName(j);
					if(null == lookup.get(libraryName)) {
						lookup.put(libraryName, new ArrayList<Double>());
					}
					lookup.get(libraryName).add(i, correlationResult.getCorrelationValue(j));
				}
				if(usingETimes) {
					xDataTraceComposition[i] = results.getDecompositionResultsList().get(i).getETimeS();
				} else {
					xDataTraceComposition[i] = results.getDecompositionResultsList().get(i).getResidualSpectrum().getScanNumber();
				}
			}
			if(0 >= lookup.size()) {
				return; // no correlation results
			}
			double minY = 0, minAbsY = 0, maxY = 0;
			for(String strName : lookup.keySet()) {
				ArrayList<Double> templist;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				for(int ii = 0; ii < tempdata.length; ii++) {
					double signal;
					signal = tempdata[ii];
					if(maxY == 0 && minY == 0) {
						maxY = minY = signal;
						minAbsY = java.lang.StrictMath.abs(signal);
					} else {
						if(maxY < signal) {
							maxY = signal;
						}
						//
						if(minY > signal) {
							minY = signal;
						}
						//
						if(signal != 0) {
							signal = java.lang.StrictMath.abs(signal);
							if(minAbsY == 0 || minAbsY > signal) {
								minAbsY = signal;
							}
						}
					}
				}
			}
			for(String strName : lookup.keySet()) {
				double temp;
				ArrayList<Double> templist;
				Color traceColor;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				double[] ydata = new double[tempdata.length];
				if(usingOffsetLogScale) {
					for(int ii = 0; ii < tempdata.length; ii++) {
						temp = tempdata[ii].doubleValue() + scaleOffset;
						if(0d >= temp) {
							temp = minAbsY;
						}
						ydata[ii] = temp;
					}
				} else {
					for(int ii = 0; ii < tempdata.length; ii++) {
						ydata[ii] = tempdata[ii].doubleValue();
					}
				}
				// create a trace data provider
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
				// traceComposition.setPointStyle(PointStyle.XCROSS);
				xyGraphComposition.addTrace(traceComposition);
			}
			if(null != traceScaleOffset) {
				xyGraphComposition.removeTrace(traceScaleOffset);
				traceScaleOffset = null;
			}
			xyGraphComposition.getPrimaryYAxis().setAutoScale(false);
			if(!usingOffsetLogScale) {
				if(0 < minY) {
					xyGraphComposition.getPrimaryYAxis().setRange(0, 1.05 * maxY);
				} else {
					xyGraphComposition.getPrimaryYAxis().setRange(1.05 * minY, 1.05 * maxY);
				}
			} else {
				xyGraphComposition.getPrimaryYAxis().setRange(0.95 * minAbsY, 1.05 * (maxY + scaleOffset));
				CircularBufferDataProvider dataProviderTraceScaleOffset = new CircularBufferDataProvider(false); // XYGraph data item
				dataProviderTraceScaleOffset.setBufferSize(2);
				double[] ydata = new double[2];
				double[] xdata = new double[2];
				ydata[0] = ydata[1] = scaleOffset;
				xdata[0] = xyGraphComposition.getPrimaryXAxis().getRange().getLower();
				xdata[1] = xyGraphComposition.getPrimaryXAxis().getRange().getUpper();
				dataProviderTraceScaleOffset.setCurrentXDataArray(xdata);
				dataProviderTraceScaleOffset.setCurrentYDataArray(ydata);
				traceScaleOffset = new Trace("Zero(" + decimalFormatscaleOffset.format(scaleOffset) + ")", xyGraphComposition.getPrimaryXAxis(), xyGraphComposition.getPrimaryYAxis(), dataProviderTraceScaleOffset);
				traceScaleOffset.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
				// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
				xyGraphComposition.addTrace(traceScaleOffset);
			}
		}
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
	}
}
