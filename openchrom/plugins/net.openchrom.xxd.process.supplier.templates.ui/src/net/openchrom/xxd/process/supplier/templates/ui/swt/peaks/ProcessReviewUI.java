/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt.peaks;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.chemclipse.support.ui.swt.EnhancedComboViewer;
import org.eclipse.chemclipse.support.updates.IUpdateListener;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.InformationUI;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.ux.extension.ui.swt.IExtendedPartUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.model.DetectorType;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PagePeakReview;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class ProcessReviewUI extends Composite implements IExtendedPartUI {

	private static final String IMAGE_BASELINE = IApplicationImage.IMAGE_BASELINE;
	private static final String TOOLTIP_BASELINE = "the baseline.";

	private AtomicReference<ComboViewer> comboViewerDetectorType = new AtomicReference<>();
	private AtomicReference<ComboViewer> comboViewerVisibility = new AtomicReference<>();
	private AtomicReference<Button> buttonToolbarSearch = new AtomicReference<>();
	private AtomicReference<SearchSupportUI> toolbarSearch = new AtomicReference<>();
	private AtomicReference<Button> buttonBaseline = new AtomicReference<>();
	private AtomicReference<PeakReviewListUI> peakReviewList = new AtomicReference<>();
	private AtomicReference<InformationUI> toolbarInfoControl = new AtomicReference<>();

	private ReviewController controller;
	private ProcessReviewSettings processSettings;

	public ProcessReviewUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setController(ReviewController controller) {

		this.controller = controller;
	}

	public void setInput(ProcessReviewSettings processSettings) {

		this.processSettings = processSettings;
		updatePeakReviewList();
		updateComboViewerDetectorType();
		updateComboViewerVisibility();
		updateToolbarInfo();
	}

	public int getSelection() {

		return peakReviewList.get().getTable().getSelectionIndex();
	}

	public void setSelection(int index) {

		Table table = peakReviewList.get().getTable();
		if(index >= 0 && index < table.getItemCount()) {
			table.setSelection(index);
			updateSelection();
		}
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);

		createToolbarMain(this);
		createToolbarSearch(this);
		createTable(this);
		createToolbarInfo(this);

		initialize();
	}

	private void initialize() {

		enableToolbar(toolbarSearch, buttonToolbarSearch.get(), IMAGE_SEARCH, TOOLTIP_SEARCH, false);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(7, false));

		createButtonToggleToolbarSearch(composite);
		createComboViewerDetectorType(composite);
		createButtonToggleBaseline(composite);
		createButtonVisibilityDetails(composite);
		createComboViewerVisibility(composite);
		createButtonReplacePeak(composite);
		createSettingsButton(composite);
	}

	private void createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				peakReviewList.get().setSearchText(searchText, caseSensitive);
			}
		});

		toolbarSearch.set(searchSupportUI);
	}

	private void createComboViewerDetectorType(Composite parent) {

		ComboViewer comboViewer = new EnhancedComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof DetectorType detectorType) {
					return detectorType.label();
				}
				return null;
			}
		});

		combo.setToolTipText("Select the detector type option.");
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof DetectorType detectorType) {
					if(controller != null) {
						if(MessageDialog.openQuestion(e.display.getActiveShell(), "Detector Type", "Would you like to switch the detector type to '" + detectorType.label() + "' for all listed templates?")) {
							controller.updateDetectorType(detectorType);
							controller.updateSettings();
							peakReviewList.get().refresh();
							updateSelection();
						}
					}
				}
			}
		});

		comboViewerDetectorType.set(comboViewer);
	}

	private void createButtonToggleBaseline(Composite parent) {

		Button button = new Button(parent, SWT.TOGGLE);
		button.setText("");
		updateBaselineButton(button);

		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleShowBaselineReview();
				controller.updateSettings();
				updateSelection();
				updateBaselineButton(button);
			}
		});

		buttonBaseline.set(button);
	}

	private void updateBaselineButton(Button button) {

		setButtonImage(button, IMAGE_BASELINE, PREFIX_SHOW, PREFIX_HIDE, TOOLTIP_BASELINE, PreferenceSupplier.isShowBaselineReview());
	}

	private Button createButtonVisibilityDetails(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		adjustButtonVisibilityDetails(button);

		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleShowReviewDetails();
				adjustButtonVisibilityDetails(button);
				if(controller != null) {
					controller.updateSettings();
				}
			}
		});

		return button;
	}

	private void adjustButtonVisibilityDetails(Button button) {

		if(PreferenceSupplier.isShowReviewDetails()) {
			button.setToolTipText("Details are active.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REVIEW_DETAILS_SHOW, IApplicationImageProvider.SIZE_16x16));
		} else {
			button.setToolTipText("Details are deactivated.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REVIEW_DETAILS_HIDE, IApplicationImageProvider.SIZE_16x16));
		}
	}

	private void createButtonToggleToolbarSearch(Composite parent) {

		Button button = createButtonToggleToolbar(parent, toolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH);
		buttonToolbarSearch.set(button);
	}

	private void createComboViewerVisibility(Composite parent) {

		ComboViewer comboViewer = new EnhancedComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof Visibility visibility) {
					return visibility.label();
				}
				return null;
			}
		});

		combo.setToolTipText("Select the visibility option.");
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof Visibility visibility) {
					PreferenceSupplier.setReviewVisibility(visibility);
					updateSelection();
				}
			}
		});

		comboViewerVisibility.set(comboViewer);
	}

	private Button createButtonReplacePeak(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		adjustDetectorButton(button);

		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleReviewReplaceNearestPeak();
				adjustDetectorButton(button);
				updateSelection();
			}
		});

		return button;
	}

	private void adjustDetectorButton(Button button) {

		if(PreferenceSupplier.isReviewReplaceNearestPeak()) {
			button.setToolTipText("Replace the nearest peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_REPLACE, IApplicationImageProvider.SIZE_16x16));
		} else {
			button.setToolTipText("Add the peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_ADD, IApplicationImageProvider.SIZE_16x16));
		}
	}

	private void createSettingsButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Open the Settings");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_CONFIGURE, IApplicationImageProvider.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", new PagePeakReview()));
				preferenceManager.addToRoot(new PreferenceNode("2", new PreferencePage()));

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

	private void createTable(Composite parent) {

		PeakReviewListUI peakReviewListUI = new PeakReviewListUI(parent, SWT.BORDER, false);
		Table table = peakReviewListUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));

		peakReviewListUI.setUpdateListener(new IUpdateListener() {

			@Override
			public void update() {

				updateSelection();
			}
		});

		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection();
			}
		});

		peakReviewList.set(peakReviewListUI);
	}

	private void createToolbarInfo(Composite parent) {

		InformationUI informationUI = new InformationUI(parent, SWT.NONE);
		informationUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		toolbarInfoControl.set(informationUI);
	}

	private void applySettings() {

		if(controller != null) {
			controller.updateSettings();
		}
		buttonBaseline.get().setSelection(PreferenceSupplier.isShowBaselineReview());
		updateBaselineButton(buttonBaseline.get());
		updateSelection();
	}

	private ReviewSetting getReviewSetting() {

		Object object = peakReviewList.get().getStructuredSelection().getFirstElement();
		if(object instanceof ReviewSetting reviewSetting) {
			return reviewSetting;
		}
		return null;
	}

	private void updatePeakReviewList() {

		if(processSettings != null) {
			List<ReviewSetting> reviewSettings = processSettings.getReviewSettings();
			peakReviewList.get().setInput(reviewSettings);
			if(!reviewSettings.isEmpty()) {
				peakReviewList.get().getTable().select(0);
			}
		} else {
			peakReviewList.get().setInput(null);
		}
	}

	private void updateComboViewerDetectorType() {

		ComboViewer comboViewer = comboViewerDetectorType.get();
		if(processSettings != null) {
			comboViewer.setInput(DetectorType.values());
		} else {
			comboViewer.setInput(null);
		}
	}

	private void updateComboViewerVisibility() {

		ComboViewer comboViewer = comboViewerVisibility.get();
		if(processSettings != null) {
			IChromatogram chromatogram = processSettings.getChromatogram();
			if(chromatogram instanceof IChromatogramCSD) {
				/*
				 * CSD
				 */
				Combo combo = comboViewer.getCombo();
				comboViewer.setInput(new Visibility[]{Visibility.TIC});
				combo.select(0);
			} else {
				/*
				 * MSD, WSD
				 */
				Visibility[] items = Visibility.values();
				comboViewer.setInput(items);
				Visibility visibility = PreferenceSupplier.getReviewVisibility();

				exitloop:
				for(int i = 0; i < items.length; i++) {
					Visibility item = items[i];
					if(item.equals(visibility)) {
						Combo combo = comboViewer.getCombo();
						combo.select(i);
						break exitloop;
					}
				}
			}
		} else {
			comboViewer.setInput(null);
		}
	}

	private void updateToolbarInfo() {

		if(processSettings == null) {
			toolbarInfoControl.get().setText("--");
		} else {
			toolbarInfoControl.get().setText("Review Settings: " + processSettings.getReviewSettings().size());
		}
	}

	private void updateSelection() {

		if(controller != null) {
			controller.update(getReviewSetting());
		}
	}
}