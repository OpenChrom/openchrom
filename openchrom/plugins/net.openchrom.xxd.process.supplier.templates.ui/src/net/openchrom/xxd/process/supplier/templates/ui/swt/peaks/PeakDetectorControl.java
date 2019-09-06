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

import java.text.DecimalFormat;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.text.ValueFormat;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferencePage;
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
import org.eclipse.swt.widgets.Text;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.RetentionTimeRange;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.PeakProcessSettings;

public class PeakDetectorControl extends Composite {

	private PeakProcessSettings peakProcessSettings;
	//
	private Button optionReplace;
	private Button navigateLeft;
	private Button navigateRight;
	private Text retentionTimeStart;
	private Text retentionTimeStop;
	private Text traces;
	//
	private PeakSupport peakSupport = new PeakSupport();
	private DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");

	public PeakDetectorControl(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setInput(PeakProcessSettings peakProcessSettings) {

		this.peakProcessSettings = peakProcessSettings;
		if(peakProcessSettings != null) {
			peakProcessSettings.setControlUpdateListener(new IUpdateListener() {

				@SuppressWarnings({"rawtypes", "unchecked"})
				@Override
				public void update() {

					setOptionReplace(optionReplace);
					//
					navigateLeft.setEnabled(peakProcessSettings.hasPrevious());
					navigateRight.setEnabled(peakProcessSettings.hasNext());
					/*
					 * Section
					 */
					IChromatogramSelection chromatogramSelection = peakProcessSettings.getChromatogramSelection();
					IChromatogram<? extends IPeak> chromatogram = chromatogramSelection.getChromatogram();
					//
					int startRetentionTime;
					int stopRetentionTime;
					String traceSelection;
					//
					if(peakProcessSettings.hasNext()) {
						DetectorSetting detectorSetting = peakProcessSettings.getSelectedDetectorSetting();
						int retentionTimeDeltaLeft = (int)(PreferenceSupplier.getUiDetectorDeltaLeftMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
						int retentionTimeDeltaRight = (int)(PreferenceSupplier.getUiDetectorDeltaRightMinutes() * IChromatogram.MINUTE_CORRELATION_FACTOR);
						RetentionTimeRange retentionTimeRange = peakSupport.getRetentionTimeRange(chromatogram.getPeaks(), detectorSetting, detectorSetting.getReferenceIdentifier());
						startRetentionTime = retentionTimeRange.getStartRetentionTime() - retentionTimeDeltaLeft;
						stopRetentionTime = detectorSetting.getStopRetentionTime() + retentionTimeDeltaRight;
						traceSelection = detectorSetting.getTraces();
					} else {
						startRetentionTime = chromatogram.getStartRetentionTime();
						stopRetentionTime = chromatogram.getStopRetentionTime();
						traceSelection = "TIC";
					}
					//
					retentionTimeStart.setText(decimalFormat.format(startRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
					retentionTimeStop.setText(decimalFormat.format(stopRetentionTime / IChromatogram.MINUTE_CORRELATION_FACTOR));
					traces.setText(traceSelection);
				}
			});
		}
	}

	private void createControl() {

		setLayout(new FillLayout());
		//
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(7, false));
		//
		navigateLeft = createButtonNavigateLeft(composite);
		retentionTimeStart = createTextStartRetentionTime(composite);
		retentionTimeStop = createTextStopRetentionTime(composite);
		traces = createTextTraces(composite);
		optionReplace = createButtonOptionReplace(composite);
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
				}
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
		text.setToolTipText("The selected traces are listed here, e.g. 103,104.");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
				if(peakProcessSettings != null) {
					peakProcessSettings.applySettings();
				}
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
						if(peakProcessSettings != null) {
							peakProcessSettings.applySettings();
						}
					} catch(Exception e1) {
						MessageDialog.openError(e.display.getActiveShell(), "Settings", "Something has gone wrong to apply the settings.");
					}
				}
			}
		});
	}

	private void setOptionReplace(Button button) {

		boolean isReplace = PreferenceSupplier.isDetectorReplacePeak();
		button.setText(isReplace ? "Replace Peak" : "Add Peak");
		button.setImage(ApplicationImageFactory.getInstance().getImage(isReplace ? IApplicationImage.IMAGE_DELETE : IApplicationImage.IMAGE_ADD, IApplicationImageProvider.SIZE_16x16));
		button.setToolTipText(isReplace ? "Disable the replace peak option." : "Enable the replace peak option.");
	}
}
