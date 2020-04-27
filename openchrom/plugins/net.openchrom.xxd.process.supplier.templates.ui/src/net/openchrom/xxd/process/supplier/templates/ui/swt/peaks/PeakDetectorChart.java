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
import org.eclipse.chemclipse.model.core.IMarkedSignals;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.selection.ChromatogramSelection;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIons;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.custom.ChromatogramPeakChart;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.BaselineSelectionPaintListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.DisplayType;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.Derivative;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
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
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;

public class PeakDetectorChart extends ChromatogramPeakChart {

	private static final String SERIES_ID_CHROMATOGRAM_TIC = "Chromatogram TIC";
	private static final String SERIES_ID_CHROMATOGRAM_XIC = "Chromatogram XIC";
	private static final String SERIES_ID_PEAK_TIC = "Peak TIC";
	private static final String SERIES_ID_PEAK_XIC = "Peak XIC";
	private static final String SERIES_ID_BASELINE_TIC = "Baseline TIC";
	private static final String SERIES_ID_BASELINE_XIC = "Baseline XIC";
	//
	private ReviewController reviewController;
	//
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
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	//
	private DetectorRange detectorRange;
	private boolean forceTIC;
	//
	private IUpdateListener updateListener = null;

	public PeakDetectorChart(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	public void setReviewController(ReviewController reviewController) {

		this.reviewController = reviewController;
	}

	public void updatePeaks(List<IPeak> peaks) {

		super.updatePeaks(peaks);
		// if(peaks != null && peaks.size() > 0) {
		// double minY = Double.MAX_VALUE;
		// double maxY = Double.MIN_VALUE;
		// for(IPeak peak : peaks) {
		// IPeakModel peakModel = peak.getPeakModel();
		// float background = peakModel.getBackgroundAbundance();
		// float abdundance = peakModel.getPeakAbundance();
		// minY = Math.min(minY, background);
		// maxY = Math.max(maxY, background + abdundance);
		// }
		// /*
		// * Set an offset of 20%.
		// */
		// maxY = maxY * 0.2d;
		// selectedRangeY = new Range(minY, maxY);
		// }
		adjustChartRange();
	}

	public boolean isForceTIC() {

		return forceTIC;
	}

	public void setForceTIC(boolean forceTIC) {

		this.forceTIC = forceTIC;
	}

	public void setUpdateListener(IUpdateListener updateListener) {

		this.updateListener = updateListener;
	}

	public void update() {

		updateDetectorRange();
	}

	public void update(DetectorRange detectorRange) {

		this.detectorRange = detectorRange;
		selectedRangeX = null;
		selectedRangeY = null;
		updateDetectorRange();
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
			extractPeak();
			setCursorDefault();
			resetSelectedRange();
			updateDetectorRange();
		}
	}

