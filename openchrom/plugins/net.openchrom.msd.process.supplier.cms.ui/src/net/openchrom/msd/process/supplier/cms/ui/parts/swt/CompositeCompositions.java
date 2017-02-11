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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.linearscale.Range;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeCompositions extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private TreeMap<String, Trace> traceCompositionsMap; // key is composition name string, value is Trace for that composition, needed so trace can be removed
	private XYGraph xyGraphComposition;
	private int xyGraphCompositionNumberOfPoints = 0; // if xyGraphCompositionNumberOfPoints > 0, then remainder of xyGraphComposition data items are valid
	private Button buttonPP; // select partial pressures
	private Button buttonMF; // select mol fraction
	private Button buttonLF; // select library fraction
	private DecompositionResults results = null;
	private boolean usingETimes;

	private enum Yunits {
		PP, MF, LF
	}; // Partial Pressure, Mol Fraction, Library Fraction

	private Yunits yUnits = Yunits.PP;

	private void setyUnits(Yunits yUnits) {

		if((null != results) && !results.isCalibrated()) {
			yUnits = Yunits.LF;
			buttonPP.setSelection(false);
			buttonMF.setSelection(false);
			buttonLF.setSelection(true);
		} else {
			this.yUnits = yUnits;
		}
		updateXYGraph();
	}

	public CompositeCompositions(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	private void initialize() {

		/*
		 * XY Graph
		 */
		GridLayout thisGridLayout = new GridLayout(1, false);
		this.setLayout(thisGridLayout);
		GridData thisGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.setLayoutData(thisGridData);
		//
		Composite compositeTopRow = new Composite(this, SWT.BORDER);
		GridLayout topRowCompositeGridLayout = new GridLayout(4, false);
		// topRowCompositeGridLayout.marginHeight = 0;
		// topRowCompositeGridLayout.marginWidth = 0;
		compositeTopRow.setLayout(topRowCompositeGridLayout);
		GridData topRowCompositeGridData = new GridData(SWT.FILL, SWT.TOP, true, false);
		compositeTopRow.setLayoutData(topRowCompositeGridData);
		// display units Buttons
		Label label = new Label(compositeTopRow, SWT.NONE);
		label.setText("Display Units:");
		//
		buttonPP = new Button(compositeTopRow, SWT.RADIO);
		buttonPP.setText("Partial Pressure");
		buttonPP.setSelection(true);
		buttonPP.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonPP.getSelection()) {
						// System.out.println("buttonPP selection event, selected");
						setyUnits(Yunits.PP);
					} else {
						// System.out.println("buttonPP selection event, not selected");
					}
					// decomposeSpectra();
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		buttonMF = new Button(compositeTopRow, SWT.RADIO);
		buttonMF.setText("Mol Fraction");
		buttonMF.setSelection(false);
		buttonMF.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonMF.getSelection()) {
						// System.out.println("buttonMF selection event, selected");
						setyUnits(Yunits.MF);
					} else {
						// System.out.println("buttonMF selection event, not selected");
					}
					// decomposeSpectra();
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
		//
		buttonLF = new Button(compositeTopRow, SWT.RADIO);
		buttonLF.setText("Library Fraction");
		buttonLF.setSelection(false);
		buttonLF.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				try {
					if(buttonLF.getSelection()) {
						// System.out.println("buttonLF selection event, selected");
						setyUnits(Yunits.LF);
					} else {
						// System.out.println("buttonLF selection event, not selected");
					}
					// decomposeSpectra();
				} catch(Exception e1) {
					logger.warn(e1);
				}
			}
		});
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

		this.results = results;
		this.usingETimes = usingETimes;
		if((null != results) && !results.isCalibrated()) {
			yUnits = Yunits.LF;
			buttonPP.setSelection(false);
			buttonMF.setSelection(false);
			buttonLF.setSelection(true);
		}
		updateXYGraph();
	}

	private void updateXYGraph() {

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
			if(results.isCalibrated() && !(Yunits.LF == yUnits)) {
				if(Yunits.PP == yUnits) {
					xyGraphComposition.getPrimaryYAxis().setTitle("Partial Pressure, " + ppUnits);
				} else if(Yunits.MF == yUnits) {
					xyGraphComposition.getPrimaryYAxis().setTitle("Mol Fraction, dimensionless");
				}
			} else {
				xyGraphComposition.getPrimaryYAxis().setTitle("Library Contribution, uncalibrated");
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
					if(results.isCalibrated() && !(Yunits.LF == yUnits)) {
						if(Yunits.PP == yUnits) {
							lookup.get(componentName).add(i, result.getPartialPressure(j, ppUnits));
						} else if(Yunits.MF == yUnits) {
							lookup.get(componentName).add(i, result.getMolFraction(j)); // MF units
						}
					} else {
						lookup.get(componentName).add(i, result.getLibraryFraction(j));
					}
				}
				if(usingETimes) {
					xDataTraceComposition[i] = result.getETimeS();
				} else {
					xDataTraceComposition[i] = result.getResidualSpectrum().getScanNumber();
				}
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
