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
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.DisplayType;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.PeakChartSupport;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtchart.extensions.core.IChartSettings;
import org.eclipse.swtchart.extensions.customcharts.ChromatogramChart;
import org.eclipse.swtchart.extensions.linecharts.ILineSeriesData;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakDetectorPage extends WizardPage {

	private Text textStartRetentionTime;
	private Text textStopRetentionTime;
	private Text textTraces;
	private ChromatogramChart chromatogramChart;
	//
	private PeakProcessSettings processSettings;
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();
	private PeakChartSupport peakChartSupport = new PeakChartSupport();
	private PeakDetectorListUtil listUtil = new PeakDetectorListUtil();
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");

	public PeakDetectorPage(String pageName, PeakProcessSettings processSettings) {
		super(pageName);
		setTitle("Modify/Add Peaks");
		setDescription("Template peak modifier/detector");
		setErrorMessage(null);
		this.processSettings = processSettings;
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//
		createNavigationBar(composite);
		chromatogramChart = createChromatogramChart(composite);
		//
		setControl(composite);
	}

	private void createNavigationBar(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(5, false));
		//
		createButtonNavigateLeft(composite);
		textStartRetentionTime = createTextStartRetentionTime(composite);
		textStopRetentionTime = createTextStopRetentionTime(composite);
		textTraces = createTextTraces(composite);
		createButtonNavigateRight(composite);
	}

	private Button createButtonNavigateLeft(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Navigate to previous setting");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_BACKWARD, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				processSettings.decreaseSelection();
				updateSettingSelection();
			}
		});
		return button;
	}

	private Text createTextStartRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Start Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Text createTextStopRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Stop Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Text createTextTraces(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Traces");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return text;
	}

	private Button createButtonNavigateRight(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Navigate to next setting");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ARROW_FORWARD, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				processSettings.increaseSelection();
				updateSettingSelection();
			}
		});
		return button;
	}

	private ChromatogramChart createChromatogramChart(Composite parent) {

		ChromatogramChart chromatogramChart = new ChromatogramChart(parent, SWT.BORDER);
		chromatogramChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = chromatogramChart.getChartSettings();
		chartSettings.setCreateMenu(true);
		chartSettings.getRangeRestriction().setZeroY(true);
		chromatogramChart.applySettings(chartSettings);
		//
		return chromatogramChart;
	}

	@SuppressWarnings("rawtypes")
	private void updateSettingSelection() {

		DetectorSetting detectorSetting = processSettings.getSelectedDetectorSetting();
		IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
		IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
		//
		chromatogramChart.deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
		//
		if(detectorSetting != null) {
			/*
			 * TODO relative retention time.
			 */
			textStartRetentionTime.setText(decimalFormat.format(detectorSetting.getStartRetentionTime() / IChromatogram.MINUTE_CORRELATION_FACTOR));
			textStopRetentionTime.setText(decimalFormat.format(detectorSetting.getStopRetentionTime() / IChromatogram.MINUTE_CORRELATION_FACTOR));
			textTraces.setText(detectorSetting.getTraces());
			//
			int startRetentionTime = detectorSetting.getStartRetentionTime();
			int stopRetentionTime = detectorSetting.getStopRetentionTime();
			chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
			IMarkedSignals<IMarkedIon> markedSignals = new MarkedIons();
			Set<Integer> traces = listUtil.extractTraces(detectorSetting.getTraces());
			for(int trace : traces) {
				markedSignals.add(new MarkedIon(trace));
			}
			//
			lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesData(chromatogramSelection, "Chromatogram", DisplayType.XIC, ChromatogramChartSupport.DERIVATIVE_NONE, Colors.BLACK, markedSignals, false));
			int i = 1;
			for(IPeak peak : chromatogram.getPeaks(startRetentionTime, stopRetentionTime)) {
				if(peakContainsTraces(peak, traces)) {
					lineSeriesDataList.add(peakChartSupport.getPeak(peak, true, false, Colors.RED, "Peak " + i++));
				}
			}
		} else {
			textStartRetentionTime.setText("");
			textStopRetentionTime.setText("");
			textTraces.setText("");
			//
			lineSeriesDataList.add(chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogram, "Chromatogram", Colors.RED));
			//
		}
		//
		chromatogramChart.addSeriesData(lineSeriesDataList);
	}

	private boolean peakContainsTraces(IPeak peak, Set<Integer> traces) {

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
		return false;
	}
}
