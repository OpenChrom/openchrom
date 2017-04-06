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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import net.openchrom.msd.process.supplier.cms.core.CorrelationResult;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeCorrelationsUI extends Composite {

	private static final Logger logger = Logger.getLogger(CompositeCorrelationsUI.class);
	//
	private TreeMap<String, Trace> traceCorrelationsMap; // key is correlation name string, value is Trace for that correlation, needed so trace can be removed
	private XYGraph xyGraphCorrelation;
	private int xyGraphCorrelationNumberOfPoints = 0; // if xyGraphCorrelationNumberOfPoints > 0, then remainder of xyGraphCorrelation data items are valid
	private DecompositionResults results = null;
	private Spinner spinnerTopSelect;
	private Label spinnerTopSelectLabel;
	private boolean usingETimes = false;
	private int maxTop = 1;

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
		spinnerTopSelectLabel = new Label(compositGroup, SWT.RIGHT);
		spinnerTopSelectLabel.setText("Show Top:");
		GridData labelGridData = new GridData(SWT.LEFT, SWT.CENTER, false, true);
		spinnerTopSelectLabel.setLayoutData(labelGridData);
		//
		spinnerTopSelect = new Spinner(compositGroup, SWT.RIGHT | SWT.BORDER | SWT.READ_ONLY);
		spinnerTopSelect.setMinimum(1);
		spinnerTopSelect.setMaximum(10);
		spinnerTopSelect.setSelection(1);
		spinnerTopSelect.setIncrement(1);
		spinnerTopSelect.setPageIncrement(10);
		spinnerTopSelect.setToolTipText("increment or decrement top N select");
		GridData spinnerLeftScanNumberGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, true);
		spinnerLeftScanNumberGridData.widthHint = 32;
		spinnerTopSelect.setLayoutData(spinnerLeftScanNumberGridData);
		spinnerTopSelect.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				maxTop = spinnerTopSelect.getSelection();
				updateXYGraph();
			}
		});
		//
		Composite compositeGraph = new Composite(this, SWT.NONE);
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraph.setLayout(new FillLayout());
		compositeGraph.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(compositeGraph, SWT.NONE));
		xyGraphCorrelation = new XYGraph();
		traceCorrelationsMap = new TreeMap<String, Trace>();
		xyGraphCorrelation.setTitle("Correlation");
		xyGraphCorrelation.getPrimaryXAxis().setAutoScale(true);
		xyGraphCorrelation.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphCorrelation.getPrimaryYAxis().setAutoScale(true);
		xyGraphCorrelation.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphCorrelation.getPrimaryYAxis().setFormatPattern("0.00");
		xyGraphCorrelation.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraphCorrelation);
	}

	private void clearXYGraph() {

		if(null != traceCorrelationsMap) {
			for(Trace traceTemp : traceCorrelationsMap.values()) {
				xyGraphCorrelation.removeTrace(traceTemp);
			}
		}
		xyGraphCorrelation.setTitle("Correlation");
		xyGraphCorrelation.getPrimaryXAxis().setAutoScale(true);
		xyGraphCorrelation.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphCorrelation.getPrimaryYAxis().setAutoScale(true);
		xyGraphCorrelation.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphCorrelation.getPrimaryYAxis().setFormatPattern("0.00");
		xyGraphCorrelation.getPrimaryYAxis().setAutoScaleThreshold(0);
		xyGraphCorrelationNumberOfPoints = 0; // invalidate current XYGraph correlation plots
	}

	public void updateXYGraph(DecompositionResults results) {

		this.results = results;
		if(null == results) {
			clearXYGraph();
			return;
		}
		updateXYGraph();
	}

	private void updateXYGraph() {

		if(null != results) {
			this.usingETimes = results.isUsingETimes();
			System.out.println("Update Correlation XYGraph for " + results.getName());
			String newTitle = results.getName();
			newTitle = "Correlation: " + newTitle;
			xyGraphCorrelation.setTitle(newTitle);
			if(usingETimes) {
				xyGraphCorrelation.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphCorrelation.getPrimaryXAxis().setTitle("Scan Number");
			}
			xyGraphCorrelation.getPrimaryYAxis().setTitle("Correlation Value");
			xyGraphCorrelationNumberOfPoints = results.getDecompositionResultsList().size();
			//
			double[] xDataTraceCorrelation = new double[xyGraphCorrelationNumberOfPoints];
			TreeMap<String, ArrayList<Double>> lookup = new TreeMap<String, ArrayList<Double>>();
			ConcurrentSkipListSet<String> topNamesList = new ConcurrentSkipListSet<String>();
			//
			String libraryName;
			CorrelationResult correlationResult;
			int selectCount;
			for(int i = 0; i < xyGraphCorrelationNumberOfPoints; i++) {
				correlationResult = results.getDecompositionResultsList().get(i).getCorrelationResult();
				selectCount = 0;
				for(int j = 0; j < correlationResult.getResultsCount(); j++) {
					if(!correlationResult.libraryIsSelected(j)) {
						topNamesList.add(correlationResult.getCorrelationLibName(j));
						selectCount++;
						if(maxTop <= selectCount) {
							break; // for
						}
					}
				}
				for(int j = 0; j < correlationResult.getResultsCount(); j++) {
					libraryName = correlationResult.getCorrelationLibName(j);
					if(null == lookup.get(libraryName)) {
						lookup.put(libraryName, new ArrayList<Double>());
					}
					lookup.get(libraryName).add(i, correlationResult.getCorrelationValue(j));
				}
				if(usingETimes) {
					xDataTraceCorrelation[i] = results.getDecompositionResultsList().get(i).getETimeS();
				} else {
					xDataTraceCorrelation[i] = results.getDecompositionResultsList().get(i).getResidualSpectrum().getScanNumber();
				}
			}
			if(0 >= lookup.size()) {
				return; // no correlation results
			}
			//
			Iterator<String> itr = traceCorrelationsMap.keySet().iterator();
			while(itr.hasNext()) {
				String strName = itr.next();
				if(!topNamesList.contains(strName)) {
					Trace traceTemp = traceCorrelationsMap.get(strName);
					xyGraphCorrelation.removeTrace(traceTemp);
					itr.remove();
				}
			}
			//
			for(String strName : topNamesList) {
				ArrayList<Double> templist;
				Color traceColor;
				templist = lookup.get(strName);
				Double[] tempdata = new Double[1];
				tempdata = templist.toArray(tempdata);
				double[] ydata = new double[tempdata.length];
				for(int ii = 0; ii < tempdata.length; ii++) {
					ydata[ii] = tempdata[ii].doubleValue();
				}
				// create a trace data provider
				CircularBufferDataProvider dataProviderTraceCorrelation = new CircularBufferDataProvider(false); // XYGraph data item
				dataProviderTraceCorrelation.setBufferSize(xyGraphCorrelationNumberOfPoints);
				dataProviderTraceCorrelation.setCurrentXDataArray(xDataTraceCorrelation);
				dataProviderTraceCorrelation.setCurrentYDataArray(ydata);
				Trace traceTemp = traceCorrelationsMap.get(strName);
				traceColor = null;
				if(null != traceTemp) {
					traceColor = traceTemp.getTraceColor();
					xyGraphCorrelation.removeTrace(traceTemp);
				}
				Trace traceCorrelation = new Trace(strName, xyGraphCorrelation.getPrimaryXAxis(), xyGraphCorrelation.getPrimaryYAxis(), dataProviderTraceCorrelation);
				traceCorrelationsMap.put(strName, traceCorrelation);
				if(null != traceColor) {
					traceCorrelation.setTraceColor(traceColor);
				}
				// traceCorrelation.setPointStyle(PointStyle.XCROSS);
				xyGraphCorrelation.addTrace(traceCorrelation);
			}
			xyGraphCorrelation.getPrimaryYAxis().setAutoScale(true);
		}
	}
}
