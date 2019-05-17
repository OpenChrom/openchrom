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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.support.charts.ChromatogramChartSupport;
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

public class PeaksPage extends WizardPage {

	private Text textSetting;
	private ChromatogramChart chromatogramChart;
	//
	private ProcessSettings processSettings;
	private ChromatogramChartSupport chromatogramChartSupport = new ChromatogramChartSupport();

	public PeaksPage(String pageName, ProcessSettings processSettings) {
		super(pageName);
		setTitle("Modify/Add Peaks");
		setDescription("Template peak modifier/detector");
		setErrorMessage(null);
		this.processSettings = processSettings;
	}

	@Override
	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
		//
		createButtonNavigateLeft(composite);
		textSetting = createTextSetting(composite);
		createButtonNavigateRight(composite);
		chromatogramChart = createChromatogramChart(composite);
		//
		setControl(composite);
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

	private Text createTextSetting(Composite parent) {

		Text text = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
		text.setText("");
		text.setToolTipText("Selected Setting");
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
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 3;
		chromatogramChart.setLayoutData(gridData);
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
		if(detectorSetting != null) {
			//
			String traces = detectorSetting.getTraces();
			textSetting.setText(detectorSetting.toString());
			//
			chromatogramChart.deleteSeries();
			List<ILineSeriesData> lineSeriesDataList = new ArrayList<>();
			IChromatogramSelection chromatogramSelection = processSettings.getChromatogramSelection();
			int startRetentionTime = detectorSetting.getStartRetentionTime();
			int stopRetentionTime = detectorSetting.getStopRetentionTime();
			chromatogramSelection.setRangeRetentionTime(startRetentionTime, stopRetentionTime);
			ILineSeriesData lineSeriesData = chromatogramChartSupport.getLineSeriesDataChromatogram(chromatogramSelection, "Chromatogram", Colors.RED);
			// ILineSeriesData lineSeriesData = chromatogramChartSupport.getLineSeriesData(chromatogramSelection, "Chromatogram", DisplayType.XIC, d, Colors.RED, markedSignals);
			lineSeriesDataList.add(lineSeriesData);
			chromatogramChart.addSeriesData(lineSeriesDataList);
		} else {
			textSetting.setText("--");
		}
	}
}
