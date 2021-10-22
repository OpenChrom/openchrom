/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
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

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.updates.IUpdateListener;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.provider.AbstractLabelProvider;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.ux.extension.ui.support.PartSupport;
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

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PagePeakReview;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakReviewListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessReviewSettings;

public class ProcessReviewUI extends Composite {

	private ReviewController controller;
	//
	private ComboViewer comboViewerVisibility;
	private Composite toolbarSearch;
	private PeakReviewListUI peakReviewListUI;
	//
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
		updateComboViewerVisibility();
	}

	public int getSelection() {

		return peakReviewListUI.getTable().getSelectionIndex();
	}

	public void setSelection(int index) {

		Table table = peakReviewListUI.getTable();
		if(index >= 0 && index < table.getItemCount()) {
			table.setSelection(index);
			updateSelection();
		}
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		toolbarSearch = createToolbarSearch(this);
		peakReviewListUI = createTable(this);
		//
		PartSupport.setCompositeVisibility(toolbarSearch, false);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(5, false));
		//
		createToggleToolbarSearch(composite);
		createButtonVisibilityDetails(composite);
		comboViewerVisibility = createComboViewerVisibility(composite);
		createButtonReplacePeak(composite);
		createSettingsButton(composite);
	}

	private SearchSupportUI createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				peakReviewListUI.setSearchText(searchText, caseSensitive);
			}
		});
		//
		return searchSupportUI;
	}

	private Button createButtonVisibilityDetails(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		adjustButtonVisibilityDetails(button);
		//
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
		//
		return button;
	}

	private void adjustButtonVisibilityDetails(Button button) {

		if(PreferenceSupplier.isShowReviewDetails()) {
			button.setToolTipText("Details are active.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REVIEW_DETAILS_SHOW, IApplicationImage.SIZE_16x16));
		} else {
			button.setToolTipText("Details are deactivated.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_REVIEW_DETAILS_HIDE, IApplicationImage.SIZE_16x16));
		}
	}

	private Button createToggleToolbarSearch(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle search toolbar.");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean visible = PartSupport.toggleCompositeVisibility(toolbarSearch);
				if(visible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImage.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImage.SIZE_16x16));
				}
			}
		});
		//
		return button;
	}

	private ComboViewer createComboViewerVisibility(Composite parent) {

		ComboViewer comboViewer = new ComboViewer(parent, SWT.READ_ONLY);
		Combo combo = comboViewer.getCombo();
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setLabelProvider(new AbstractLabelProvider() {

			@Override
			public String getText(Object element) {

				if(element instanceof Visibility) {
					return ((Visibility)element).name();
				}
				return null;
			}
		});
		//
		combo.setToolTipText("Select the visibility option.");
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof Visibility) {
					Visibility visibility = (Visibility)object;
					PreferenceSupplier.setReviewVisibility(visibility);
					updateSelection();
				}
			}
		});
		//
		return comboViewer;
	}

	private Button createButtonReplacePeak(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		adjustDetectorButton(button);
		//
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.toggleReviewReplaceNearestPeak();
				adjustDetectorButton(button);
				updateSelection();
			}
		});
		//
		return button;
	}

	private void adjustDetectorButton(Button button) {

		if(PreferenceSupplier.isReviewReplaceNearestPeak()) {
			button.setToolTipText("Replace the nearest peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_REPLACE, IApplicationImage.SIZE_16x16));
		} else {
			button.setToolTipText("Add the peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_ADD, IApplicationImage.SIZE_16x16));
		}
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
				preferenceManager.addToRoot(new PreferenceNode("1", new PagePeakReview()));
				preferenceManager.addToRoot(new PreferenceNode("2", new PreferencePage()));
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

	private PeakReviewListUI createTable(Composite parent) {

		PeakReviewListUI peakReviewListUI = new PeakReviewListUI(parent, SWT.BORDER);
		Table table = peakReviewListUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		peakReviewListUI.setUpdateListener(new IUpdateListener() {

			@Override
			public void update() {

				updateSelection();
			}
		});
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection();
			}
		});
		//
		return peakReviewListUI;
	}

	private void applySettings() {

		if(controller != null) {
			controller.updateSettings();
		}
		updateSelection();
	}

	private ReviewSetting getReviewSetting() {

		Object object = peakReviewListUI.getStructuredSelection().getFirstElement();
		if(object instanceof ReviewSetting) {
			return (ReviewSetting)object;
		}
		return null;
	}

	private void updatePeakReviewList() {

		if(processSettings != null) {
			List<ReviewSetting> reviewSettings = processSettings.getReviewSettings();
			peakReviewListUI.setInput(reviewSettings);
			if(reviewSettings.size() > 0) {
				peakReviewListUI.getTable().select(0);
			}
		} else {
			peakReviewListUI.setInput(null);
		}
	}

	private void updateComboViewerVisibility() {

		if(processSettings != null) {
			IChromatogram<?> chromatogram = processSettings.getChromatogram();
			if(chromatogram instanceof IChromatogramCSD) {
				/*
				 * CSD
				 */
				Combo combo = comboViewerVisibility.getCombo();
				comboViewerVisibility.setInput(new Visibility[]{Visibility.TIC});
				combo.select(0);
			} else {
				/*
				 * MSD, WSD
				 */
				Visibility[] items = Visibility.values();
				comboViewerVisibility.setInput(items);
				Visibility visibility = PreferenceSupplier.getReviewVisibility();
				//
				exitloop:
				for(int i = 0; i < items.length; i++) {
					Visibility item = items[i];
					if(item.equals(visibility)) {
						Combo combo = comboViewerVisibility.getCombo();
						combo.select(i);
						break exitloop;
					}
				}
			}
		} else {
			comboViewerVisibility.setInput(null);
		}
	}

	private void updateSelection() {

		if(controller != null) {
			ReviewSetting reviewSetting = getReviewSetting();
			controller.update(reviewSetting);
		}
	}
}
