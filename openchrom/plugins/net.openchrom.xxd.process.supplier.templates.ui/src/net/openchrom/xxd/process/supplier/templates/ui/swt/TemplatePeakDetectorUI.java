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
 * Christoph Läubrich - adjust chart API
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import java.text.DecimalFormat;
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
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIon;
import org.eclipse.chemclipse.msd.model.core.support.MarkedIons;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.support.ui.workbench.DisplayUtils;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.ui.support.PartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.BaselineSelectionPaintListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.DisplayType;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.IAxis;
import org.eclipse.swtchart.IAxisSet;
import org.eclipse.swtchart.ILineSeries.PlotSymbolType;
import org.eclipse.swtchart.IPlotArea;
import org.eclipse.swtchart.LineStyle;
import org.eclipse.swtchart.Range;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.customcharts.ChromatogramChart;
import org.eclipse.swtchart.extensions.events.AbstractHandledEventProcessor;
import org.eclipse.swtchart.extensions.events.IHandledEventProcessor;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesSettings;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakProcessSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class TemplatePeakDetectorUI extends Composite {

	private PeakProcessSettings peakProcessSettings;
	//
	private static final String SERIES_ID_CHROMATOGRAM = "Chromatogram";
	private static final String SERIES_ID_PEAKS = "Peaks";
	private static final String SERIES_ID_PEAK = "Peak";
	//
	private Composite toolbarInfo;
	//
	private Button buttonNavigateLeft;
	private Text textStartRetentionTime;
	private Text textStopRetentionTime;
	private Text textTraces;
	private Button buttonOptionReplace;
	private Button buttonNavigateRight;
	private ChromatogramChart chromatogramChart;
	//
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	private PeakRetentionTimeComparator peakRetentionTimeComparator = new PeakRetentionTimeComparator(SortOrder.ASC);
	private PeakSupport peakSupport = new PeakSupport();
	//
	private BaselineSelectionPaintListener baselineSelectionPaintListener;
	private int xStart;
	private int yStart;
	private int xStop;
	private int yStop;
	private Cursor defaultCursor;

	private class PreviousPeakEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_KEY_UP;
		}

		@Override
		public int getButton() {

			return SWT.ARROW_LEFT;
		}

		@Override
		public int getStateMask() {

			return SWT.NONE;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			peakProcessSettings.decreaseSelection();
			updateSettingSelection(true);
		}
	}

	private class OptionReplacePeakEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_KEY_UP;
		}

		@Override
		public int getButton() {

			return BaseChart.KEY_CODE_d;
		}

		@Override
		public int getStateMask() {

			return SWT.NONE;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			PreferenceSupplier.toggleDetectorReplacePeak();
			buttonOptionReplace.setSelection(PreferenceSupplier.isDetectorReplacePeak());
		}
	}

	private class NextPeakEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_KEY_UP;
		}

		@Override
		public int getButton() {

			return SWT.ARROW_RIGHT;
		}

		@Override
		public int getStateMask() {

			return SWT.NONE;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			peakProcessSettings.increaseSelection();
			updateSettingSelection(true);
		}
	}

	private class MouseDownEventProcessor extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_MOUSE_DOWN;
		}

		@Override
		public int getButton() {

			return BaseChart.BUTTON_LEFT;
		}

		@Override
		public int getStateMask() {

			return SWT.CTRL;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			handleMouseDownEvent(event);
		}
	}

	private class MouseMoveEventProcessor extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_MOUSE_MOVE;
		}

		@Override
		public int getStateMask() {

			return SWT.CTRL;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			handleMouseMoveEvent(event);
		}
	}

	private class MouseUpEventProcessor extends AbstractHandledEventProcessor implements IHandledEventProcessor {

		@Override
		public int getEvent() {

			return BaseChart.EVENT_MOUSE_UP;
		}

		@Override
		public int getButton() {

			return BaseChart.BUTTON_LEFT;
		}

		@Override
		public int getStateMask() {

			return SWT.BUTTON1;
		}

		@Override
		public void handleEvent(BaseChart baseChart, Event event) {

			handleMouseUpEvent(event);
		}
	}

	public TemplatePeakDetectorUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setPeakProcessSettings(PeakProcessSettings peakProcessSettings) {

		this.peakProcessSettings = peakProcessSettings;
		updateSettingSelection(true);
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginLeft = 0;
		gridLayout.marginRight = 0;
		composite.setLayout(gridLayout);
		//
		createButtonBar(composite);
		toolbarInfo = createToolbarInfo(composite);
		createChromatogramChart(composite);
		//
		PartSupport.setCompositeVisibility(toolbarInfo, true);
	}

	private void createButtonBar(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(8, false));
		//
		buttonNavigateLeft = createButtonNavigateLeft(composite);
		textStartRetentionTime = createTextStartRetentionTime(composite);
		textStopRetentionTime = createTextStopRetentionTime(composite);
		textTraces = createTextTraces(composite);
		buttonOptionReplace = createButtonOptionReplace(composite);
		buttonNavigateRight = createButtonNavigateRight(composite);
		createButtonInfo(composite);
		createSettingsButton(composite);
	}

	private Button createButtonNavigateLeft(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Navigate to previous peak range.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				peakProcessSettings.decreaseSelection();
				updateSettingSelection(false);
			}
		});
		return button;
	}

	private Text createTextStartRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setText("");
		text.setToolTipText("Start Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Text createTextStopRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setText("");
		text.setToolTipText("Stop Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Text createTextTraces(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setText("");
		text.setToolTipText("The selected traces are listed here.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Button createButtonOptionReplace(Composite parent) {

		Button button = new Button(parent, SWT.CHECK);
		button.setText("Replace Peak");
		button.setSelection(PreferenceSupplier.isDetectorReplacePeak());
		button.setToolTipText("Enables/Disables the option to delete the selected peak.");
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleDetectorReplacePeak();
			}
		});
		return button;
	}

	private Button createButtonNavigateRight(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Navigate to next peak range.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_FORWARD, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				peakProcessSettings.increaseSelection();
				updateSettingSelection(false);
			}
		});
		return button;
	}

	private Button createButtonInfo(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle info toolbar.");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean visible = PartSupport.toggleCompositeVisibility(toolbarInfo);
				if(visible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_INFO, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		return button;
	}

	private void createSettingsButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Open the Settings");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IPreferencePage preferencePage = new PreferencePage();
				preferencePage.setTitle("Template Processor");
				//
				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", preferencePage));
				//
				PreferenceDialog preferenceDialog = new PreferenceDialog(e.display.getActiveShell(), preferenceManager);
				preferenceDialog.create();
				preferenceDialog.setMessage("Settings");
				if(preferenceDialog.open() == Window.OK) {
					try {
						applySettings();
					} catch(Exception e1) {
						MessageDialog.openError(e.display.getActiveShell(), "Settings", "Something has gone wrong to apply the settings.");
					}
				}
			}
		});
	}

	private Composite createToolbarInfo(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(1, false));
		//
		Label label = new Label(composite, SWT.NONE);
		label.setText("Detect Peak(s): CTRL + press left mouse button to select a range under the peak.");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		return composite;
	}

	private ChromatogramChart createChromatogramChart(Composite parent) {

		chromatogramChart = new ChromatogramChart(parent, SWT.BORDER);
		chromatogramChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		defaultCursor = chromatogramChart.getBaseChart().getCursor();
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = chromatogramChart.getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.addHandledEventProcessor(new PreviousPeakEvent());
		chartSettings.addHandledEventProcessor(new NextPeakEvent());
		chartSettings.addHandledEventProcessor(new OptionReplacePeakEvent());
		chartSettings.addHandledEventProcessor(new MouseDownEventProcessor());
		chartSettings.addHandledEventProcessor(new MouseMoveEventProcessor());
		chartSettings.addHandledEventProcessor(new MouseUpEventProcessor());
		chartSettings.getRangeRestriction().setZeroY(true);
		chromatogramChart.applySettings(chartSettings);
		/*
		 * Add the paint listeners to draw the selected peak range.
		 */
		IPlotArea plotArea = getPlotArea();
		baselineSelectionPaintListener = new BaselineSelectionPaintListener();
		plotArea.addCustomPaintListener(baselineSelectionPaintListener);
		//
		return chromatogramChart;
	}

	private void applySettings() {

		updateSettingSelection(false);
	}

	@SuppressWarnings("rawtypes")
	private void updateSettingSelection(boolean adjustRange) {

		BaseChart baseChart = chromatogramChart.getBaseChart();
		IAxisSet axisSet = baseChart.getAxisSet();
		IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		IAxis yAxis = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range rangeX = xAxis.getRange();
		Range rangeY = yAxis.getRange();
		//
		DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
		IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
		//
		buttonNavigateLeft.setEnabled(peakProcessSettings.hasPrevious());
		buttonNavigateRight.setEnabled(peakProcessSettings.hasNext());
		//
		chromatogramChart.deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		if(detectorSetting != null) {
			lineSeriesDataList = extractSelectedRange(chromatogramSelection, detectorSetting);
		} else {
			lineSeriesDataList = extractFullRange(chromatogramSelection);
		}
		//
		chromatogramChart.addSeriesData(lineSeriesDataList);
		if(adjustRange) {
			adjustChartRange(rangeX, rangeY);
		}
	}

	private void adjustChartRange(Range rangeX, Range rangeY) {

		BaseChart baseChart = chromatogramChart.getBaseChart();
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
		//
		PeakSupport retentionTimeSupport = new PeakSupport();
		RetentionTimeRange retentionTimeRange = retentionTimeSupport.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier());
		int startRetentionTime = retentionTimeRange.getStartRetentionTime() - retentionTimeDeltaLeft;
		int stopRetentionTime = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		//
		textStartRetentionTime.setText(decimalFormat.format(startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
		textStopRetentionTime.setText(decimalFormat.format(stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
		textTraces.setText(detectorSetting.getTraces());
		//
		chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
		IMarkedSignals<IMarkedIon> markedSignals = new MarkedIons();
		Set<Integer> traces = listUtil.extractTraces(detectorSetting.getTraces());
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
		//
		int startRetentionTime = chromatogram.getStartRetentionTime();
		int stopRetentionTime = chromatogram.getStopRetentionTime();
		textStartRetentionTime.setText(decimalFormat.format(startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
		textStopRetentionTime.setText(decimalFormat.format(stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
		textTraces.setText("");
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

	private void handleMouseDownEvent(Event event) {

		if(isControlKeyPressed(event)) {
			startBaselineSelection(event.x, event.y);
			setCursor(SWT.CURSOR_CROSS);
		}
	}

	private void handleMouseMoveEvent(Event event) {

		if(isControlKeyPressed(event)) {
			if(xStart > 0 && yStart > 0) {
				trackBaselineSelection(event.x, event.y);
			}
		}
	}

	private void handleMouseUpEvent(Event event) {

		if(isControlKeyPressed(event)) {
			stopBaselineSelection(event.x, event.y);
			extractPeak();
			setCursorDefault();
			resetSelectedRange();
			updateSettingSelection(true);
		}
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

		chromatogramChart.getBaseChart().setCursor(DisplayUtils.getDisplay().getSystemCursor(cursorId));
	}

	private void setCursorDefault() {

		chromatogramChart.getBaseChart().setCursor(defaultCursor);
	}

	private void redrawChart() {

		chromatogramChart.getBaseChart().redraw();
	}

	private IPlotArea getPlotArea() {

		return chromatogramChart.getBaseChart().getPlotArea();
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
		Point rectangle = getPlotArea().getSize();
		int width = rectangle.x;
		double factorWidth = 0.0d;
		if(width != 0) {
			factorWidth = 100.0d / width;
			/*
			 * Calculate the percentage heights and widths.
			 */
			// double percentageStartHeight = (100.0d - (factorHeight * yStart)) / 100.0d;
			// double percentageStopHeight = (100.0d - (factorHeight * yStop)) / 100.0d;
			double percentageStartWidth = (factorWidth * xStart) / 100.0d;
			double percentageStopWidth = (factorWidth * xStop) / 100.0d;
			/*
			 * Calculate the start and end points.
			 */
			BaseChart baseChart = chromatogramChart.getBaseChart();
			IAxis retentionTime = baseChart.getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
			Range millisecondsRange = retentionTime.getRange();
			// IAxis intensity = baseChart.getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
			// Range abundanceRange = intensity.getRange();
			/*
			 * With abundance and retention time.
			 */
			// double abundanceHeight = abundanceRange.upper - abundanceRange.lower;
			double millisecondsWidth = millisecondsRange.upper - millisecondsRange.lower;
			/*
			 * Peak start and stop abundances and retention times.
			 */
			int startRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStartWidth);
			int stopRetentionTime = (int)(millisecondsRange.lower + millisecondsWidth * percentageStopWidth);
			// float startAbundance = (float)(abundanceRange.lower + abundanceHeight * percentageStartHeight);
			// float stopAbundance = (float)(abundanceRange.lower + abundanceHeight * percentageStopHeight);
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
				traces = listUtil.extractTraces(detectorSetting.getTraces());
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
}
