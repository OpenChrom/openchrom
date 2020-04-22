/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.TargetsListUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakEditListUI;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakReviewControl extends Composite {

	private ProcessReviewSettings processSettings;
	private PeakDetectorChart peakDetectorChart;
	//
	private PeakReviewListUI peakReviewListUI;
	private PeakEditListUI peakEditListUI;
	private TargetsListUI targetListUI;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();

	public PeakReviewControl(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setPeakDetectorChart(PeakDetectorChart peakDetectorChart) {

		this.peakDetectorChart = peakDetectorChart;
	}

	public void setInput(ProcessReviewSettings processSettings) {

		this.processSettings = processSettings;
		if(processSettings != null) {
			peakReviewListUI.setInput(processSettings.getReviewSettings());
		} else {
			peakReviewListUI.setInput(null);
		}
		updateRangeSelection();
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//
		createToolbarMain(composite);
		peakReviewListUI = createTableReview(composite);
		peakEditListUI = createTablePeaks(composite);
		targetListUI = createTableTargets(composite);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));
		//
		createSettingsButton(composite);
	}

	private PeakReviewListUI createTableReview(Composite parent) {

		PeakReviewListUI listUI = new PeakReviewListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateRangeSelection();
			}
		});
		//
		return listUI;
	}

	private PeakEditListUI createTablePeaks(Composite parent) {

		PeakEditListUI listUI = new PeakEditListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = listUI.getStructuredSelection().getFirstElement();
				if(object instanceof IPeak) {
					IPeak peak = (IPeak)object;
					targetListUI.setInput(peak.getTargets());
				} else {
					targetListUI.clear();
				}
			}
		});
		//
		return listUI;
	}

	private TargetsListUI createTableTargets(Composite parent) {

		TargetsListUI listUI = new TargetsListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				//
			}
		});
		//
		return listUI;
	}

	private void createSettingsButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Open the Settings");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", new PreferencePage()));
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

	private void applySettings() {

		updateChart();
	}

	private void updateRangeSelection() {

		updateChart();
	}

	private void updateChart() {

		if(peakDetectorChart != null) {
			Object object = peakReviewListUI.getStructuredSelection().getFirstElement();
			if(object instanceof ReviewSetting) {
				ReviewSetting reviewSetting = (ReviewSetting)object;
				if(processSettings != null) {
					IChromatogram<?> chromatogram = processSettings.getChromatogram();
					if(chromatogram != null) {
						/*
						 * Settings
						 */
						int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
						int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
						int startRetentionTime = reviewSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
						int stopRetentionTime = reviewSetting.getStopRetentionTime() + retentionTimeDeltaRight;
						//
						DetectorRange detectorRange = new DetectorRange();
						detectorRange.setChromatogram(chromatogram);
						detectorRange.setRetentionTimeStart(startRetentionTime);
						detectorRange.setRetentionTimeStop(stopRetentionTime);
						detectorRange.setTraces(peakDetectorListUtil.extractTraces(reviewSetting.getTraces()));
						detectorRange.setDetectorType("VV");
						detectorRange.setOptimizeRange(true);
						//
						peakEditListUI.setInput(chromatogram.getPeaks(startRetentionTime, stopRetentionTime));
						peakDetectorChart.update(detectorRange, null);
					}
				}
			}
		}
	}
}
