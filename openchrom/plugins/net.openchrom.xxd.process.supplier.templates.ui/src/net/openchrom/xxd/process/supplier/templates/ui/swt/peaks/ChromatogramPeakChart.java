/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.comparator.PeakRetentionTimeComparator;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IMarkedSignals;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIons;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.charts.ChromatogramChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.BaselineSelectionPaintListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.DisplayType;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakProcessSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class ChromatogramPeakChart extends ChromatogramChart {

	private static final String SERIES_ID_CHROMATOGRAM = "Chromatogram";
	private static final String SERIES_ID_PEAKS = "Peaks";
	private static final String SERIES_ID_PEAK = "Peak";
	//
	private BaselineSelectionPaintListener baselineSelectionPaintListener;
	private Cursor defaultCursor;
	//
	private int xStart;
	private int yStart;
	private int xStop;
	private int yStop;
	//
	private PeakSupport peakSupport = new PeakSupport();
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	private PeakRetentionTimeComparator peakRetentionTimeComparator = new PeakRetentionTimeComparator(SortOrder.ASC);
	//
	private PeakProcessSettings peakProcessSettings;

	public ChromatogramPeakChart(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public void setInput(PeakProcessSettings peakProcessSettings) {

		this.peakProcessSettings = peakProcessSettings;
		if(peakProcessSettings != null) {
			peakProcessSettings.setChartUpdateListener(new IUpdateListener() {

				@Override
				public void update() {

					updateSettingSelection(false);
				}
			});
		}
		updateSettingSelection(true);
	}

	public void handleMouseDownEvent(Event event) {

		super.handleMouseDownEvent(event);
		if(isControlKeyPressed(event)) {
			startBaselineSelection(event.x, event.y);
			setCursor(SWT.CURSOR_CROSS);
		}
	}

	public void handleMouseMoveEvent(Event event) {

		super.handleMouseMoveEvent(event);
		if(isControlKeyPressed(event)) {
			if(xStart > 0 && yStart > 0) {
				trackBaselineSelection(event.x, event.y);
			}
		}
	}

	public void handleMouseUpEvent(Event event) {

		super.handleMouseUpEvent(event);
		if(isControlKeyPressed(event)) {
			stopBaselineSelection(event.x, event.y);
			extractPeak();
			setCursorDefault();
			resetSelectedRange();
			updateSettingSelection(true);
		}
	}

	protected void nextPeakSection() {

		peakProcessSettings.increaseSelection();
		updateSettingSelection(true);
	}

	protected void previousPeakSection() {

		peakProcessSettings.decreaseSelection();
		updateSettingSelection(true);
	}

	protected void toggleReplacePeak() {

		PreferenceSupplier.toggleDetectorReplacePeak();
		if(peakProcessSettings != null) {
			peakProcessSettings.updateControl();
		}
	}

	private void initialize() {

		defaultCursor = getBaseChart().getCursor();
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.addHandledEventProcessor(new PreviousPeakEvent(this));
		chartSettings.addHandledEventProcessor(new NextPeakEvent(this));
		chartSettings.addHandledEventProcessor(new ReplacePeakToggleEvent(this));
		chartSettings.getRangeRestriction().setZeroY(true);
		applySettings(chartSettings);
		/*
		 * Add the paint listeners to draw the selected peak range.
		 */
		IPlotArea plotArea = getBaseChart().getPlotArea();
		baselineSelectionPaintListener = new BaselineSelectionPaintListener();
		plotArea.addCustomPaintListener(baselineSelectionPaintListener);
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

		getBaseChart().setCursor(DisplayUtils.getDisplay().getSystemCursor(cursorId));
	}

	private void setCursorDefault() {

		getBaseChart().setCursor(defaultCursor);
	}

	private void redrawChart() {

		getBaseChart().redraw();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void extractPeak() {

		IPeak peak = extractPeakFromUserSelection(xStart, yStart, xStop, yStop);
		if(peak != null) {
			IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
			IChromatogram chromatogram = chromatogramSelection.getChromatogram();
			/*
			 * Remove the closest peak?
			 */
			if(PreferenceSupplier.isDetectorReplacePeak()) {
				removeClosestPeak(peak, chromatogramSelection);
			}
			/*
			 * Add the new peak.
			 */
			chromatogram.addPeak(peak);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void removeClosestPeak(IPeak peak, IChromatogramSelection chromatogramSelection) {

		IPeak peakToDelete = null;
		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		//
		for(Object object : chromatogram.getPeaks(chromatogramSelection)) {
			IPeak peakX = (IPeak)object;
			//
			if(peakToDelete == null) {
				peakToDelete = peakX;
			} else {
				int peakRetentionTime = peak.getPeakModel().getRetentionTimeAtPeakMaximum();
				int peakRetentionTimeX = peakX.getPeakModel().getRetentionTimeAtPeakMaximum();
				int peakRetentionTimeD = peakToDelete.getPeakModel().getRetentionTimeAtPeakMaximum();
				//
				if(Math.abs(peakRetentionTime - peakRetentionTimeX) < Math.abs(peakRetentionTimeD - peakRetentionTimeX)) {
					peakToDelete = peakX;
				}
			}
		}
		//
		if(peakToDelete != null) {
			chromatogram.removePeak(peakToDelete);
		}
	}

	/**
	 * Extracts the selected peak.
	 * 
	 * @param xStop
	 * @param yStop
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private IPeak extractPeakFromUserSelection(int xStart, int yStart, int xStop, int yStop) {

		/*
		 * Calculate the rectangle factors.
		 * Enable to set the peak as selected with retention time and abundance range.
		 */
		IPeak peak = null;
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
			IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
			IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
			DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
			//
			Set<Integer> traces;
			boolean includeBackground;
			boolean optimizeRange;
			//
			if(detectorSetting != null) {
				traces = peakDetectorListUtil.extractTraces(detectorSetting.getTraces());
				includeBackground = detectorSetting.isIncludeBackground();
				optimizeRange = detectorSetting.isOptimizeRange();
			} else {
				traces = new HashSet<>();
				includeBackground = false;
				optimizeRange = true;
			}
			//
			peak = peakSupport.extractPeakByRetentionTime(chromatogram, startRetentionTime, stopRetentionTime, includeBackground, optimizeRange, traces);
		}
		return peak;
	}

	@SuppressWarnings("rawtypes")
	private void updateSettingSelection(boolean adjustRange) {

		BaseChart baseChart = getBaseChart();
		IAxisSet axisSet = baseChart.getAxisSet();
		IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxis = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = xAxis.getRange();
		Range rangeY = yAxis.getRange();
		//
		DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
		IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
		//
		if(peakProcessSettings != null) {
			peakProcessSettings.updateControl();
		}
		//
		deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		if(detectorSetting != null) {
			lineSeriesDataList = extractSelectedRange(chromatogramSelection, detectorSetting);
		} else {
			lineSeriesDataList = extractFullRange(chromatogramSelection);
		}
		//
		addSeriesData(lineSeriesDataList);
		if(adjustRange) {
			adjustChartRange(rangeX, rangeY);
		}
	}

	private void adjustChartRange(Range rangeX, Range rangeY) {

		BaseChart baseChart = getBaseChart();
		IAxisSet axisSet = baseChart.getAxisSet();
		/*
		 * Axis X
		 */
		if(rangeX.lower != 0.0d && rangeX.upper != 1.0d) {
			IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
			xAxis.setRange(rangeX);
		}
		/*
		 * Axis Y
		 */
		if(rangeY.lower != 0.0d && rangeY.upper != 1.0d) {
			IAxis yAxis = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
			yAxis.setRange(rangeY);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private List<ILineSeriesData> extractSelectedRange(IChromatogramSelection chromatogramSelection, DetectorSetting detectorSetting) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
		//
		int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
		RetentionTimeRange retentionTimeRange = peakSupport.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier());
		int startRetentionTime = retentionTimeRange.getStartRetentionTime() - retentionTimeDeltaLeft;
		int stopRetentionTime = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		//
		if(peakProcessSettings != null) {
			peakProcessSettings.updateControl();
		}
		//
		chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
		IMarkedSignals<IMarkedIon> markedSignals = new MarkedIons();
		Set<Integer> traces = peakDetectorListUtil.extractTraces(detectorSetting.getTraces());
		for(int trace : traces) {
			markedSignals.add(new MarkedIon(trace));
		}
		//
		lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, SERIES_ID_CHROMATOGRAM, DisplayType.XIC, ChromatogramChartSupport.DERIVATIVE_NONE, Colors.BLACK, markedSignals, false));
		lineSeriesDataList.addAll(extractPeaksSeries(chromatogram, startRetentionTime, stopRetentionTime, traces));
		//
		return lineSeriesDataList;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private List<ILineSeriesData> extractFullRange(IChromatogramSelection chromatogramSelection) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
		int startRetentionTime = chromatogram.getStartRetentionTime();
		int stopRetentionTime = chromatogram.getStopRetentionTime();
		//
		if(peakProcessSettings != null) {
			peakProcessSettings.updateControl();
		}
		//
		lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogram, SERIES_ID_CHROMATOGRAM, Colors.RED));
		lineSeriesDataList.add(extractPeaksSeries(chromatogram));
		lineSeriesDataList.addAll(extractPeaksSeries(chromatogram, startRetentionTime, stopRetentionTime, null));
		//
		return lineSeriesDataList;
	}

	private ILineSeriesData extractPeaksSeries(IChromatogram<? extends IPeak> chromatogram) {

		List<IPeak> peaks = new ArrayList<>(chromatogram.getPeaks());
		Collections.sort(peaks, peakRetentionTimeComparator);
		ILineSeriesData lineSeriesData = peakChartSupport.getPeaks(peaks, true, false, Colors.DARK_GRAY, SERIES_ID_PEAKS);
		ILineSeriesSettings lineSeriesSettings = lineSeriesData.getSettings();
		lineSeriesSettings.setEnableArea(false);
		lineSeriesSettings.setLineStyle(LineStyle.NONE);
		lineSeriesSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
		lineSeriesSettings.setSymbolSize(5);
		lineSeriesSettings.setSymbolColor(Colors.DARK_GRAY);
		//
		return lineSeriesData;
	}

	private List<ILineSeriesData> extractPeaksSeries(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, Set<Integer> traces) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		//
		int i = 1;
		for(IPeak peak : chromatogram.getPeaks(startRetentionTime, stopRetentionTime)) {
			if(traces != null && peakContainsTraces(peak, traces)) {
				lineSeriesDataList.add(peakChartSupport.getPeak(peak, true, false, Colors.RED, SERIES_ID_PEAK + i++));
			} else {
				lineSeriesDataList.add(peakChartSupport.getPeak(peak, true, false, Colors.RED, SERIES_ID_PEAK + i++));
			}
		}
		//
		return lineSeriesDataList;
	}

	private boolean peakContainsTraces(IPeak peak, Set<Integer> traces) {

		if(peak != null && traces != null) {
			IScan scan = peak.getPeakModel().getPeakMaximum();
			if(scan instanceof IScanMSD) {
				IScanMSD scanMSD = (IScanMSD)scan;
				IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
				for(int trace : traces) {
					if(extractedIonSignal.getAbundance(trace) > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
