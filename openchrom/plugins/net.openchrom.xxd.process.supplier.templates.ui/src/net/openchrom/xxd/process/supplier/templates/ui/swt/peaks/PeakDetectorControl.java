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
 * Christoph Läubrich - paint comment on chart
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.support.IRetentionTimeRange;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;
import net.openchrom.xxd.process.supplier.templates.util.RetentionTimeValidator;
import net.openchrom.xxd.process.supplier.templates.util.TracesValidator;

public class PeakDetectorControl extends Composite {

	private ProcessDetectorSettings peakProcessSettings;
	//
	private Button optionReplace;
	private Button navigateLeft;
	private Button navigateRight;
	private Text retentionTimeStart;
	private Text retentionTimeStop;
	private Text traces;
	private Button updateChart;
	//
	private PeakDetectorChart peakDetectorChart;
	//
	private PeakSupport peakSupport = new PeakSupport();
	private PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
	//
	private RetentionTimeValidator retentionTimeValidator = new RetentionTimeValidator();
	private TracesValidator tracesValidator = new TracesValidator();

	public PeakDetectorControl(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setPeakDetectorChart(PeakDetectorChart peakDetectorChart) {

		this.peakDetectorChart = peakDetectorChart;
	}

	public void setInput(ProcessDetectorSettings peakProcessSettings) {

		this.peakProcessSettings = peakProcessSettings;
		updateRangeSelection();
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(8, false));
		//
		navigateLeft = createButtonNavigateLeft(composite);
		retentionTimeStart = createTextStartRetentionTime(composite);
		retentionTimeStop = createTextStopRetentionTime(composite);
		traces = createTextTraces(composite);
		optionReplace = createButtonOptionReplace(composite);
		updateChart = createButtonUpdateChart(composite);
		navigateRight = createButtonNavigateRight(composite);
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

				if(peakProcessSettings != null) {
					peakProcessSettings.decreaseSelection();
					updateRangeSelection();
				}
			}
		});
		return button;
	}

	private Text createTextStartRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Start Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		RetentionTimeValidator retentionTimeValidator = new RetentionTimeValidator();
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				validate(retentionTimeValidator, controlDecoration, text.getText());
				validateSettings();
			}
		});
		//
		return text;
	}

	private Text createTextStopRetentionTime(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("Stop Retention Time (Minutes)");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		RetentionTimeValidator retentionTimeValidator = new RetentionTimeValidator();
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				validate(retentionTimeValidator, controlDecoration, text.getText());
				validateSettings();
			}
		});
		//
		return text;
	}

	private Text createTextTraces(Composite parent) {

		Text text = new Text(parent, SWT.BORDER);
		text.setText("");
		text.setToolTipText("The selected traces are listed here, e.g. 103,104.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		TracesValidator tracesValidator = new TracesValidator();
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				validate(tracesValidator, controlDecoration, text.getText());
				validateSettings();
			}
		});
		//
		return text;
	}

	private Button createButtonOptionReplace(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		setOptionReplace(button);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleDetectorReplacePeak();
				setOptionReplace(button);
			}
		});
		return button;
	}

	private Button createButtonUpdateChart(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Update the chart.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXECUTE, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateChart();
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

				if(peakProcessSettings != null) {
					peakProcessSettings.increaseSelection();
					updateRangeSelection();
				}
			}
		});
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
						updateRangeSelection();
					} catch(Exception e1) {
						MessageDialog.openError(e.display.getActiveShell(), "Settings", "Something has gone wrong to apply the settings.");
					}
				}
			}
		});
	}

	private void setOptionReplace(Button button) {

		boolean isReplace = PreferenceSupplier.isDetectorReplacePeak();
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(isReplace ? IApplicationImage.IMAGE_DELETE : IApplicationImage.IMAGE_ADD, IApplicationImageProvider.SIZE_16x16));
		button.setToolTipText(isReplace ? "Disable the replace peak option." : "Enable the replace peak option.");
	}

	private void updateRangeSelection() {

		updateWidgets();
		updateChart();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void updateWidgets() {

		if(peakProcessSettings != null) {
			IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
			IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
			//
			navigateLeft.setEnabled(peakProcessSettings.hasPrevious());
			navigateRight.setEnabled(peakProcessSettings.hasNext());
			setOptionReplace(optionReplace);
			//
			int startRetentionTime;
			int stopRetentionTime;
			String traceSelection;
			boolean enableWidgets;
			//
			if(peakProcessSettings.hasNext()) {
				/*
				 * Selected Range
				 */
				DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
				int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
				int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
				IRetentionTimeRange retentionTimeRange = peakSupport.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier());
				startRetentionTime = retentionTimeRange.getStartRetentionTime() - retentionTimeDeltaLeft;
				stopRetentionTime = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
				traceSelection = detectorSetting.getTraces();
				enableWidgets = false; // No modification allowed
			} else {
				/*
				 * Chromatogram
				 */
				startRetentionTime = chromatogram.getStartRetentionTime();
				stopRetentionTime = chromatogram.getStopRetentionTime();
				traceSelection = "";
				enableWidgets = true; // Modification allowed
			}
			//
			retentionTimeStart.setText(decimalFormat.format(startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
			retentionTimeStop.setText(decimalFormat.format(stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
			traces.setText(traceSelection);
			//
			updateChart.setEnabled(enableWidgets);
			retentionTimeStart.setEnabled(enableWidgets);
			retentionTimeStop.setEnabled(enableWidgets);
			traces.setEnabled(enableWidgets && (chromatogram instanceof IChromatogramMSD));
		}
	}

	private boolean validate(IValidator validator, ControlDecoration controlDecoration, String text) {

		IStatus status = validator.validate(text);
		if(status.isOK()) {
			controlDecoration.hide();
			return true;
		} else {
			controlDecoration.setImage(FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage());
			controlDecoration.showHoverText(status.getMessage());
			controlDecoration.show();
			return false;
		}
	}

	private void validateSettings() {

		updateChart.setEnabled(false);
		//
		if(retentionTimeValidator.validate(retentionTimeStart.getText()).isOK()) {
			if(retentionTimeValidator.validate(retentionTimeStop.getText()).isOK()) {
				int startRetentionTime = getRetentionTime(retentionTimeStart.getText());
				int stopRetentionTime = getRetentionTime(retentionTimeStop.getText());
				if(startRetentionTime < stopRetentionTime) {
					if(tracesValidator.validate(traces.getText()).isOK()) {
						updateChart.setEnabled(true);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void updateChart() {

		if(peakDetectorChart != null && peakProcessSettings != null) {
			DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
			if(detectorSetting != null) {
				/*
				 * Detector Range
				 */
				DetectorRange detectorRange = new DetectorRange();
				detectorRange.setChromatogram(peakProcessSettings.getChromatogramSelection().getChromatogram());
				detectorRange.setRetentionTimeStart(getRetentionTime(retentionTimeStart.getText()));
				detectorRange.setRetentionTimeStop(getRetentionTime(retentionTimeStop.getText()));
				detectorRange.setTraces(peakDetectorListUtil.extractTraces(traces.getText()));
				detectorRange.setDetectorType(detectorSetting.getDetectorType().name());
				detectorRange.setOptimizeRange(detectorSetting.isOptimizeRange());
				//
				peakDetectorChart.update(detectorRange);
			}
		}
	}

	private int getRetentionTime(String value) {

		int retentionTime = 0;
		try {
			double retentionTimeMinutes = Double.parseDouble(value.trim());
			retentionTime = (int)(retentionTimeMinutes * IChromatogram.MINUTE_CORRELATION_FACTOR);
		} catch(NumberFormatException e) {
			//
		}
		return retentionTime;
	}
}
