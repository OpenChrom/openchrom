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

import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.nebula.visualization.xygraph.dataprovider.CircularBufferDataProvider;
import org.eclipse.nebula.visualization.xygraph.figures.Trace;
import org.eclipse.nebula.visualization.xygraph.figures.XYGraph;
import org.eclipse.nebula.visualization.xygraph.util.XYGraphMediaFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.process.supplier.cms.core.DecompositionResults;

public class CompositeSignals extends Composite {

	private int xyGraphSignalNumberOfResidualPoints = 0; // if xyGraphSignalNumberOfResidualPoints > 0, then remainder of xyGraphSignal residual data items are valid
	private XYGraph xyGraphSignals;
	private Trace traceScanSignalSum; // xyGraphSignal scan data item
	private Trace traceResidualSignalSum; // xyGraphSignal scan data item
	private Trace traceLeftMarker; // xyGraphSignal scan data item
	private Trace traceRightMarker; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceSignalSum; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceResidualSignalSum; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceLeftMarker; // xyGraphSignal scan data item
	private CircularBufferDataProvider dataProviderTraceRightMarker; // xyGraphSignal scan data item
	private double xDataTraceLeftMarker[]; // xyGraphSignal scan data item
	private double xDataTraceRightMarker[]; // xyGraphSignal scan data item
	private double xDataTraceScanSignalSum[]; // xyGraphSignal scan data item
	private double xDataTraceResidualSignalSum[]; // xyGraphSignal scan data item
	// private XYGraph xyGraphSignal;
	private int xyGraphSignalNumberOfScanPoints = 0; // if xyGraphSignalNumberOfScanPoints > 0, then remainder of xyGraphSignal scan data items are valid
	private double yDataTraceScanSignalSum[]; // xyGraphSignal scan data item
	private double yDataTraceResidualSignalSum[]; // xyGraphSignal scan data item
	private double yDataTraceLeftMarker[]; // xyGraphSignal scan data item
	private double yDataTraceRightMarker[]; // xyGraphSignal scan data item
	private String signalUnits;
	private boolean updateNeededLeftMarker = false;
	private boolean updateNeededRightMarker = false;
	private boolean updateNeededResiduals = false;
	private boolean updateNeededSignalSum = false;
	private double positionLeftMarker;
	private double positionRightMarker;
	private double signalMinY;
	private double signalMaxY;

	public CompositeSignals(Composite parent, int style) {
		super(parent, style);
		this.initialize();
	}

	public void setLeftMarker(double xPosition) {

		if(null != traceLeftMarker) {
			xyGraphSignals.removeTrace(traceLeftMarker);
		}
		positionLeftMarker = xPosition;
		updateNeededLeftMarker = true;
	}

	public void setRightMarker(double xPosition) {

		if(null != traceRightMarker) {
			xyGraphSignals.removeTrace(traceRightMarker);
		}
		positionRightMarker = xPosition;
		updateNeededRightMarker = true;
	}

	private void updateLeftMarker() {

		if(updateNeededLeftMarker) {
			dataProviderTraceLeftMarker = new CircularBufferDataProvider(false);
			dataProviderTraceLeftMarker.setBufferSize(2);
			xDataTraceLeftMarker = new double[2];
			yDataTraceLeftMarker = new double[2];
			traceLeftMarker = new Trace("", xyGraphSignals.getPrimaryXAxis(), xyGraphSignals.getPrimaryYAxis(), dataProviderTraceLeftMarker);
			traceLeftMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			xDataTraceLeftMarker[0] = positionLeftMarker;
			xDataTraceLeftMarker[1] = positionLeftMarker;
			yDataTraceLeftMarker[0] = xyGraphSignals.getPrimaryYAxis().getRange().getLower();
			yDataTraceLeftMarker[1] = xyGraphSignals.getPrimaryYAxis().getRange().getUpper();
			dataProviderTraceLeftMarker.setCurrentXDataArray(xDataTraceLeftMarker);
			dataProviderTraceLeftMarker.setCurrentYDataArray(yDataTraceLeftMarker);
			// xyGraphSignal.getPrimaryXAxis().setAutoScale(true);
			xyGraphSignals.addTrace(traceLeftMarker);
			updateNeededLeftMarker = false;
		}
	}

