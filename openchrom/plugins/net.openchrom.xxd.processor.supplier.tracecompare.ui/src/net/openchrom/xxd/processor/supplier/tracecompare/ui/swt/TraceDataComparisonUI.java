/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.chemclipse.wsd.model.core.selection.IChromatogramSelectionWSD;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.SeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.swtchart.ITitle;

public class TraceDataComparisonUI extends Composite {

	private static final String FLAG_MATCHED = "FlagMatched";
	private static final int HORIZONTAL_INDENT = 15;
	//
	private Label labelTrace;
	private Button buttonEvaluated;
	private Button buttonIsMatched;
	private TraceDataUI sampleDataUI;
	private TraceDataUI referenceDataUI;
	private Text commentsText;

	public TraceDataComparisonUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setData(String trace, IChromatogramSelectionWSD sampleChromatogramSelectionWSD, IChromatogramSelectionWSD referenceChromatogramSelectionWSD) {

		IChromatogramWSD sampleChromatogramWSD = sampleChromatogramSelectionWSD.getChromatogramWSD();
		sampleDataUI.addSeriesData(getLineSeriesDataList(sampleChromatogramWSD), LineChart.MEDIUM_COMPRESSION);
		//
		IChromatogramWSD referenceChromatogramWSD = referenceChromatogramSelectionWSD.getChromatogramWSD();
		referenceDataUI.addSeriesData(getLineSeriesDataList(referenceChromatogramWSD), LineChart.MEDIUM_COMPRESSION);
		//
		setTrace(trace, sampleChromatogramWSD.getName(), referenceChromatogramWSD.getName());
	}

	private void setTrace(String trace, String sample, String reference) {

		Display display = Display.getDefault();
		Font font = new Font(display, "Arial", 14, SWT.BOLD);
		labelTrace.setFont(font);
		labelTrace.setText("Trace: " + trace);
		font.dispose();
		//
		ITitle title;
		//
		title = sampleDataUI.getBaseChart().getTitle();
		title.setText(sample + " (" + trace + ")");
		title.setForeground(Colors.BLACK);
		//
		title = referenceDataUI.getBaseChart().getTitle();
		title.setText(reference + " (" + trace + ")");
		title.setForeground(Colors.BLACK);
	}

	private List<ILineSeriesData> getLineSeriesDataList(IChromatogramWSD chromatogramWSD) {

		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		ISeriesData seriesData = getSeriesXY(chromatogramWSD);
		//
		ILineSeriesData lineSeriesData = new LineSeriesData(seriesData);
		ILineSeriesSettings lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
		//
		return lineSeriesDataList;
	}

	private ISeriesData getSeriesXY(IChromatogramWSD chromatogramWSD) {

		int size = chromatogramWSD.getNumberOfScans();
		double[] xSeries = new double[size];
		double[] ySeries = new double[size];
		//
		int i = 0;
		for(IScan scan : chromatogramWSD.getScans()) {
			xSeries[i] = scan.getRetentionTime();
			ySeries[i] = scan.getTotalSignal();
			i++;
		}
		//
		ISeriesData seriesData = new SeriesData(xSeries, ySeries, chromatogramWSD.getName());
		return seriesData;
	}

	private void createControl() {

		setLayout(new GridLayout(2, true));
		//
		createButtonSection(this);
		createCommentsSection(this);
		createTraceDataSection(this);
		//
		showComments(false);
	}

	private void createButtonSection(Composite parent) {

		labelTrace = new Label(parent, SWT.NONE);
		labelTrace.setText("");
		GridData gridDataLabel = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLabel.horizontalIndent = HORIZONTAL_INDENT;
		labelTrace.setLayoutData(gridDataLabel);
		//
		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridDataComposite);
		composite.setLayout(new GridLayout(3, false));
		//
		buttonEvaluated = new Button(composite, SWT.PUSH);
		buttonEvaluated.setText("");
		buttonEvaluated.setToolTipText("Mark this view as evaluated.");
		buttonEvaluated.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REPORT, IApplicationImage.SIZE_16x16));
		buttonEvaluated.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		//
		buttonIsMatched = new Button(composite, SWT.PUSH);
		buttonIsMatched.setText("");
		buttonIsMatched.setToolTipText("Flag as matched");
		buttonIsMatched.setData(FLAG_MATCHED, false);
		buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16));
		buttonIsMatched.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isMatched = (boolean)buttonIsMatched.getData(FLAG_MATCHED);
				if(isMatched) {
					buttonIsMatched.setData(FLAG_MATCHED, false);
					buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DESELECTED, IApplicationImage.SIZE_16x16));
				} else {
					buttonIsMatched.setData(FLAG_MATCHED, true);
					buttonIsMatched.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SELECTED, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		Button buttonFlipComments = new Button(composite, SWT.PUSH);
		buttonFlipComments.setText("");
		buttonFlipComments.setToolTipText("Show/Hide Comments");
		buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
		buttonFlipComments.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean isVisible = !commentsText.isVisible();
				showComments(isVisible);
				//
				if(isVisible) {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_COLLAPSE_ALL, IApplicationImage.SIZE_16x16));
				} else {
					buttonFlipComments.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
				}
			}
		});
	}

	private void createCommentsSection(Composite parent) {

		commentsText = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.H_SCROLL);
		commentsText.setText("");
		GridData gridData = getGridData();
		gridData.horizontalIndent = HORIZONTAL_INDENT;
		commentsText.setLayoutData(gridData);
	}

	private void createTraceDataSection(Composite parent) {

		sampleDataUI = new TraceDataUI(parent, SWT.NONE, true, false, false);
		sampleDataUI.setLayoutData(getGridData());
		//
		referenceDataUI = new TraceDataUI(parent, SWT.NONE, false, true, true);
		referenceDataUI.setLayoutData(getGridData());
		/*
		 * Link both charts.
		 */
		sampleDataUI.addLinkedScrollableChart(referenceDataUI);
		referenceDataUI.addLinkedScrollableChart(sampleDataUI);
	}

	private GridData getGridData() {

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		return gridData;
	}

	private void showComments(boolean isVisible) {

		GridData gridData = (GridData)commentsText.getLayoutData();
		gridData.exclude = !isVisible;
		commentsText.setVisible(isVisible);
		Composite parent = commentsText.getParent();
		parent.layout(false);
		parent.redraw();
	}
}
