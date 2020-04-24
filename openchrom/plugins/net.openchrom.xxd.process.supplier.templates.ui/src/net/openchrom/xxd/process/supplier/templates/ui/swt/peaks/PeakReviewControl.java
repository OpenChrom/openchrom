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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.TargetsListUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakStatusLabelProvider;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakStatusListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakReviewControl extends Composite {

	private ProcessReviewSettings processSettings;
	private PeakDetectorChart peakDetectorChart;
	//
	private PeakReviewListUI peakReviewListUI;
	private PeakStatusListUI peakStatusListUI;
	private TargetsListUI targetListUI;
	//
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();

	public PeakReviewControl(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setPeakDetectorChart(PeakDetectorChart peakDetectorChart) {

		this.peakDetectorChart = peakDetectorChart;
		if(peakDetectorChart != null) {
			peakDetectorChart.setUpdateListener(new IUpdateListener() {

				@Override
				public void update() {

					updateEditList();
				}
			});
		}
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
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		//
		createReviewSection(sashForm);
		createPeakSection(sashForm);
		createTargetSection(sashForm);
	}

	private void createReviewSection(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//
		createToolbarMain(composite);
		peakReviewListUI = createTableReview(composite);
	}

	private void createPeakSection(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//
		createToolbarPeaks(composite);
		peakStatusListUI = createTablePeaks(composite);
	}

	private void createTargetSection(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//
		createToolbarTargets(composite);
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
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateRangeSelection();
			}
		});
		//
		return listUI;
	}

	private void createToolbarPeaks(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(2, false));
		//
		createDeleteButton(composite);
		createDeleteAllButton(composite);
	}

	private PeakStatusListUI createTablePeaks(Composite parent) {

		PeakStatusListUI listUI = new PeakStatusListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = listUI.getStructuredSelection().getFirstElement();
				List<IPeak> peaks = new ArrayList<>();
				//
				if(object instanceof IPeak) {
					IPeak peak = (IPeak)object;
					peaks.add(peak);
					targetListUI.setInput(peak.getTargets());
				} else {
					targetListUI.clear();
				}
				//
				if(peakDetectorChart != null) {
					peakDetectorChart.updatePeaks(peaks);
				}
			}
		});
		//
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {

			}

			@Override
			public void mouseDown(MouseEvent arg0) {

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

				Object object = listUI.getStructuredSelection().getFirstElement();
				if(object instanceof IPeak) {
					IPeak peak = (IPeak)object;
					if(PeakStatusLabelProvider.isPeakReviewed(peak)) {
						peak.removeClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
					} else {
						peak.addClassifier(ReviewSetting.CLASSIFIER_REVIEW_OK);
					}
					listUI.refresh();
				}
			}
		});
		//
		return listUI;
	}

	private void createToolbarTargets(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(1, false));
		//
		createDeleteButton(composite);
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

	private void createDeleteButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Delete the selected peak(s)");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
	}

	private void createDeleteAllButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Delete all peak(s)");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE_ALL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

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
						int startRetentionTime = getStartRetentionTime(reviewSetting);
						int stopRetentionTime = getStopRetentionTime(reviewSetting);
						//
						DetectorRange detectorRange = new DetectorRange();
						detectorRange.setChromatogram(chromatogram);
						detectorRange.setRetentionTimeStart(startRetentionTime);
						detectorRange.setRetentionTimeStop(stopRetentionTime);
						detectorRange.setTraces(peakDetectorListUtil.extractTraces(reviewSetting.getTraces()));
						detectorRange.setDetectorType("VV");
						detectorRange.setOptimizeRange(true);
						//
						peakDetectorChart.update(detectorRange, null);
						updateEditList();
					}
				}
			}
		}
	}

	private void updateEditList() {

		Object object = peakReviewListUI.getStructuredSelection().getFirstElement();
		if(object instanceof ReviewSetting) {
			ReviewSetting reviewSetting = (ReviewSetting)object;
			if(processSettings != null) {
				IChromatogram<?> chromatogram = processSettings.getChromatogram();
				if(chromatogram != null) {
					/*
					 * Settings
					 */
					int startRetentionTime = getStartRetentionTime(reviewSetting);
					int stopRetentionTime = getStopRetentionTime(reviewSetting);
					peakStatusListUI.setInput(chromatogram.getPeaks(startRetentionTime, stopRetentionTime));
				}
			}
		}
	}

	private int getStartRetentionTime(ReviewSetting reviewSetting) {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStartRetentionTime() - retentionTimeDeltaLeft;
		}
		return time;
	}

	private int getStopRetentionTime(ReviewSetting reviewSetting) {

		int time = 0;
		if(reviewSetting != null) {
			int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
			time = reviewSetting.getStopRetentionTime() + retentionTimeDeltaRight;
		}
		return time;
	}
}
