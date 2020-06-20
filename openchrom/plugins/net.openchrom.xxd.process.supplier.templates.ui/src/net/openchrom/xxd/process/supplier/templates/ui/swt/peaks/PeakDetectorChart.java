/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.selection.ChromatogramSelection;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.ChromatogramPeakChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.PeakChartSettings;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.BaselineSelectionPaintListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.ICustomSelectionHandler;
import org.eclipse.swtchart.extensions.core.IExtendedChart;
import org.eclipse.swtchart.extensions.core.RangeRestriction;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;

public class PeakDetectorChart extends ChromatogramPeakChart {

	private BaselineSelectionPaintListener baselineSelectionPaintListener;
	private Cursor defaultCursor;
	//
	private int xStart;
	private int yStart;
	private int xStop;
	private int yStop;
	//
	private Range selectedRangeX = null;
	private Range selectedRangeY = null;
	//
	private PeakSupport peakSupport = new PeakSupport();
	private DetectorRange detectorRange;
	@SuppressWarnings("rawtypes")
	private IChromatogramSelection chromatogramSelection = null;
	//
	private IPeakUpdateListener peakUpdateListener = null;
	private ISectionUpdateListener sectionUpdateListener = null;
	//
	private PeakDetectorChartSettings chartSettingsDefault = new PeakDetectorChartSettings();
	private DeltaRangePaintListener deltaRangePaintListener = new DeltaRangePaintListener(this.getBaseChart());
	//
	private boolean isReplacePeak = false;

