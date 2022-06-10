/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.ITargetSupplier;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.targets.ITarget;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.model.updates.ITargetUpdateListener;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.comparator.SortOrder;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.swt.ui.services.IScanIdentifierService;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.xxd.ui.Activator;
import org.eclipse.chemclipse.ux.extension.xxd.ui.calibration.IUpdateListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.preferences.PreferencePageTargets;
import org.eclipse.chemclipse.ux.extension.xxd.ui.preferences.PreferencePageTargetsList;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.IExtendedPartUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ISettingsHandler;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.ScanIdentifierUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.TargetsListUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.targets.ComboTarget;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swtchart.extensions.core.IKeyboardSupport;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;

public class ExtendedTargetsUI extends Composite implements IExtendedPartUI {

	private static final String MENU_CATEGORY_TARGETS = "Targets";
	//
	private ComboTarget comboTarget;
	private Button buttonTargetAdd;
	private Button buttonTargetDelete;
	private Button buttonToolbarSearch;
	private Button buttonTableEdit;
	//
	private AtomicReference<ScanIdentifierUI> identifierControl = new AtomicReference<>();
	private AtomicReference<SearchSupportUI> toolbarSearch = new AtomicReference<>();
	private AtomicReference<TargetsListUI> targetListControl = new AtomicReference<>();
	//
	private ReviewController controller;
	private ReviewSetting reviewSetting;
	private IPeak peak;

	public ExtendedTargetsUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setController(ReviewController controller) {

		this.controller = controller;
	}

	public void setInput(ReviewSetting reviewSetting, IPeak peak) {

		this.reviewSetting = reviewSetting;
		this.peak = peak;
		//
		updateInput();
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		createToolbarSearch(this);
		createTableTargets(this);
		//
		initialize();
	}

