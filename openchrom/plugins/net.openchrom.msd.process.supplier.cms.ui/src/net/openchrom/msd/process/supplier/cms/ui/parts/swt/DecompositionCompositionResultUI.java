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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.process.supplier.cms.core.DecompositionResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class DecompositionCompositionResultUI extends Composite {

	private static final Logger logger = Logger.getLogger(DecompositionResultUI.class);
	private boolean hasETimes; // true if all residual spectra have valid ETimes, set when the CMS file is read
	private XYGraph xyGraph;
	private int xyGraphNumberOfPoints = 0; // if xyNumberOfPoints > 0, then remainder of XYGraph data items are valid

	public DecompositionCompositionResultUI(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {

		setLayout(new FillLayout());
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout compositeGridLayout = new GridLayout(1, false);
		composite.setLayout(compositeGridLayout);
		//
		/*
		 * XY Graph
		 */
		Composite compositeGraph = new Composite(composite, SWT.NONE);
		compositeGraph.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraph = new XYGraph();
		xyGraph.setTitle("Composition");
		xyGraph.getPrimaryXAxis().setAutoScale(true);
		xyGraph.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setAutoScale(true);
		xyGraph.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraph.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraph.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraph);
	}

	public void updateXYGraph(DecompositionResults results) {

		if(null != results) {
			hasETimes = results.hasETimes();
			hasETimes = false; // whw, for testing
			System.out.println("Update Composition XYGraph for " + results.getName());
			// if(0 == xyGraphNumberOfPoints) {
			String newTitle = results.getName();
			String ppUnits = results.getResults().get(0).getSourcePressureUnits();
			newTitle = "Composition: " + newTitle;
			xyGraph.setTitle(newTitle);
			if(hasETimes) {
				xyGraph.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraph.getPrimaryXAxis().setTitle("Scan Number");
			}
			TreeMap<String, ArrayList<Double>> lookup = new TreeMap<String, ArrayList<Double>>();
			if(results.isCalibrated()) {
				xyGraph.getPrimaryYAxis().setTitle("Partial Pressure, " + ppUnits);
				xyGraphNumberOfPoints = results.getResults().size();
				double[] xDataTraceComposition = new double[xyGraphNumberOfPoints];
				String componentName;
				DecompositionResult result;
				for(int i = 0; i < xyGraphNumberOfPoints; i++) {
					result = results.getResults().get(i);
					for(int j = 0; j < result.getNumberOfComponents(); j++) {
						componentName = result.getLibCompName(j);
						if(null == lookup.get(componentName)) {
							lookup.put(componentName, new ArrayList<Double>());
						}
						lookup.get(componentName).add(i, result.getPartialPressure(j));
					}
					if(hasETimes) {
						xDataTraceComposition[i] = result.getETimeS();
					} else {
						xDataTraceComposition[i] = result.getResidualSpectrum().getSpectrumNumber();
					}
				}
				if(0 >= lookup.size()) {
					return;
				}
				for(String name : lookup.keySet()) {
					Double[] tempdata = null;
					tempdata = lookup.get(name).toArray(tempdata);
					double[] ydata = new double[tempdata.length];
					for(int ii = 0; ii < tempdata.length; ii++) {
						ydata[ii] = tempdata[ii].doubleValue();
					}
					// create a trace data provider, which will provide the data to the trace.
					CircularBufferDataProvider dataProviderTraceComposition = new CircularBufferDataProvider(false); // XYGraph data item
					dataProviderTraceComposition.setCurrentXDataArray(xDataTraceComposition);
					dataProviderTraceComposition.setCurrentYDataArray(ydata);
					// if(null != traceComposition) {
					// xyGraph.removeTrace(traceComposition);
					// }
					// if(null != traceComposition) {
					// xyGraph.removeTrace(traceComposition);
					// }
					Trace traceComposition = new Trace(name, xyGraph.getPrimaryXAxis(), xyGraph.getPrimaryYAxis(), dataProviderTraceComposition);
					// traceComposition.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_BLUE));
					// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
					xyGraph.addTrace(traceComposition);
				}
			}
			// }
			// xyGraph.setShowLegend(!xyGraph.isShowLegend());
			// Display display = Display.getDefault();
		}
	}
}
