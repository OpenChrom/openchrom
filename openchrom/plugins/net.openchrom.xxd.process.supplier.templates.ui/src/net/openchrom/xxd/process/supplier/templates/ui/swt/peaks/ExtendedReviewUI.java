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

import java.util.List;

import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.detector.TemplatePeakDetector;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class ExtendedReviewUI extends Composite {

	private ReviewController reviewController;
	//
	private ComboViewer comboViewerPeakType;
	private Button buttonOptimizeRange;
	private Button buttonForceTIC;
	private PeakReviewListUI peakReviewListUI;

	public ExtendedReviewUI(Composite parent, int style) {
		super(parent, style);
		createControl();
	}

	public void setReviewController(ReviewController reviewController) {

		this.reviewController = reviewController;
	}

	public void setInput(ProcessReviewSettings processSettings) {

		if(processSettings != null) {
			List<ReviewSetting> reviewSettings = processSettings.getReviewSettings();
			peakReviewListUI.setInput(reviewSettings);
			if(reviewSettings.size() > 0) {
				peakReviewListUI.getTable().select(0);
			}
		} else {
			peakReviewListUI.setInput(null);
		}
		//
		updateSelection();
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		peakReviewListUI = createTableReview(this);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(4, false));
		//
		comboViewerPeakType = createDetectorTypeComboViewer(composite);
		buttonOptimizeRange = createOptimizeRangeButton(composite);
		buttonForceTIC = createForceTicButton(composite);
		createSettingsButton(composite);
	}

	private ComboViewer createDetectorTypeComboViewer(Composite parent) {

		ComboViewer comboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof PeakType) {
					PeakType peakType = (PeakType)element;
					return peakType.toString();
				}
				return null;
			}
		});
		//
		combo.setToolTipText("Select a peak type.");
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof PeakType) {
					PeakType peakType = (PeakType)object;
					buttonOptimizeRange.setEnabled(PeakType.VV.equals(peakType));
					updateSelection();
				}
			}
		});
		//
		TemplatePeakDetector templatePeakDetector = new TemplatePeakDetector();
		PeakType[] peakTypes = templatePeakDetector.getSupportedPeakTypes();
		comboViewer.setInput(peakTypes);
		if(peakTypes.length > 0) {
			combo.select(0);
		}
		//
		return comboViewer;
	}

	private Button createOptimizeRangeButton(Composite parent) {

		Button button = new Button(parent, SWT.CHECK);
		button.setText("Optimize Range");
		button.setToolTipText("When using VV, shall the peak range be optimized?");
		button.setSelection(true);
		//
		button.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				updateSelection();
			}
		});
		//
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {

				updateSelection();
			}
		});
		//
		return button;
	}

	private Button createForceTicButton(Composite parent) {

		Button button = new Button(parent, SWT.CHECK);
		button.setText("TIC");
		button.setToolTipText("Force to show also TIC peak(s)");
		button.setSelection(true);
		//
		button.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				updateSelection();
			}
		});
		//
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {

				updateSelection();
			}
		});
		//
		return button;
	}

	private void createSettingsButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Open the Settings");
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

	private PeakReviewListUI createTableReview(Composite parent) {

		PeakReviewListUI listUI = new PeakReviewListUI(parent, SWT.BORDER);
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection();
			}
		});
		//
		return listUI;
	}

	private void applySettings() {

		updateSelection();
	}

	private ReviewSetting getReviewSetting() {

		Object object = peakReviewListUI.getStructuredSelection().getFirstElement();
		if(object instanceof ReviewSetting) {
			return (ReviewSetting)object;
		}
		return null;
	}

	private PeakType getPeakType() {

		PeakType peakType = null;
		Object object = comboViewerPeakType.getStructuredSelection().getFirstElement();
		if(object instanceof PeakType) {
			peakType = (PeakType)object;
		}
		return peakType;
	}

	private boolean isOptimizeRange() {

		return buttonOptimizeRange.getSelection();
	}

	private boolean isForceTic() {

		return buttonForceTIC.getSelection();
	}

	private void updateSelection() {

		if(reviewController != null) {
			ReviewSetting reviewSetting = getReviewSetting();
			PeakType peakType = getPeakType();
			boolean optimizeRange = isOptimizeRange();
			boolean forceTIC = isForceTic();
			reviewController.update(reviewSetting, peakType, optimizeRange, forceTIC);
		}
	}
}
