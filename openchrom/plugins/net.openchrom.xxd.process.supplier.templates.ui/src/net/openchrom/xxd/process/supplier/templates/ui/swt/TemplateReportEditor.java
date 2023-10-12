/*******************************************************************************
 * Copyright (c) 2020, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 * Philip Wenig - refactoring, so that it can be used with an annotation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.ADD_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.DIALOG_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EDIT_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EXPORT_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.IMPORT_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_ADD;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EDIT;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EXPORT_FAILED;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EXPORT_SUCCESSFUL;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_REMOVE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_REMOVE_ALL;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE_ALL_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE_TOOLTIP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.chemclipse.processing.core.ICategories;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.files.ExtendedFileDialog;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.support.updates.IUpdateListener;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.IChangeListener;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.IExtendedPartUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.ReportInputValidator;
import net.openchrom.xxd.process.supplier.templates.util.ReportListUtil;

public class TemplateReportEditor extends Composite implements IChangeListener, IExtendedPartUI {

	private static final String DELETE = "Delete";
	//
	private Button buttonToolbarSearch;
	private AtomicReference<SearchSupportUI> toolbarSearch = new AtomicReference<>();
	private AtomicReference<ReportListUI> tableViewer = new AtomicReference<>();
	//
	private List<Button> buttons = new ArrayList<>();
	private List<Listener> listeners = new ArrayList<>();
	//
	private ReportSettings reportSettings = new ReportSettings();

	public TemplateReportEditor(Composite parent, int style) {

		super(parent, style);
		createControl();
	}

	public ReportSettings getReportSettings() {

		return reportSettings;
	}

	public void load(String entries) {

		reportSettings.load(entries);
		setViewerInput();
	}

	public String save() {

		return reportSettings.save();
	}

	@Override
	public void addChangeListener(Listener listener) {

		/*
		 * Listen to changes in the table.
		 */
		Control control = tableViewer.get().getControl();
		control.addListener(SWT.Selection, listener);
		control.addListener(SWT.KeyUp, listener);
		control.addListener(SWT.MouseUp, listener);
		control.addListener(SWT.MouseDoubleClick, listener);
		/*
		 * Listen to selection of the buttons.
		 */
		for(Button button : buttons) {
			button.addListener(SWT.Selection, listener);
			button.addListener(SWT.KeyUp, listener);
			button.addListener(SWT.MouseUp, listener);
			button.addListener(SWT.MouseDoubleClick, listener);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {

		toolbarSearch.get().setEnabled(enabled);
		tableViewer.get().getControl().setEnabled(enabled);
		for(Button button : buttons) {
			button.setEnabled(enabled);
		}
	}

	private void createControl() {

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		//
		createToolbarMain(this);
		createToolbarSearch(this);
		createTableSection(this);
		//
		initialize();
	}

	private void initialize() {

		enableToolbar(toolbarSearch, buttonToolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH, false);
		setViewerInput();
	}

	private void createToolbarMain(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalAlignment = SWT.END;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(7, false));
		//
		buttonToolbarSearch = createButtonToggleToolbar(composite, toolbarSearch, IMAGE_SEARCH, TOOLTIP_SEARCH);
		add(createButtonAdd(composite));
		add(createButtonEdit(composite));
		add(createButtonRemove(composite));
		add(createButtonRemoveAll(composite));
		add(createButtonImport(composite));
		add(createButtonExport(composite));
	}

	private void add(Button button) {

		buttons.add(button);
	}

	private void createToolbarSearch(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		searchSupportUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				tableViewer.get().setSearchText(searchText, caseSensitive);
			}
		});
		//
		toolbarSearch.set(searchSupportUI);
	}

	private void createTableSection(Composite parent) {

		ReportListUI reportListUI = new ReportListUI(parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		reportListUI.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		reportListUI.setUpdateListener(new IUpdateListener() {

			@Override
			public void update() {

				setViewerInput();
			}
		});
		//
		Shell shell = reportListUI.getTable().getShell();
		ITableSettings tableSettings = reportListUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		reportListUI.applySettings(tableSettings);
		//
		tableViewer.set(reportListUI);
	}

	private Button createButtonAdd(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(ADD_TOOLTIP);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_ADD, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				InputDialog dialog = new InputDialog(e.display.getActiveShell(), DIALOG_TITLE, MESSAGE_ADD, ReportListUtil.EXAMPLE_SINGLE, new ReportInputValidator(reportSettings));
				if(IDialogConstants.OK_ID == dialog.open()) {
					String item = dialog.getValue();
					ReportSetting setting = reportSettings.extractSettingInstance(item);
					if(setting != null) {
						reportSettings.add(setting);
						setViewerInput();
					}
				}
			}
		});
		//
		return button;
	}

	private Button createButtonEdit(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(EDIT_TOOLTIP);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EDIT, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection structuredSelection = (IStructuredSelection)tableViewer.get().getSelection();
				Object object = structuredSelection.getFirstElement();
				if(object instanceof ReportSetting) {
					ReportSettings reportSettingsMod = new ReportSettings();
					reportSettingsMod.addAll(reportSettings);
					ReportSetting setting = (ReportSetting)object;
					reportSettingsMod.remove(setting);
					InputDialog dialog = new InputDialog(e.display.getActiveShell(), DIALOG_TITLE, MESSAGE_EDIT, reportSettings.extractSetting(setting), new ReportInputValidator(reportSettingsMod));
					if(IDialogConstants.OK_ID == dialog.open()) {
						String item = dialog.getValue();
						ReportSetting settingNew = reportSettings.extractSettingInstance(item);
						setting.copyFrom(settingNew);
						setViewerInput();
					}
				}
			}
		});
		//
		return button;
	}

	private Button createButtonRemove(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(REMOVE_TOOLTIP);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				deleteItems(e.display.getActiveShell());
			}
		});
		//
		return button;
	}

	private Button createButtonRemoveAll(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(REMOVE_ALL_TOOLTIP);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_DELETE_ALL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(MessageDialog.openQuestion(e.display.getActiveShell(), DIALOG_TITLE, MESSAGE_REMOVE_ALL)) {
					reportSettings.clear();
					setViewerInput();
				}
			}
		});
		//
		return button;
	}

	private Button createButtonImport(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(IMPORT_TITLE);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_IMPORT, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = ExtendedFileDialog.create(e.widget.getDisplay().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText(IMPORT_TITLE);
				fileDialog.setFilterExtensions(new String[]{ReportSettings.FILTER_EXTENSION});
				fileDialog.setFilterNames(new String[]{ReportSettings.FILTER_NAME});
				fileDialog.setFilterPath(PreferenceSupplier.getListPathImport());
				String path = fileDialog.open();
				if(path != null) {
					PreferenceSupplier.setListPathImport(fileDialog.getFilterPath());
					File file = new File(path);
					reportSettings.importItems(file);
					setViewerInput();
				}
			}
		});
		//
		return button;
	}

	private Button createButtonExport(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("");
		button.setToolTipText(EXPORT_TITLE);
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXPORT, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = ExtendedFileDialog.create(e.widget.getDisplay().getActiveShell(), SWT.SAVE);
				fileDialog.setOverwrite(true);
				fileDialog.setText(EXPORT_TITLE);
				fileDialog.setFilterExtensions(new String[]{ReportSettings.FILTER_EXTENSION});
				fileDialog.setFilterNames(new String[]{ReportSettings.FILTER_NAME});
				fileDialog.setFileName(ReportSettings.FILE_NAME);
				fileDialog.setFilterPath(PreferenceSupplier.getListPathExport());
				String path = fileDialog.open();
				if(path != null) {
					PreferenceSupplier.setListPathExport(fileDialog.getFilterPath());
					File file = new File(path);
					if(reportSettings.exportItems(file)) {
						MessageDialog.openInformation(e.display.getActiveShell(), EXPORT_TITLE, MESSAGE_EXPORT_SUCCESSFUL);
					} else {
						MessageDialog.openWarning(e.display.getActiveShell(), EXPORT_TITLE, MESSAGE_EXPORT_FAILED);
					}
				}
			}
		});
		//
		return button;
	}

	private void setViewerInput() {

		tableViewer.get().setInput(reportSettings);
		for(Listener listener : listeners) {
			listener.handleEvent(new Event());
		}
	}

	private void addDeleteMenuEntry(Shell shell, ITableSettings tableSettings) {

		tableSettings.addMenuEntry(new ITableMenuEntry() {

			@Override
			public String getName() {

				return DELETE;
			}

			@Override
			public String getCategory() {

				return ICategories.CHROMATOGRAM_REPORTS;
			}

			@Override
			public void execute(ExtendedTableViewer extendedTableViewer) {

				deleteItems(shell);
			}
		});
	}

	private void addKeyEventProcessors(Shell shell, ITableSettings tableSettings) {

		tableSettings.addKeyEventProcessor(new IKeyEventProcessor() {

			@Override
			public void handleEvent(ExtendedTableViewer extendedTableViewer, KeyEvent e) {

				if(e.keyCode == SWT.DEL) {
					deleteItems(shell);
				}
			}
		});
	}

	private void deleteItems(Shell shell) {

		if(MessageDialog.openQuestion(shell, DIALOG_TITLE, MESSAGE_REMOVE)) {
			IStructuredSelection structuredSelection = (IStructuredSelection)tableViewer.get().getSelection();
			for(Object object : structuredSelection.toArray()) {
				if(object instanceof ReportSetting) {
					reportSettings.remove((ReportSetting)object);
				}
			}
			setViewerInput();
		}
	}
}