	private void updateRightMarker() {

		if(updateNeededRightMarker) {
			dataProviderTraceRightMarker = new CircularBufferDataProvider(false);
			dataProviderTraceRightMarker.setBufferSize(2);
			xDataTraceRightMarker = new double[2];
			yDataTraceRightMarker = new double[2];
			xDataTraceRightMarker[0] = positionRightMarker;
			xDataTraceRightMarker[1] = positionRightMarker;
			yDataTraceRightMarker[0] = xyGraphSignals.getPrimaryYAxis().getRange().getLower();
			yDataTraceRightMarker[1] = xyGraphSignals.getPrimaryYAxis().getRange().getUpper();
			traceRightMarker = new Trace("", xyGraphSignals.getPrimaryXAxis(), xyGraphSignals.getPrimaryYAxis(), dataProviderTraceRightMarker);
			traceRightMarker.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_RED));
			dataProviderTraceRightMarker.setCurrentXDataArray(xDataTraceRightMarker);
			dataProviderTraceRightMarker.setCurrentYDataArray(yDataTraceRightMarker);
			xyGraphSignals.addTrace(traceRightMarker);
			updateNeededRightMarker = false;
		}
	}

	public void clearResiduals() {

		if(null != traceResidualSignalSum) {
			xyGraphSignals.removeTrace(traceResidualSignalSum);
		}
		updateNeededResiduals = true;
		xyGraphSignalNumberOfResidualPoints = 0; // invalidate current XYGraph residual plot
	}

	private void updateResiduals(DecompositionResults results, boolean usingETimes) {

		ICalibratedVendorMassSpectrum spectrum;
		if((null != results) && updateNeededResiduals) {
			xyGraphSignals.getPrimaryYAxis().setAutoScale(true);
			dataProviderTraceResidualSignalSum = new CircularBufferDataProvider(false);
			xyGraphSignalNumberOfResidualPoints = results.getResults().size();
			dataProviderTraceResidualSignalSum.setBufferSize(xyGraphSignalNumberOfResidualPoints);
			xDataTraceResidualSignalSum = new double[xyGraphSignalNumberOfResidualPoints];
			yDataTraceResidualSignalSum = new double[xyGraphSignalNumberOfResidualPoints];
			for(int i = 0; i < results.getResults().size(); i++) {
				double signalSum;
				spectrum = results.getResults().get(i).getResidualSpectrum();
				if(!usingETimes) {
					xDataTraceResidualSignalSum[i] = spectrum.getScanNumber();
				} else {
					xDataTraceResidualSignalSum[i] = spectrum.getEtimes();
				}
				signalSum = spectrum.getTotalSignal();
				yDataTraceResidualSignalSum[i] = signalSum;
				if(signalMinY > signalSum) {
					signalMinY = signalSum;
				}
				if(signalMaxY < signalSum) {
					signalMaxY = signalSum;
				}
			}
			dataProviderTraceResidualSignalSum.setCurrentXDataArray(xDataTraceResidualSignalSum);
			dataProviderTraceResidualSignalSum.setCurrentYDataArray(yDataTraceResidualSignalSum);
			traceResidualSignalSum = new Trace("sum(Residual)", xyGraphSignals.getPrimaryXAxis(), xyGraphSignals.getPrimaryYAxis(), dataProviderTraceResidualSignalSum);
			traceResidualSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_PURPLE));
			// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
			xyGraphSignals.addTrace(traceResidualSignalSum);
			updateNeededResiduals = false;
		}
	}

	public void clearXYGraph() {

		if(null != traceScanSignalSum) {
			xyGraphSignals.removeTrace(traceScanSignalSum);
		}
		clearResiduals();
		updateNeededSignalSum = true;
	}

	private void initialize() {

		this.setLayout(new FillLayout());
		// compositeGraph.setLayout(new FillLayout());
		GridData compositeGraphGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compositeGraphGridData.horizontalSpan = 2;
		this.setLayoutData(compositeGraphGridData);
		//
		LightweightSystem lightweightSystem = new LightweightSystem(new Canvas(this, SWT.NONE));
		xyGraphSignals = new XYGraph();
		xyGraphSignals.setTitle("Signal");
		xyGraphSignals.getPrimaryXAxis().setAutoScale(true);
		xyGraphSignals.getPrimaryXAxis().setShowMajorGrid(true);
		xyGraphSignals.getPrimaryYAxis().setAutoScale(true);
		xyGraphSignals.getPrimaryYAxis().setShowMajorGrid(true);
		xyGraphSignals.getPrimaryYAxis().setFormatPattern("0.0##E00");
		xyGraphSignals.getPrimaryYAxis().setAutoScaleThreshold(0);
		lightweightSystem.setContents(xyGraphSignals);
	}

	private void updateSignalSum(IMassSpectra spectra, boolean usingETimes) {

		ICalibratedVendorMassSpectrum spectrum;
		if(updateNeededSignalSum) {
			signalMinY = 0;
			signalMaxY = 0;
			xyGraphSignals.getPrimaryYAxis().setAutoScale(true);
			xyGraphSignals.setTitle("Signal: " + spectra.getName());
			if(usingETimes) {
				xyGraphSignals.getPrimaryXAxis().setTitle("Elapsed Time, s");
			} else {
				xyGraphSignals.getPrimaryXAxis().setTitle("Scan Number");
			}
			// create a trace data provider, which will provide the data to the trace.
			dataProviderTraceSignalSum = new CircularBufferDataProvider(false);
			xyGraphSignalNumberOfScanPoints = spectra.getList().size();
			dataProviderTraceSignalSum.setBufferSize(xyGraphSignalNumberOfScanPoints);
			xDataTraceScanSignalSum = new double[xyGraphSignalNumberOfScanPoints];
			yDataTraceScanSignalSum = new double[xyGraphSignalNumberOfScanPoints];
			spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(1);
			signalUnits = spectrum.getSignalUnits();
			xyGraphSignals.getPrimaryYAxis().setTitle("Signal, " + signalUnits);
			for(int i = spectra.getList().size(); i > 0;) {
				double signalSum;
				spectrum = (ICalibratedVendorMassSpectrum)spectra.getMassSpectrum(i);
				--i;
				if(!usingETimes) {
					xDataTraceScanSignalSum[i] = spectrum.getScanNumber();
				} else {
					xDataTraceScanSignalSum[i] = spectrum.getEtimes();
				}
				signalSum = spectrum.getTotalSignal();
				yDataTraceScanSignalSum[i] = signalSum;
				if(signalMinY > yDataTraceScanSignalSum[i]) {
					signalMinY = yDataTraceScanSignalSum[i];
				}
				if(signalMaxY < signalSum) {
					signalMaxY = signalSum;
				}
			}
			dataProviderTraceSignalSum.setCurrentXDataArray(xDataTraceScanSignalSum);
			dataProviderTraceSignalSum.setCurrentYDataArray(yDataTraceScanSignalSum);
			if(null != traceScanSignalSum) {
				xyGraphSignals.removeTrace(traceScanSignalSum);
			}
			traceScanSignalSum = new Trace("sum(Signal)", xyGraphSignals.getPrimaryXAxis(), xyGraphSignals.getPrimaryYAxis(), dataProviderTraceSignalSum);
			traceScanSignalSum.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_BLUE));
			// traceScanSignalSum.setPointStyle(PointStyle.XCROSS);
			xyGraphSignals.addTrace(traceScanSignalSum);
			updateNeededSignalSum = false;
		}
	}

	public void updateXYGraph(IMassSpectra spectra, DecompositionResults results, boolean usingETimes, boolean usingOffsetLogScale) {

		System.out.println("Update Signal XYGraph for " + spectra.getName());
		updateSignalSum(spectra, usingETimes);
		updateResiduals(results, usingETimes);
		if((null != yDataTraceLeftMarker) && (signalMinY < yDataTraceLeftMarker[0])) {
			setLeftMarker(positionLeftMarker);
			setRightMarker(positionRightMarker);
		}
		xyGraphSignals.getPrimaryYAxis().setAutoScale(false);
		xyGraphSignals.getPrimaryYAxis().setRange(signalMinY, signalMaxY);
		updateLeftMarker();
		updateRightMarker();
		// xyGraph.setShowLegend(!xyGraph.isShowLegend());
	}
}
