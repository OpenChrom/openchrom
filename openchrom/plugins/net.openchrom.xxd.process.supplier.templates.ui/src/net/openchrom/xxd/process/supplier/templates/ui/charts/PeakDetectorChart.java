/*******************************************************************************
 * Copyright (c) 2019, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - add support for comments
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.charts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.baseline.IBaselineModel;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.selection.ChromatogramSelection;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IPeakUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.selection.ChromatogramSelectionMSD;
import org.eclipse.chemclipse.support.traces.ITrace;
import org.eclipse.chemclipse.support.traces.TraceFactory;
import org.eclipse.chemclipse.support.traces.TraceNominalMSD;
import org.eclipse.chemclipse.support.traces.TraceRasteredWSD;
import org.eclipse.chemclipse.ux.extension.ui.support.BaselineSelectionPaintListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.ChromatogramPeakChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.PeakChartSettings;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.ChromatogramSelectionWSD;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.core.ICustomSelectionHandler;
import org.eclipse.swtchart.extensions.core.RangeRestriction;

import net.openchrom.xxd.process.supplier.templates.model.PeakSelectionChoice;
import net.openchrom.xxd.process.supplier.templates.model.PeakSelectionCriterion;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.ui.swt.peaks.DeltaRangePaintListener;
import net.openchrom.xxd.process.supplier.templates.ui.swt.peaks.IRangeUpdateListener;
import net.openchrom.xxd.process.supplier.templates.ui.swt.peaks.ISectionUpdateListener;
import net.openchrom.xxd.process.supplier.templates.util.TracesUtil;

public class PeakDetectorChart extends ChromatogramPeakChart {

	private BaselineSelectionPaintListener baselineSelectionPaintListener;
	private Cursor defaultCursor;

	private int xStart;
	private int yStart;
	private int xStop;
	private int yStop;

	private DetectorRange detectorRange;
	private IChromatogramSelection chromatogramSelection = null;

	private IPeakUpdateListener peakUpdateListener = null;
	private ISectionUpdateListener sectionUpdateListener = null;
	private IRangeUpdateListener rangeUpdateListener = null;

	private PeakDetectorChartSettings chartSettingsDefault = new PeakDetectorChartSettings();
	private DeltaRangePaintListener deltaRangePaintListener = new DeltaRangePaintListener(this.getBaseChart());

	private boolean isReplacePeak = false;
	private int replacePeakDelta = 5000; // 5 Seconds

	public PeakDetectorChart() {

		super();
	}

	public PeakDetectorChart(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	@Override
	public void updatePeaks(List<IPeak> peaks) {

		updatePeaks(peaks, false);
	}

	@Override
	public void updatePeaks(List<IPeak> peaks, boolean hideExistingPeaks) {

		super.updatePeaks(peaks, hideExistingPeaks);
		adjustChartRange();
	}

	public void setPeakUpdateListener(IPeakUpdateListener peakUdateListener) {

		this.peakUpdateListener = peakUdateListener;
	}

	public void setSectionUpdateListener(ISectionUpdateListener sectionUpdateListener) {

		this.sectionUpdateListener = sectionUpdateListener;
	}

	public void setRangeUpdateListener(IRangeUpdateListener rangeUpdateListener) {

		this.rangeUpdateListener = rangeUpdateListener;
	}

	public void update(DetectorRange detectorRange) {

		update(detectorRange, chartSettingsDefault);
	}

	public void update(DetectorRange detectorRange, PeakDetectorChartSettings chartSettings) {

		this.detectorRange = detectorRange;
		clearSelectedRange();
		int deltaRetentionTimeLeft = chartSettings.getDeltaRetentionTimeLeft();
		int deltaRetentionTimeRight = chartSettings.getDeltaRetentionTimeRight();
		deltaRangePaintListener.setDeltaRetentionTime(deltaRetentionTimeLeft, deltaRetentionTimeRight);
		isReplacePeak = chartSettings.isReplacePeak();
		replacePeakDelta = chartSettings.getReplacePeakDelta();
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
			int x1 = xStart;
			int y1 = yStart;
			int x2 = xStop;
			int y2 = yStop;
			setCursorDefault();
			resetSelectedRange();
			/*
			 * If uncaught exceptions are thrown, the cursor
			 * reset shall be not affected.
			 */
			IPeak peak = extractPeak(x1, y1, x2, y2);
			updateChart(peak);
		}
	}

	@Override
	public void handleKeyUpEvent(Event event) {

		super.handleKeyUpEvent(event);
		if(event.keyCode == SWT.KEYPAD_ADD) {
			rangeUpdateListener.update(true);
		} else if(event.keyCode == SWT.KEYPAD_SUBTRACT) {
			rangeUpdateListener.update(false);
		}
		/*
		 * Reset the baseline marker
		 */
		setCursorDefault();
		resetSelectedRange();
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
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.setEnableCompress(!chartSettings.isBufferSelection());
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setZeroY(true);
		applySettings(chartSettings);
		/*
		 * Add the paint listeners to draw the selected peak range.
		 */
		IPlotArea plotArea = getBaseChart().getPlotArea();
		baselineSelectionPaintListener = new BaselineSelectionPaintListener();
		plotArea.addCustomPaintListener(baselineSelectionPaintListener);

		getBaseChart().addCustomRangeSelectionHandler(new ICustomSelectionHandler() {

			@Override
			public void handleUserSelection(Event event) {

				assignCurrentRangeSelection();
			}
		});
	}

	private boolean isControlKeyPressed(Event event) {

		return (event.stateMask & SWT.MOD1) == SWT.MOD1;
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

		baselineSelectionPaintListener.setX1(xStart);
		baselineSelectionPaintListener.setY1(yStart);
		baselineSelectionPaintListener.setX2(xStop);
		baselineSelectionPaintListener.setY2(yStop);

		redrawChart();
	}

	private void stopBaselineSelection(int x, int y) {

		xStop = x;
		yStop = y;
	}

	private void resetSelectedRange() {

		baselineSelectionPaintListener.reset();

		xStart = 0;
		yStart = 0;
		xStop = 0;
		yStop = 0;

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

	private void updateDetectorRange(PeakChartSettings peakChartSettings) {

		deleteSeries();
		if(detectorRange != null) {
			IChromatogram chromatogram = detectorRange.getChromatogram();
			if(chromatogram != null) {
				/*
				 * TIC/XIC
				 */
				boolean showTraces = false;
				String traces = detectorRange.getTraces();
				if(showTracesMSD(chromatogram, traces)) {
					ChromatogramSelectionMSD chromatogramSelectionMSD = new ChromatogramSelectionMSD((IChromatogramMSD)chromatogram);
					chromatogramSelectionMSD.getSelectedIons().add(TracesUtil.getTraces(traces));
					chromatogramSelection = chromatogramSelectionMSD;
					showTraces = true;
				} else if(showTracesWSD(chromatogram, traces)) {
					ChromatogramSelectionWSD chromatogramSelectionWSD = new ChromatogramSelectionWSD((IChromatogramWSD)chromatogram);
					chromatogramSelectionWSD.getSelectedWavelengths().add(TracesUtil.getTraces(traces));
					chromatogramSelection = chromatogramSelectionWSD;
					showTraces = true;
				} else {
					chromatogramSelection = new ChromatogramSelection(chromatogram);
				}
				/*
				 * Retention Time Range
				 */
				int startRetentionTime = getStartRetentionTime(chromatogram, detectorRange);
				int stopRetentionTime = getStopRetentionTime(chromatogram, detectorRange);
				chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
				updateChromatogram(chromatogramSelection, peakChartSettings);
				/*
				 * Intensity Range
				 */
				double minY = showTraces ? 0.0d : getMinY(chromatogramSelection, startRetentionTime, stopRetentionTime);
				double maxY = getMaxY(chromatogramSelection, startRetentionTime, stopRetentionTime);
				Range selectedRangeX = new Range(startRetentionTime, stopRetentionTime);
				Range selectedRangeY = new Range(minY, maxY);
				updateRange(selectedRangeX, selectedRangeY);
			}
		}
	}

	private int getStartRetentionTime(IChromatogram chromatogram, DetectorRange detectorRange) {

		int retentionTime = detectorRange.getRetentionTimeStart();
		if(retentionTime < chromatogram.getStartRetentionTime()) {
			retentionTime = chromatogram.getStartRetentionTime();
		}
		return retentionTime;
	}

	private int getStopRetentionTime(IChromatogram chromatogram, DetectorRange detectorRange) {

		int retentionTime = detectorRange.getRetentionTimeStop();
		if(retentionTime > chromatogram.getStopRetentionTime()) {
			retentionTime = chromatogram.getStopRetentionTime();
		}
		return retentionTime;
	}

	private boolean showTracesMSD(IChromatogram chromatogram, String traces) {

		return chromatogram instanceof IChromatogramMSD && !traces.isEmpty();
	}

	private boolean showTracesWSD(IChromatogram chromatogram, String traces) {

		return chromatogram instanceof IChromatogramWSD && !traces.isEmpty();
	}

	private void updateChart(IPeak peak) {

		if(chromatogramSelection != null) {
			if(peak != null) {
				List<IPeak> peaks = new ArrayList<>();
				peaks.add(peak);
				updatePeaks(peaks);
			}
		}
	}

	private double getMinY(IChromatogramSelection chromatogramSelection, int startRetentionTime, int stopRetentionTime) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();

		double minY = Double.MAX_VALUE;
		int startScan = PeakSupport.getStartScan(chromatogram, startRetentionTime);
		int stopScan = PeakSupport.getStopScan(chromatogram, stopRetentionTime);
		for(int i = startScan; i <= stopScan; i++) {
			double intensity = chromatogram.getScan(i).getTotalSignal();
			minY = Math.min(intensity, minY);
		}

		minY -= minY * PreferenceSupplier.getOffsetMinY() / 100.0d;

		return minY;
	}

	private double getMaxY(IChromatogramSelection chromatogramSelection, int startRetentionTime, int stopRetentionTime) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();

		double maxY = Double.MIN_VALUE;
		int startScan = PeakSupport.getStartScan(chromatogram, startRetentionTime);
		int stopScan = PeakSupport.getStopScan(chromatogram, stopRetentionTime);
		for(int i = startScan; i <= stopScan; i++) {
			double intensity = chromatogram.getScan(i).getTotalSignal();
			maxY = Math.max(intensity, maxY);
		}

		maxY += maxY * PreferenceSupplier.getOffsetMaxY() / 100.0d;
		maxY = (maxY == 0) ? chromatogramSelection.getStopAbundance() : maxY;

		return maxY;
	}

	private IPeak extractPeak(int xStart, int yStart, int xStop, int yStop) {

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
						removeClosestPeak(peak, chromatogram, startRetentionTime, stopRetentionTime, replacePeakDelta);
					}
					PeakSupport.addPeak(chromatogram, peak);
					fireUpdate(peak);
				}
			}
		}
		return peak;
	}

	private void removeClosestPeak(IPeak peakSource, IChromatogram chromatogram, int startRetentionTime, int stopRetentionTime, int replacePeakDelta) {

		int retentionTimeSource = peakSource.getPeakModel().getRetentionTimeAtPeakMaximum();
		List<? extends IChromatogramPeak> peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
		IPeak peakDelete = null;

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

				if(Math.abs(retentionTimeSource - retentionTime) < Math.abs(retentionTimeDelete - retentionTime)) {
					peakDelete = peak;
				}
			}
		}

		if(peakDelete != null) {
			/*
			 * Keep identification results.
			 * Delete the peak if a specific peak was found and
			 * the peak top is inside the delta range of the existing peak.
			 */
			peakSource.getTargets().addAll(peakDelete.getTargets());
			int retentionTimeTarget = peakDelete.getPeakModel().getRetentionTimeAtPeakMaximum();
			if(Math.abs(retentionTimeSource - retentionTimeTarget) <= replacePeakDelta) {
				chromatogram.getPeaks().remove(peakDelete);
			}
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
			BaseChart baseChart = getBaseChart();
			IAxisSet axisSet = baseChart.getAxisSet();
			Point rectangle = baseChart.getPlotArea().getSize();
			int width = rectangle.x;

			if(width != 0) {
				double factorWidth = 100.0d / width;
				double percentageStartWidth = (factorWidth * xStart) / 100.0d;
				double percentageStopWidth = (factorWidth * xStop) / 100.0d;
				/*
				 * Retention Time
				 */
				IAxis retentionTime = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
				Range millisecondsRange = retentionTime.getRange();
				double millisecondsWidth = millisecondsRange.upper - millisecondsRange.lower;
				int startRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStartWidth);
				int stopRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStopWidth);
				/*
				 * Allow peak selection right to left.
				 */
				if(startRetentionTime > stopRetentionTime) {
					int tmp = startRetentionTime;
					startRetentionTime = stopRetentionTime;
					stopRetentionTime = tmp;
				}
				IChromatogram chromatogram = detectorRange.getChromatogram();
				if(chromatogram != null) {
					/*
					 * General Settings
					 */
					boolean autoAdjustScanRange = false;
					PeakSelectionCriterion peakSelectionCriterion = PeakSelectionCriterion.HEIGHT_HIGHEST;
					PeakSelectionChoice peakSelectionChoice = PeakSelectionChoice.FIRST;
					String traces = detectorRange.getTraces();
					PeakType detectorType = detectorRange.getDetectorType();
					if(PeakType.MM.equals(detectorType) || PeakType.CB.equals(detectorType)) {
						double height = rectangle.y;
						if(height > 0) {
							float startIntensity;
							float stopIntensity;
							if(PeakType.MM.equals(detectorType)) {
								/*
								 * MM
								 */
								double factorY1 = 1.0d - yStart / height;
								double factorY2 = 1.0d - yStop / height;
								IAxis intensity = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
								Range intensityRange = intensity.getRange();
								double intensityHeight = intensityRange.upper - intensityRange.lower;
								startIntensity = (float)(intensityRange.lower + intensityHeight * factorY1);
								stopIntensity = (float)(intensityRange.lower + intensityHeight * factorY2);
							} else {
								/*
								 * CB
								 */
								IBaselineModel baselineModel = chromatogram.getBaselineModel();
								Set<ITrace> traceSet = getTraceSet(chromatogram, traces);
								if(traceSet != null) {
									startIntensity = baselineModel.getBackground(startRetentionTime, traceSet);
									stopIntensity = baselineModel.getBackground(stopRetentionTime, traceSet);
								} else {
									startIntensity = baselineModel.getBackground(startRetentionTime);
									stopIntensity = baselineModel.getBackground(stopRetentionTime);
								}
							}
							PeakSupport peakSupport = new PeakSupport();
							peak = peakSupport.extractPeakByRetentionTime(chromatogram, startRetentionTime, stopRetentionTime, startIntensity, stopIntensity, traces, autoAdjustScanRange, peakSelectionCriterion, peakSelectionChoice);
						}
					} else {
						/*
						 * VV, BB
						 */
						PeakSupport peakSupport = new PeakSupport();
						boolean includeBackground = detectorRange.isIncludeBackground();
						boolean optimizeRange = detectorRange.isOptimizeRange();
						peak = peakSupport.extractPeakByRetentionTime(chromatogram, startRetentionTime, stopRetentionTime, includeBackground, optimizeRange, traces, autoAdjustScanRange, peakSelectionCriterion, peakSelectionChoice);
					}
				}
			}
		}

		return peak;
	}

	private Set<ITrace> getTraceSet(IChromatogram chromatogram, String traces) {

		if(chromatogram instanceof IChromatogramMSD) {
			return new HashSet<>(TraceFactory.parseTraces(traces, TraceNominalMSD.class));
		} else if(chromatogram instanceof IChromatogramWSD) {
			return new HashSet<>(TraceFactory.parseTraces(traces, TraceRasteredWSD.class));
		}

		return null;
	}

	private void fireUpdate(IPeak peak) {

		if(peakUpdateListener != null) {
			peakUpdateListener.update(peak);
		}
	}
}