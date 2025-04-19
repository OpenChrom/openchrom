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

import java.util.Arrays;
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
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.ux.extension.ui.swt.IExtendedPartUI;
import org.eclipse.chemclipse.ux.extension.ui.swt.ISettingsHandler;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.Visibility;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PagePeakDetector;
import net.openchrom.xxd.process.supplier.templates.ui.preferences.PreferencePage;
import net.openchrom.xxd.process.supplier.templates.ui.swt.PeakDetectorListUI;
import net.openchrom.xxd.process.supplier.templates.ui.wizards.ProcessDetectorSettings;

public class ProcessDetectorUI extends Composite implements IExtendedPartUI {

	private static final String IMAGE_TARGET_INPUT = IApplicationImage.IMAGE_TARGET;
	private static final String TOOLTIP_TARGET_INPUT = "the target name input dialog";
	//
	private AtomicReference<Button> buttonToolbarSearch = new AtomicReference<>();
	private AtomicReference<SearchSupportUI> toolbarSearch = new AtomicReference<>();
	private AtomicReference<ComboViewer> comboViewerVisibility = new AtomicReference<>();
	private AtomicReference<PeakDetectorListUI> peakDetectorList = new AtomicReference<>();
	//
	private DetectorController controller;
	private ProcessDetectorSettings processSettings;

	public ProcessDetectorUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setController(DetectorController controller) {

		this.controller = controller;
	}

	public void setInput(ProcessDetectorSettings processSettings) {

		this.processSettings = processSettings;
		updatePeakDetectorList();
		updateComboViewerVisibility();
	}

	public int getSelection() {

		return peakDetectorList.get().getTable().getSelectionIndex();
	}

	public void setSelection(int index) {

		Table table = peakDetectorList.get().getTable();
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
		createToolbarSearch(this);
		createTable(this);
		//
		initialize();
	}

	private void initialize() {

		enableToolbar(toolbarSearch, buttonToolbarSearch.get(), IMAGE_SEARCH, TOOLTIP_SEARCH, false);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(5, false));
		//
		createButtonToggleToolbarSearch(composite);
		createButtonToggleTargetInput(composite);
		createComboViewerVisibility(composite);
		createButtonReplacePeak(composite);
		createSettingsButton(composite);
	}

	private void createButtonToggleToolbarSearch(Composite parent) {

		Button button = createButtonToggleToolbar(parent, toolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH);
		buttonToolbarSearch.set(button);
	}

	private void createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				peakDetectorList.get().setSearchText(searchText, caseSensitive);
			}
		});
		//
		toolbarSearch.set(searchSupportUI);
	}

	private Button createButtonToggleTargetInput(Composite parent) {

		Button button = new Button(parent, SWT.TOGGLE);
		button.setText("");
		updateTargetButton(button);
		//
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.setDetectorShowTargetNameDialog(!PreferenceSupplier.isDetectorShowTargetNameDialog());
				updateTargetButton(button);
			}
		});
		//
		return button;
	}

	private void updateTargetButton(Button button) {

		setButtonImage(button, IMAGE_TARGET_INPUT, PREFIX_ENABLE, PREFIX_DISABLE, TOOLTIP_TARGET_INPUT, PreferenceSupplier.isDetectorShowTargetNameDialog());
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
		//
		combo.setToolTipText("Select the visibility option.");
		GridData gridData = new GridData();
		gridData.widthHint = 150;
		combo.setLayoutData(gridData);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				Object object = comboViewer.getStructuredSelection().getFirstElement();
				if(object instanceof Visibility visibility) {
					PreferenceSupplier.setDetectorVisibility(visibility);
					updateSelection();
				}
			}
		});
		//
		comboViewerVisibility.set(comboViewer);
	}

	private Button createButtonReplacePeak(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		updateReplacePeakButton(button);
		//
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				PreferenceSupplier.setDetectorReplaceNearestPeak(!PreferenceSupplier.isDetectorReplaceNearestPeak());
				updateReplacePeakButton(button);
				updateSelection();
			}
		});
		//
		return button;
	}

	private void updateReplacePeakButton(Button button) {

		if(PreferenceSupplier.isDetectorReplaceNearestPeak()) {
			button.setToolTipText("Replace the nearest peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_REPLACE, IApplicationImageProvider.SIZE_16x16));
		} else {
			button.setToolTipText("Add the peak.");
			button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_PEAK_ADD, IApplicationImageProvider.SIZE_16x16));
		}
	}

	private void createSettingsButton(Composite parent) {

		createSettingsButton(parent, Arrays.asList(PagePeakDetector.class, PreferencePage.class), new ISettingsHandler() {

			@Override
			public void apply(Display display) {

				applySettings();
			}
		});
	}

	private void createTable(Composite parent) {

		PeakDetectorListUI peakDetectorListUI = new PeakDetectorListUI(parent, SWT.BORDER, false);
		Table table = peakDetectorListUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		peakDetectorListUI.setUpdateListener(new IUpdateListener() {

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
		peakDetectorList.set(peakDetectorListUI);
	}

	private void applySettings() {

		updateSelection();
	}

	private DetectorSetting getDetectorSetting() {

		Object object = peakDetectorList.get().getStructuredSelection().getFirstElement();
		if(object instanceof DetectorSetting detectorSetting) {
			return detectorSetting;
		}
		return null;
	}

	private void updatePeakDetectorList() {

		if(processSettings != null) {
			List<DetectorSetting> detectorSettings = processSettings.getDetectorSettings();
			peakDetectorList.get().setInput(detectorSettings);
			if(!detectorSettings.isEmpty()) {
				peakDetectorList.get().getTable().select(0);
			}
		} else {
			peakDetectorList.get().setInput(null);
		}
	}

	private void updateComboViewerVisibility() {

		if(processSettings != null) {
			IChromatogram chromatogram = processSettings.getChromatogram();
			if(chromatogram instanceof IChromatogramCSD) {
				/*
				 * CSD
				 */
				Combo combo = comboViewerVisibility.get().getCombo();
				comboViewerVisibility.get().setInput(new Visibility[]{Visibility.TIC});
				combo.select(0);
			} else {
				/*
				 * MSD, WSD
				 */
				Visibility[] items = Visibility.values();
				comboViewerVisibility.get().setInput(items);
				Visibility visibility = PreferenceSupplier.getDetectorVisibility();
				//
				exitloop:
				for(int i = 0; i < items.length; i++) {
					Visibility item = items[i];
					if(item.equals(visibility)) {
						Combo combo = comboViewerVisibility.get().getCombo();
						combo.select(i);
						break exitloop;
					}
				}
			}
		} else {
			comboViewerVisibility.get().setInput(null);
		}
	}

	private void updateSelection() {

		if(controller != null) {
			DetectorSetting detectorSetting = getDetectorSetting();
			controller.update(detectorSetting);
		}
	}
}