	private void initialize() {

		enableToolbar(toolbarSearch, buttonToolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH, false);
		enableEdit(targetListControl, buttonTableEdit, IMAGE_EDIT_ENTRY, false);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(7, false));
		//
		comboTarget = createComboTarget(composite);
		buttonTargetAdd = createButtonTargetAdd(composite);
		buttonTargetDelete = createButtonTargetDelete(composite);
		buttonToolbarSearch = createButtonToggleToolbar(composite, toolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH);
		buttonTableEdit = createButtonToggleEditTable(composite, targetListControl, IMAGE_EDIT_ENTRY);
		createScanIdentifierUI(composite);
		createButtonSettings(composite);
	}

	private void createScanIdentifierUI(Composite parent) {

		ScanIdentifierUI scanIdentifierUI = new ScanIdentifierUI(parent, SWT.NONE);
		scanIdentifierUI.setUpdateListener(new IUpdateListener() {

			@Override
			public void update(Display display) {

				updateInput();
			}
		});
		//
		identifierControl.set(scanIdentifierUI);
	}

	private void createButtonSettings(Composite parent) {

		ISettingsHandler settingsHandler = new ISettingsHandler() {

			@Override
			public void apply(Display display) {

				applySettings();
			}
		};
		//
		Button button = createSettingsButtonBasic(parent);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {

				/*
				 * Dynamically show different settings, based on the selected scan type.
				 */
				List<Class<? extends IPreferencePage>> preferencePages = getPreferencePages();
				showPreferencesDialog(event, preferencePages, settingsHandler);
			}
		});
	}

	private List<Class<? extends IPreferencePage>> getPreferencePages() {

		/*
		 * Default pages
		 */
		List<Class<? extends IPreferencePage>> preferencePages = new ArrayList<>();
		preferencePages.add(PreferencePageTargets.class);
		preferencePages.add(PreferencePageTargetsList.class);
		/*
		 * Additional pages.
		 */
		DataType scanDataType = getScanDataType();
		Object[] scanIdentifierServices = Activator.getDefault().getScanIdentifierServices();
		if(scanIdentifierServices != null) {
			for(Object object : scanIdentifierServices) {
				if(object instanceof IScanIdentifierService) {
					IScanIdentifierService scanIdentifierService = (IScanIdentifierService)object;
					DataType dataType = scanIdentifierService.getDataType();
					if(scanDataType.equals(dataType)) {
						Class<? extends IWorkbenchPreferencePage> preferencePage = scanIdentifierService.getPreferencePage();
						if(preferencePage != null) {
							preferencePages.add(preferencePage);
						}
					}
				}
			}
		}
		//
		return preferencePages;
	}

	private void applySettings() {

		updateInput();
	}

	private void createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				targetListControl.get().setSearchText(searchText, caseSensitive);
			}
		});
		//
		toolbarSearch.set(searchSupportUI);
	}

	private void createTableTargets(Composite parent) {

		TargetsListUI targetListUI = new TargetsListUI(parent, SWT.BORDER);
		targetListUI.setEditingSupport();
		Table table = targetListUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		setCellColorProvider(targetListUI);
		//
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateButtons();
				fireUpdate(false);
			}
		});
		/*
		 * Add the delete targets support.
		 */
		Shell shell = targetListUI.getTable().getShell();
		ITableSettings tableSettings = targetListUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addVerifyTargetsMenuEntry(tableSettings);
		addUnverifyTargetsMenuEntry(tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		targetListUI.applySettings(tableSettings);
		//
		targetListControl.set(targetListUI);
	}

	private void addDeleteMenuEntry(Shell shell, ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Delete Targets";
			}

			@Override
			public String getCategory() {

				return MENU_CATEGORY_TARGETS;
			}

			@Override
			public void execute(ExtendedTableViewer extendedTableViewer) {

				deleteTargets(shell);
			}
		});
	}

	private void addVerifyTargetsMenuEntry(ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Verify Targets Check";
			}

			@Override
			public String getCategory() {

				return MENU_CATEGORY_TARGETS;
			}

			@Override
			public void execute(ExtendedTableViewer extendedTableViewer) {

				verifyTargets(true);
			}
		});
	}

	private void addUnverifyTargetsMenuEntry(ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Verify Targets Uncheck";
			}

			@Override
			public String getCategory() {

				return MENU_CATEGORY_TARGETS;
			}

			@Override
			public void execute(ExtendedTableViewer extendedTableViewer) {

				verifyTargets(false);
			}
		});
	}

	private void addKeyEventProcessors(Shell shell, ITableSettings tableSettings) {

		tableSettings.addKeyEventProcessor(new IKeyEventProcessor() {

			@Override
			public void handleEvent(ExtendedTableViewer extendedTableViewer, KeyEvent e) {

				if(e.keyCode == SWT.DEL) {
					deleteTargets(shell);
				} else if(e.keyCode == IKeyboardSupport.KEY_CODE_LC_I && (e.stateMask & SWT.MOD1) == SWT.MOD1) {
					if((e.stateMask & SWT.MOD3) == SWT.MOD3) {
						/*
						 * CTRL + ALT + I
						 */
						verifyTargets(false);
					} else {
						/*
						 * CTRL + I
						 */
						verifyTargets(true);
					}
				} else {
					fireUpdate(false);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void verifyTargets(boolean verified) {

		Iterator iterator = targetListControl.get().getStructuredSelection().iterator();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof IIdentificationTarget) {
				IIdentificationTarget identificationTarget = (IIdentificationTarget)object;
				identificationTarget.setManuallyVerified(verified);
			}
		}
		//
		targetListControl.get().refresh(true);
		fireUpdate(true);
	}

	private void setCellColorProvider(TargetsListUI listUI) {

		List<TableViewerColumn> tableViewerColumns = listUI.getTableViewerColumns();
		TableViewerColumn tableViewerColumn = tableViewerColumns.get(2); // Caution, index may change.
		if(tableViewerColumn != null) {
			tableViewerColumn.setLabelProvider(new StyledCellLabelProvider() {

				@Override
				public void update(ViewerCell cell) {

					if(cell != null) {
						IIdentificationTarget target = (IIdentificationTarget)cell.getElement();
						boolean isCompoundAvailable = ReviewSupport.isCompoundAvailable(target, reviewSetting);
						Color background = isCompoundAvailable ? Colors.getColor(Colors.LIGHT_GREEN) : null;
						cell.setBackground(background);
						cell.setForeground(Colors.BLACK);
						cell.setText(target.getLibraryInformation().getName());
						super.update(cell);
					}
				}
			});
		}
	}

	private ComboTarget createComboTarget(Composite parent) {

		ComboTarget comboTarget = new ComboTarget(parent, SWT.NONE);
		comboTarget.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		comboTarget.setTargetUpdateListener(new ITargetUpdateListener() {

			@Override
			public void update(IIdentificationTarget identificationTarget) {

				if(identificationTarget != null) {
					setTarget(comboTarget.getDisplay(), identificationTarget);
				}
			}
		});
		//
		return comboTarget;
	}

	private Button createButtonTargetAdd(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Add the target.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IIdentificationTarget identificationTarget = comboTarget.createTarget();
				if(identificationTarget != null) {
					setTarget(e.display, identificationTarget);
				}
			}
		});
		//
		return button;
	}

	private void setTarget(Display display, IIdentificationTarget identificationTarget) {

		if(peak instanceof ITargetSupplier) {
			ITargetSupplier targetSupplier = (ITargetSupplier)peak;
			targetSupplier.getTargets().add(identificationTarget);
		}
		//
		updateInput();
	}

	private Button createButtonTargetDelete(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText("Delete the selected targets.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				deleteTargets(e.display.getActiveShell());
			}
		});
		//
		return button;
	}

	@SuppressWarnings("rawtypes")
	private void deleteTargets(Shell shell) {

		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setText("Delete Targets");
		messageBox.setMessage("Would you like to delete the selected targets?");
		if(messageBox.open() == SWT.YES) {
			/*
			 * Delete Target
			 */
			Iterator iterator = targetListControl.get().getStructuredSelection().iterator();
			while(iterator.hasNext()) {
				Object object = iterator.next();
				if(object instanceof IIdentificationTarget) {
					deleteTarget((ITarget)object);
				}
			}
			//
			updateInput();
		}
	}

	private void deleteTarget(ITarget target) {

		if(peak instanceof ITargetSupplier) {
			ITargetSupplier targetSupplier = peak;
			targetSupplier.getTargets().remove(target);
		}
	}

	private void updateInput() {

		updateComboTarget();
		updateIdentifierControl();
		updateTargetList();
		updateSearchText();
		updateButtons();
		//
		fireUpdate(false);
	}

	private void updateComboTarget() {

		comboTarget.updateContentProposals();
	}

	private void updateIdentifierControl() {

		identifierControl.get().updateIdentifier();
		if(peak != null) {
			identifierControl.get().setInput(peak.getPeakModel().getPeakMaximum());
		} else {
			IScan scan = null;
			identifierControl.get().setInput(scan);
		}
	}

	private void updateTargetList() {

		TargetsListUI targetListUI = targetListControl.get();
		if(peak instanceof ITargetSupplier) {
			float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
			ITargetSupplier targetSupplier = (ITargetSupplier)peak;
			List<IIdentificationTarget> identificationTargets = new ArrayList<>(targetSupplier.getTargets());
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			Collections.sort(identificationTargets, identificationTargetComparator);
			targetListUI.setInput(identificationTargets);
			/*
			 * Select the first entry if available.
			 */
			if(identificationTargets != null && !identificationTargets.isEmpty()) {
				targetListUI.getTable().select(0);
			}
		} else {
			targetListUI.setInput(null);
		}
	}

	private void updateSearchText() {

		if(PreferenceSupplier.isReviewUpdateSearchTarget()) {
			if(reviewSetting != null) {
				toolbarSearch.get().setSearchText(reviewSetting.getName());
			} else {
				toolbarSearch.get().setSearchText("");
			}
		}
	}

	private void updateButtons() {

		buttonTargetAdd.setEnabled(true);
		buttonTargetDelete.setEnabled(targetListControl.get().getTable().getSelectionCount() > 0);
	}

	private IIdentificationTarget getIdentificationTarget() {

		Object object = targetListControl.get().getStructuredSelection().getFirstElement();
		if(object instanceof IIdentificationTarget) {
			return (IIdentificationTarget)object;
		}
		//
		return null;
	}

	private DataType getScanDataType() {

		if(peak instanceof IPeakCSD) {
			return DataType.CSD;
		} else if(peak instanceof IPeakMSD) {
			return DataType.MSD;
		} else if(peak instanceof IPeakWSD) {
			return DataType.WSD;
		}
		//
		return DataType.NONE;
	}

	private void fireUpdate(boolean updateChart) {

		if(controller != null) {
			IIdentificationTarget identificationTarget = getIdentificationTarget();
			if(identificationTarget != null) {
				if(updateChart) {
					controller.updateDetectorChart();
				}
				controller.update(peak, identificationTarget);
			}
		}
	}
}