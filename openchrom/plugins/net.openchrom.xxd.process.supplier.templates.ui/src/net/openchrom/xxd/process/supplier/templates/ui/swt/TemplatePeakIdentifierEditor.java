/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.swt;

import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.ADD;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.ADD_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.DIALOG_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EDIT;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EDIT_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EXPORT;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.EXPORT_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.IMPORT;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.IMPORT_TITLE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_ADD;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EDIT;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EXPORT_FAILED;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_EXPORT_SUCCESSFUL;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_REMOVE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.MESSAGE_REMOVE_ALL;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE_ALL;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE_ALL_TOOLTIP;
import static net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.AbstractFieldEditor.REMOVE_TOOLTIP;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.processing.supplier.ProcessorPreferences;
import org.eclipse.chemclipse.support.ui.events.IKeyEventProcessor;
import org.eclipse.chemclipse.support.ui.menu.ITableMenuEntry;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.support.ui.swt.ITableSettings;
import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.methods.SettingsUIProvider;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;
import net.openchrom.xxd.process.supplier.templates.model.IdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakIdentifierSettings;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.PeakIdentifierInputValidator;
import net.openchrom.xxd.process.supplier.templates.util.PeakIdentifierListUtil;

public class TemplatePeakIdentifierEditor implements SettingsUIProvider.SettingsUIControl {

	private static final int NUMBER_COLUMNS = 2;
	//
	private Composite composite;
	private IdentifierSettings settings = new IdentifierSettings();
	private PeakIdentifierListUI listUI;
	//
	private static final String FILTER_EXTENSION = "*.txt";
	private static final String FILTER_NAME = "Peak Identifier Template (*.txt)";
	private static final String FILE_NAME = "PeakIdentifierTemplate.txt";
	//
	private static final String CATEGORY = "Peak Identifier";
	private static final String DELETE = "Delete";
	private List<Listener> listeners = new ArrayList<>();
	private List<Button> buttons = new ArrayList<>();
	private SearchSupportUI searchSupportUI;
	private ProcessorPreferences<PeakIdentifierSettings> preferences;