	private void initialize() {

		defaultCursor = getBaseChart().getCursor();
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.addHandledEventProcessor(new ReplacePeakToggleEvent());
		chartSettings.getRangeRestriction().setZeroY(true);
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
	private void updateDetectorRange() {

		deleteSeries();
		if(detectorRange != null) {
			IChromatogram<? extends IPeak> chromatogram = detectorRange.getChromatogram();
			if(chromatogram != null) {
				/*
				 * PeakChart
				 */
				IChromatogramSelection chromatogramSelection = new ChromatogramSelection(chromatogram);
				int startRetentionTime = detectorRange.getRetentionTimeStart();
				int stopRetentionTime = detectorRange.getRetentionTimeStop();
				chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
				updateChromatogram(chromatogramSelection);
				selectedRangeX = new Range(startRetentionTime, stopRetentionTime);
				adjustChartRange();
				/*
				 * TIC/XIC
				 */
				// addSeriesData(extractDataRange(chromatogram));
				// adjustChartRange();
			}
		}
	}

	private void adjustChartRange() {

		setRange(IExtendedChart.X_AXIS, selectedRangeX);
		setRange(IExtendedChart.Y_AXIS, selectedRangeY);
		redrawChart();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void extractPeak() {

		if(detectorRange != null) {
			IPeak peak = extractPeakFromUserSelection(xStart, yStart, xStop, yStop);
			if(peak != null) {
				IChromatogram chromatogram = detectorRange.getChromatogram();
				if(chromatogram != null) {
					/*
					 * Remove/Add Peak
					 */
					if(PreferenceSupplier.isDetectorReplacePeak()) {
						int startRetentionTime = detectorRange.getRetentionTimeStart();
						int stopRetentionTime = detectorRange.getRetentionTimeStop();
						Set<Integer> traces = detectorRange.getTraces();
						removeClosestPeak(peak, chromatogram, startRetentionTime, stopRetentionTime, traces);
					}
					chromatogram.addPeak(peak);
					fireUpdate();
				}
			}
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void removeClosestPeak(IPeak peak, IChromatogram chromatogram, int startRetentionTime, int stopRetentionTime, Set<Integer> traces) {

		boolean useTraces = traces.size() > 0;
		IPeak peakToDelete = null;
		for(Object object : chromatogram.getPeaks(startRetentionTime, stopRetentionTime)) {
			/*
			 * Check for traces.
			 */
			IPeak peakX = (IPeak)object;
			if(useTraces) {
				if(!peakMatchesTraces(peakX, traces)) {
					peakX = null;
				}
			}
			/*
			 * Select peak for deletion?
			 */
			if(peakX != null) {
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

	@SuppressWarnings({"rawtypes", "unchecked"})
	private List<ILineSeriesData> extractDataRange(IChromatogram<? extends IPeak> chromatogram) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		if(detectorRange != null) {
			int startRetentionTime = detectorRange.getRetentionTimeStart();
			int stopRetentionTime = detectorRange.getRetentionTimeStop();
			Set<Integer> traces = detectorRange.getTraces();
			//
			IChromatogramSelection chromatogramSelection = new ChromatogramSelection(chromatogram);
			chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
			/*
			 * Chromatogram Series
			 */
			if(chromatogram instanceof IChromatogramMSD) {
				if(traces.size() > 0) {
					IMarkedSignals<IMarkedIon> markedSignals = extractMarkedSignals(traces);
					lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, SERIES_ID_CHROMATOGRAM_XIC, DisplayType.XIC, Derivative.NONE, Colors.RED, markedSignals, false));
					if(forceTIC) {
						lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, SERIES_ID_CHROMATOGRAM_TIC, DisplayType.TIC, Derivative.NONE, Colors.BLACK, null, false));
					}
				} else {
					lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, SERIES_ID_CHROMATOGRAM_TIC, DisplayType.TIC, Derivative.NONE, Colors.RED, null, false));
				}
			} else {
				lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, SERIES_ID_CHROMATOGRAM_TIC, DisplayType.TIC, Derivative.NONE, Colors.RED, null, false));
			}
			/*
			 * Peaks Series
			 */
			lineSeriesDataList.addAll(extractPeaksSeries(chromatogram, startRetentionTime, stopRetentionTime, traces));
		}
		//
		return lineSeriesDataList;
	}

	private IMarkedSignals<IMarkedIon> extractMarkedSignals(Set<Integer> traces) {

		IMarkedSignals<IMarkedIon> markedSignals = new MarkedIons(IMarkedIons.IonMarkMode.INCLUDE);
		for(int trace : traces) {
			markedSignals.add(new MarkedIon(trace));
		}
		return markedSignals;
	}

	private List<ILineSeriesData> extractPeaksSeries(IChromatogram<? extends IPeak> chromatogram, int startRetentionTime, int stopRetentionTime, Set<Integer> traces) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		int i = 1;
		for(IPeak peak : chromatogram.getPeaks(startRetentionTime, stopRetentionTime)) {
			if(traces.size() > 0 && peak instanceof IPeakMSD) {
				if(peakMatchesTraces(peak, traces)) {
					addPeakSeries(lineSeriesDataList, peak, true, i++);
				}
			} else {
				addPeakSeries(lineSeriesDataList, peak, false, i++);
			}
		}
		return lineSeriesDataList;
	}

	private void addPeakSeries(List<ILineSeriesData> lineSeriesDataList, IPeak peak, boolean xic, int i) {

		String idPeak = xic ? SERIES_ID_PEAK_XIC : SERIES_ID_PEAK_TIC;
		String idBaseline = xic ? SERIES_ID_BASELINE_XIC : SERIES_ID_BASELINE_TIC;
		//
		lineSeriesDataList.add(peakChartSupport.getPeak(peak, true, false, Colors.RED, idPeak + i));
		lineSeriesDataList.add(peakChartSupport.getPeakBaseline(peak, false, Colors.BLACK, idBaseline + i));
	}

	private boolean peakMatchesTraces(IPeak peak, Set<Integer> traces) {

		if(peak != null && traces != null) {
			IScan scan = peak.getPeakModel().getPeakMaximum();
			if(scan instanceof IScanMSD) {
				IScanMSD scanMSD = (IScanMSD)scan;
				if(traces.size() == scanMSD.getIons().size()) {
					IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
					for(int trace : traces) {
						if(extractedIonSignal.getAbundance(trace) > 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void fireUpdate() {

		if(updateListener != null) {
			updateListener.update();
		}
	}
}