	public PeakDetectorChart(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void updatePeaks(List<IPeak> peaks) {

		super.updatePeaks(peaks);
		adjustChartRange();
	}

	public void setPeakUpdateListener(IPeakUpdateListener peakUdateListener) {

		this.peakUpdateListener = peakUdateListener;
	}

	public void setSectionUpdateListener(ISectionUpdateListener sectionUpdateListener) {

		this.sectionUpdateListener = sectionUpdateListener;
	}

	public void update(DetectorRange detectorRange) {

		update(detectorRange, chartSettingsDefault);
	}

	public void updateRangeX(Range selectedRangeX) {

		updateRange(selectedRangeX, selectedRangeY);
	}

	public void updateRangeY(Range selectedRangeY) {

		updateRange(selectedRangeX, selectedRangeY);
	}

	public void updateRange(Range selectedRangeX, Range selectedRangeY) {

		this.selectedRangeX = selectedRangeX;
		this.selectedRangeY = selectedRangeY;
		adjustChartRange();
	}

	public void update(DetectorRange detectorRange, PeakDetectorChartSettings chartSettings) {

		this.detectorRange = detectorRange;
		selectedRangeX = null;
		selectedRangeY = null;
		int deltaRetentionTimeLeft = chartSettings.getDeltaRetentionTimeLeft();
		int deltaRetentionTimeRight = chartSettings.getDeltaRetentionTimeRight();
		deltaRangePaintListener.setDeltaRetentionTime(deltaRetentionTimeLeft, deltaRetentionTimeRight);
		isReplacePeak = chartSettings.isReplacePeak();
		updateDetectorRange(chartSettings);
	}

	@Override
	public void handleMouseDownEvent(Event event) {

		super.handleMouseDownEvent(event);
		if(isControlKeyPressed(event)) {
			startBaselineSelection(event.x, event.y);
			setCursor(SWT.CURSOR_CROSS);
		}
	}

	@Override
	public void handleMouseMoveEvent(Event event) {

		super.handleMouseMoveEvent(event);
		if(isControlKeyPressed(event)) {
			if(xStart > 0 && yStart > 0) {
				trackBaselineSelection(event.x, event.y);
			}
		}
	}

	@Override
	public void handleMouseUpEvent(Event event) {

		super.handleMouseUpEvent(event);
		if(isControlKeyPressed(event)) {
			stopBaselineSelection(event.x, event.y);
			IPeak peak = extractPeak();
			setCursorDefault();
			resetSelectedRange();
			updateChart(peak);
		}
	}

	@Override
	public void handleMouseDoubleClick(Event event) {

		super.handleMouseDoubleClick(event);
		if(sectionUpdateListener != null) {
			int x = event.x;
			Point size = getBaseChart().getPlotArea().getSize();
			int rangePrevious = size.x / 2;
			sectionUpdateListener.update(x < rangePrevious);
		}
	}

	private void createControl() {

		defaultCursor = getBaseChart().getCursor();
		/*
		 * Chart Settings
		 */
		boolean bufferedSelection = PreferenceSupplier.isChartBufferedSelection();
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.setBufferSelection(bufferedSelection);
		chartSettings.setEnableCompress(!bufferedSelection);
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setZeroY(true);
		applySettings(chartSettings);
		/*
		 * Add the paint listeners to draw the selected peak range.
		 */
		IPlotArea plotArea = getBaseChart().getPlotArea();
		baselineSelectionPaintListener = new BaselineSelectionPaintListener();
		plotArea.addCustomPaintListener(baselineSelectionPaintListener);
		//
		getBaseChart().addCustomRangeSelectionHandler(new ICustomSelectionHandler() {

			@Override
			public void handleUserSelection(Event event) {

				BaseChart baseChart = getBaseChart();
				selectedRangeX = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS).getRange();
				selectedRangeY = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS).getRange();
			}
		});
	}

	private boolean isControlKeyPressed(Event event) {

		return (event.stateMask & SWT.CTRL) == SWT.CTRL;
	}

	private void startBaselineSelection(int x, int y) {

		xStart = x;
		yStart = y;
		/*
		 * Set the start point.
		 */
		baselineSelectionPaintListener.setX1(xStart);
		baselineSelectionPaintListener.setY1(yStart);
	}

	private void trackBaselineSelection(int x, int y) {

		xStop = x;
		yStop = y;
		//
		baselineSelectionPaintListener.setX1(xStart);
		baselineSelectionPaintListener.setY1(yStart);
		baselineSelectionPaintListener.setX2(xStop);
		baselineSelectionPaintListener.setY2(yStop);
		//
		redrawChart();
	}

	private void stopBaselineSelection(int x, int y) {

		xStop = x;
		yStop = y;
	}

	private void resetSelectedRange() {

		baselineSelectionPaintListener.reset();
		//
		xStart = 0;
		yStart = 0;
		xStop = 0;
		yStop = 0;
		//
		redrawChart();
	}

	private void setCursor(int cursorId) {

		getBaseChart().setCursor(getBaseChart().getDisplay().getSystemCursor(cursorId));
	}

	private void setCursorDefault() {

		getBaseChart().setCursor(defaultCursor);
	}

	private void redrawChart() {

		getBaseChart().redraw();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void updateDetectorRange(PeakChartSettings peakChartSettings) {

		deleteSeries();
		if(detectorRange != null) {
			IChromatogram<? extends IPeak> chromatogram = detectorRange.getChromatogram();
			if(chromatogram != null) {
				/*
				 * TIC/XIC
				 */
				Set<Integer> traces = detectorRange.getTraces();
				if(showTraces(chromatogram, traces)) {
					ChromatogramSelectionMSD chromatogramSelectionMSD = new ChromatogramSelectionMSD((IChromatogramMSD)chromatogram);
					chromatogramSelectionMSD.getSelectedIons().add(traces);
					chromatogramSelection = chromatogramSelectionMSD;
				} else {
					chromatogramSelection = new ChromatogramSelection(chromatogram);
				}
				/*
				 * Retention Time Range
				 */
				int startRetentionTime = detectorRange.getRetentionTimeStart();
				int stopRetentionTime = detectorRange.getRetentionTimeStop();
				chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
				updateChromatogram(chromatogramSelection, peakChartSettings);
				/*
				 * Intensity Range
				 */
				double minY = showTraces(chromatogram, traces) ? 0.0d : getMinY(chromatogramSelection, startRetentionTime, stopRetentionTime);
				double maxY = getMaxY(chromatogramSelection, startRetentionTime, stopRetentionTime);
				selectedRangeX = new Range(startRetentionTime, stopRetentionTime);
				selectedRangeY = new Range(minY, maxY);
				adjustChartRange();
			}
		}
	}

	private boolean showTraces(IChromatogram<? extends IPeak> chromatogram, Set<Integer> traces) {

		return chromatogram instanceof IChromatogramMSD && traces.size() > 0;
	}

	private void updateChart(IPeak peak) {

		if(chromatogramSelection != null) {
			if(selectedRangeX != null) {
				if(peak != null) {
					List<IPeak> peaks = new ArrayList<>();
					peaks.add(peak);
					updatePeaks(peaks);
				}
			}
		}
	}

	private double getMinY(IChromatogramSelection<?, ?> chromatogramSelection, int startRetentionTime, int stopRetentionTime) {

		IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
		//
		double minY = Double.MAX_VALUE;
		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		for(int i = startScan; i <= stopScan; i++) {
			double intensity = chromatogram.getScan(i).getTotalSignal();
			minY = Math.min(intensity, minY);
		}
		//
		minY -= minY * PreferenceSupplier.getOffsetMinY() / 100.0d;
		//
		return minY;
	}

	private double getMaxY(IChromatogramSelection<?, ?> chromatogramSelection, int startRetentionTime, int stopRetentionTime) {

		IChromatogram<?> chromatogram = chromatogramSelection.getChromatogram();
		//
		double maxY = Double.MIN_VALUE;
		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		for(int i = startScan; i <= stopScan; i++) {
			double intensity = chromatogram.getScan(i).getTotalSignal();
			maxY = Math.max(intensity, maxY);
		}
		//
		maxY += maxY * PreferenceSupplier.getOffsetMaxY() / 100.0d;
		maxY = (maxY == 0) ? chromatogramSelection.getStopAbundance() : maxY;
		//
		return maxY;
	}

	private void adjustChartRange() {

		setRange(IExtendedChart.X_AXIS, selectedRangeX);
		setRange(IExtendedChart.Y_AXIS, selectedRangeY);
		redrawChart();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private IPeak extractPeak() {

		IPeak peak = null;
		if(detectorRange != null) {
			peak = extractPeakFromUserSelection(xStart, yStart, xStop, yStop);
			if(peak != null) {
				IChromatogram chromatogram = detectorRange.getChromatogram();
				if(chromatogram != null) {
					/*
					 * Remove/Add Peak
					 */
					if(isReplacePeak) {
						int startRetentionTime = detectorRange.getRetentionTimeStart();
						int stopRetentionTime = detectorRange.getRetentionTimeStop();
						Set<Integer> traces = detectorRange.getTraces();
						removeClosestPeak(peak, traces, chromatogram, startRetentionTime, stopRetentionTime);
					}
					//
					chromatogram.addPeak(peak);
					fireUpdate(peak);
				}
			}
		}
		return peak;
	}

	private void removeClosestPeak(IPeak peakSource, Set<Integer> traces, IChromatogram<IPeak> chromatogram, int startRetentionTime, int stopRetentionTime) {

		int retentionTimeSource = peakSource.getPeakModel().getRetentionTimeAtPeakMaximum();
		List<IPeak> peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
		IPeak peakDelete = null;
		//
		for(IPeak peak : peaks) {
			if(peakDelete == null) {
				/*
				 * Set the delete peak.
				 */
				peakDelete = peak;
			} else {
				/*
				 * Find the closest peak.
				 */
				int retentionTime = peak.getPeakModel().getRetentionTimeAtPeakMaximum();
				int retentionTimeDelete = peakDelete.getPeakModel().getRetentionTimeAtPeakMaximum();
				//
				if(Math.abs(retentionTimeSource - retentionTime) < Math.abs(retentionTimeDelete - retentionTime)) {
					peakDelete = peak;
				}
			}
		}
		/*
		 * Delete the peak if a specific peak was found.
		 */
		if(peakDelete != null) {
			chromatogram.removePeak(peakDelete);
		}
	}

	/**
	 * Extracts the selected peak.
	 * 
	 * @param xStop
	 * @param yStop
	 */
	private IPeak extractPeakFromUserSelection(int xStart, int yStart, int xStop, int yStop) {

		/*
		 * Calculate the rectangle factors.
		 * Enable to set the peak as selected with retention time and abundance range.
		 */
		IPeak peak = null;
		if(detectorRange != null) {
			Point rectangle = getBaseChart().getPlotArea().getSize();
			int width = rectangle.x;
			double factorWidth = 0.0d;
			if(width != 0) {
				factorWidth = 100.0d / width;
				double percentageStartWidth = (factorWidth * xStart) / 100.0d;
				double percentageStopWidth = (factorWidth * xStop) / 100.0d;
				/*
				 * Calculate the start and end points.
				 */
				BaseChart baseChart = getBaseChart();
				IAxis retentionTime = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
				Range millisecondsRange = retentionTime.getRange();
				/*
				 * With abundance and retention time.
				 */
				double millisecondsWidth = millisecondsRange.upper - millisecondsRange.lower;
				/*
				 * Peak start and stop abundances and retention times.
				 */
				int startRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStartWidth);
				int stopRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStopWidth);
				//
				IChromatogram<? extends IPeak> chromatogram = detectorRange.getChromatogram();
				if(chromatogram != null) {
					Set<Integer> traces = detectorRange.getTraces();
					boolean includeBackground = detectorRange.isIncludeBackground();
					boolean optimizeRange = detectorRange.isOptimizeRange();
					peak = peakSupport.extractPeakByRetentionTime(chromatogram, startRetentionTime, stopRetentionTime, includeBackground, optimizeRange, traces);
				}
			}
		}
		return peak;
	}

	private void fireUpdate(IPeak peak) {

		if(peakUpdateListener != null) {
			peakUpdateListener.update(peak);
		}
	}
}