	public TemplatePeakIdentifierEditor(Composite parent, ProcessorPreferences<PeakIdentifierSettings> preferences, PeakIdentifierSettings settings) {
		this.preferences = preferences;
		if(settings != null) {
			this.settings.load(settings.getIdentifierSettings());
		}
		composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(NUMBER_COLUMNS, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		createLabelSection(composite);
		createSearchSection(composite);
		createTableSection(composite);
		createButtonGroup(composite);
	}

	private void createSearchSection(Composite parent) {

		searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = NUMBER_COLUMNS;
		searchSupportUI.setLayoutData(gridData);
		searchSupportUI.setSearchListener(new ISearchListener() {

			@Override
			public void performSearch(String searchText, boolean caseSensitive) {

				listUI.setSearchText(searchText, caseSensitive);
			}
		});
	}

	private void createLabelSection(Composite parent) {

		Label label = new Label(parent, SWT.LEFT);
		label.setText("");
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.horizontalSpan = NUMBER_COLUMNS;
		label.setLayoutData(gridData);
	}

	private void createTableSection(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		composite.setLayoutData(gridData);
		//
		listUI = new PeakIdentifierListUI(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		//
		Shell shell = listUI.getTable().getShell();
		ITableSettings tableSettings = listUI.getTableSettings();
		addDeleteMenuEntry(shell, tableSettings);
		addKeyEventProcessors(shell, tableSettings);
		listUI.applySettings(tableSettings);
		//
		setTableViewerInput();
	}

	private void createButtonGroup(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(gridData);
		//
		setButtonLayoutData(createButtonAdd(composite));
		setButtonLayoutData(createButtonEdit(composite));
		setButtonLayoutData(createButtonRemove(composite));
		setButtonLayoutData(createButtonRemoveAll(composite));
		setButtonLayoutData(createButtonImport(composite));
		setButtonLayoutData(createButtonExport(composite));
	}

	private void setButtonLayoutData(Button button) {

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		button.setLayoutData(data);
		buttons.add(button);
	}

	private Button createButtonAdd(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(ADD);
		button.setToolTipText(ADD_TOOLTIP);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				InputDialog dialog = new InputDialog(button.getShell(), DIALOG_TITLE, MESSAGE_ADD, PeakIdentifierListUtil.EXAMPLE_SINGLE, new PeakIdentifierInputValidator(settings.keySet()));
				if(IDialogConstants.OK_ID == dialog.open()) {
					String item = dialog.getValue();
					IdentifierSetting setting = settings.extractSettingInstance(item);
					if(setting != null) {
						settings.add(setting);
						setTableViewerInput();
					}
				}
			}
		});
		//
		return button;
	}

	private Button createButtonEdit(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(EDIT);
		button.setToolTipText(EDIT_TOOLTIP);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection structuredSelection = (IStructuredSelection)listUI.getSelection();
				Object object = structuredSelection.getFirstElement();
				if(object instanceof IdentifierSetting) {
					Set<String> keySetEdit = new HashSet<>();
					keySetEdit.addAll(settings.keySet());
					IdentifierSetting setting = (IdentifierSetting)object;
					keySetEdit.remove(setting.getName());
					InputDialog dialog = new InputDialog(button.getShell(), DIALOG_TITLE, MESSAGE_EDIT, settings.extractSetting(setting), new PeakIdentifierInputValidator(keySetEdit));
					if(IDialogConstants.OK_ID == dialog.open()) {
						String item = dialog.getValue();
						IdentifierSetting settingNew = settings.extractSettingInstance(item);
						setting.copyFrom(settingNew);
						setTableViewerInput();
					}
				}
			}
		});
		//
		return button;
	}

	private Button createButtonRemove(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(REMOVE);
		button.setToolTipText(REMOVE_TOOLTIP);
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
		button.setText(REMOVE_ALL);
		button.setToolTipText(REMOVE_ALL_TOOLTIP);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				if(MessageDialog.openQuestion(button.getShell(), DIALOG_TITLE, MESSAGE_REMOVE_ALL)) {
					settings.clear();
					setTableViewerInput();
				}
			}
		});
		//
		return button;
	}

	private Button createButtonImport(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(IMPORT);
		button.setToolTipText(IMPORT_TITLE);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(e.widget.getDisplay().getActiveShell(), SWT.READ_ONLY);
				fileDialog.setText(IMPORT_TITLE);
				fileDialog.setFilterExtensions(new String[]{FILTER_EXTENSION});
				fileDialog.setFilterNames(new String[]{FILTER_NAME});
				fileDialog.setFilterPath(PreferenceSupplier.getListPathImport());
				String path = fileDialog.open();
				if(path != null) {
					PreferenceSupplier.setListPathImport(fileDialog.getFilterPath());
					File file = new File(path);
					settings.importItems(file);
					setTableViewerInput();
				}
			}
		});
		//
		return button;
	}

	private Button createButtonExport(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(EXPORT);
		button.setToolTipText(EXPORT_TITLE);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(e.widget.getDisplay().getActiveShell(), SWT.SAVE);
				fileDialog.setOverwrite(true);
				fileDialog.setText(EXPORT_TITLE);
				fileDialog.setFilterExtensions(new String[]{FILTER_EXTENSION});
				fileDialog.setFilterNames(new String[]{FILTER_NAME});
				fileDialog.setFileName(FILE_NAME);
				fileDialog.setFilterPath(PreferenceSupplier.getListPathExport());
				String path = fileDialog.open();
				if(path != null) {
					PreferenceSupplier.setListPathExport(fileDialog.getFilterPath());
					File file = new File(path);
					if(settings.exportItems(file)) {
						MessageDialog.openInformation(button.getShell(), EXPORT_TITLE, MESSAGE_EXPORT_SUCCESSFUL);
					} else {
						MessageDialog.openWarning(button.getShell(), EXPORT_TITLE, MESSAGE_EXPORT_FAILED);
					}
				}
			}
		});
		//
		return button;
	}

	private void setTableViewerInput() {

		listUI.setInput(settings.values());
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

				return CATEGORY;
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
			IStructuredSelection structuredSelection = (IStructuredSelection)listUI.getSelection();
			for(Object object : structuredSelection.toArray()) {
				if(object instanceof IdentifierSetting) {
					settings.remove(((IdentifierSetting)object).getName());
				}
			}
			setTableViewerInput();
		}
	}

	@Override
	public void setEnabled(boolean enabled) {

		listUI.getControl().setEnabled(enabled);
		for(Button button : buttons) {
			button.setEnabled(enabled);
		}
		searchSupportUI.setEnabled(enabled);
	}

	@Override
	public IStatus validate() {

		return ValidationStatus.ok();
	}

	@Override
	public String getSettings() throws IOException {

		if(preferences != null) {
			PeakIdentifierSettings s = new PeakIdentifierSettings();
			s.setIdentifierSettings(settings.save());
			return preferences.getSerialization().toString(s);
		}
		return "";
	}

	@Override
	public void addChangeListener(Listener listener) {

		listeners.add(listener);
	}

	@Override
	public Control getControl() {

		return composite;
	}

	public void load(String entries) {

		settings.load(entries);
		setTableViewerInput();
	}

	public String getValues() {

		return settings.save();
	}
}
