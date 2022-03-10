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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.ITargetSupplier;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.targets.ITarget;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImageProvider;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.swt.ui.support.Colors;
import org.eclipse.chemclipse.ux.extension.ui.support.PartSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.preferences.PreferencePageTargets;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.TargetsListUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swtchart.extensions.core.IKeyboardSupport;

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReviewSupport;

public class ExtendedTargetsUI extends Composite {

	private static final String MENU_CATEGORY_TARGETS = "Targets";
	//
	private ReviewController controller;
	private SearchSupportUI searchSupportUI;
	private TargetsListUI targetListUI;
	//
	private ReviewSetting reviewSetting;
	private IPeak peak;
	private Set<IIdentificationTarget> targets;

	public ExtendedTargetsUI(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public void setController(ReviewController controller) {

		this.controller = controller;
	}

	public void setInput(ReviewSetting reviewSetting, IPeak peak, Set<IIdentificationTarget> targets) {

		this.reviewSetting = reviewSetting;
		this.peak = peak;
		this.targets = targets;
		/*
		 * Set the input and sort the table.
		 */
		targetListUI.setInput(targets);
		targetListUI.sortTable();
		targetListUI.setEditEnabled(false);
		/*
		 * Select the first entry if available.
		 */
		if(targets != null && !targets.isEmpty()) {
			targetListUI.getTable().select(0);
		}
		//
		updateSearchText();
		updateSelection(false);
	}

	private void updateSearchText() {

		if(PreferenceSupplier.isReviewUpdateSearchTarget()) {
			if(reviewSetting != null) {
				searchSupportUI.setSearchText(reviewSetting.getName());
			} else {
				searchSupportUI.setSearchText("");
			}
		}
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, true);
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		searchSupportUI = createToolbarSearch(this);
		targetListUI = createTableTargets(this);
		//
		PartSupport.setCompositeVisibility(searchSupportUI, false);
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(2, false));
		//
		createToggleToolbarSearch(composite);
		createSettingsButton(composite);
	}

	private Button createToggleToolbarSearch(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText("Toggle search toolbar.");
		button.setText("");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				boolean visible = PartSupport.toggleCompositeVisibility(searchSupportUI);
				if(visible) {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImageProvider.SIZE_16x16));
				} else {
					button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_SEARCH, IApplicationImageProvider.SIZE_16x16));
				}
			}
		});
		//
		return button;
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
				preferenceManager.addToRoot(new PreferenceNode("1", new PreferencePageTargets()));
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

		// TODO
	}

	private IIdentificationTarget getIdentificationTarget() {

		Object object = targetListUI.getStructuredSelection().getFirstElement();
		if(object instanceof IIdentificationTarget) {
			return (IIdentificationTarget)object;
		}
		return null;
	}

	private SearchSupportUI createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				targetListUI.setSearchText(searchText, caseSensitive);
			}
		});
		//
		return searchSupportUI;
	}

	private TargetsListUI createTableTargets(Composite parent) {

		TargetsListUI listUI = new TargetsListUI(parent, SWT.BORDER);
		listUI.setEditingSupport();
		Table table = listUI.getTable();
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		setCellColorProvider(listUI);
		table.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				updateSelection(false);
			}
		});
		/*
		 * Add the delete targets support.
		 */
		Shell shell = listUI.getTable().getShell();
		ITableSettings tableSettings = listUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addVerifyTargetsMenuEntry(tableSettings);
		addUnverifyTargetsMenuEntry(tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		listUI.applySettings(tableSettings);
		//
		return listUI;
	}

	private void addDeleteMenuEntry(Shell shell, ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Delete Target(s)";
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

	@SuppressWarnings("rawtypes")
	private void deleteTargets(Shell shell) {

		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		messageBox.setText("Delete Target(s)");
		messageBox.setMessage("Would you like to delete the selected target(s)?");
		if(messageBox.open() == SWT.YES) {
			/*
			 * Delete Target
			 */
			Iterator iterator = targetListUI.getStructuredSelection().iterator();
			while(iterator.hasNext()) {
				Object object = iterator.next();
				if(object instanceof IIdentificationTarget) {
					deleteTarget((ITarget)object);
				}
			}
			//
			if(peak instanceof ITargetSupplier) {
				ITargetSupplier targetSupplier = peak;
				targets = targetSupplier.getTargets();
				targetListUI.setInput(targets);
			}
			updateSelection(true);
		}
	}

	private void deleteTarget(ITarget target) {

		if(peak instanceof ITargetSupplier) {
			ITargetSupplier targetSupplier = peak;
			targetSupplier.getTargets().remove(target);
		}
	}

	private void addVerifyTargetsMenuEntry(ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return "Verify Target(s) Check";
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

				return "Verify Target(s) Uncheck";
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
					updateSelection(false);
				}
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void verifyTargets(boolean verified) {

		Iterator iterator = targetListUI.getStructuredSelection().iterator();
		while(iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof IIdentificationTarget) {
				IIdentificationTarget identificationTarget = (IIdentificationTarget)object;
				identificationTarget.setManuallyVerified(verified);
			}
		}
		targetListUI.refresh(true);
		updateSelection(true);
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

	private void updateSelection(boolean updateChart) {

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
