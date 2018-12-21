/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.fieldeditors;

import java.io.File;

import org.eclipse.chemclipse.swt.ui.components.ISearchListener;
import org.eclipse.chemclipse.swt.ui.components.SearchSupportUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.model.AssignerReferences;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.internal.provider.StandardsReferencerInputValidator;
import net.openchrom.xxd.process.supplier.templates.ui.swt.StandardsReferencerListUI;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerListUtil;

public class StandardsReferencerFieldEditor extends FieldEditor {

	private static final int NUMBER_COLUMNS = 2;
	//
	private Composite composite;
	private AssignerReferences settings = new AssignerReferences();
	private StandardsReferencerListUI listUI;
	//
	private static final String ADD = "Add";
	private static final String ADD_TOOLTIP = "Add a new standard";
	private static final String EDIT = "Edit";
	private static final String EDIT_TOOLTIP = "Edit the selected standard";
	private static final String REMOVE = "Remove";
	private static final String REMOVE_TOOLTIP = "Remove Selected Standard(s)";
	private static final String REMOVE_ALL = "Remove All";
	private static final String REMOVE_ALL_TOOLTIP = "Remove All Standards";
	private static final String IMPORT = "Import";
	private static final String EXPORT = "Export";
	//
	private static final String IMPORT_TITLE = "Import Standards";
	private static final String EXPORT_TITLE = "Export Standards";
	private static final String DIALOG_TITLE = "Standard(s)";
	private static final String MESSAGE_ADD = "You can create a new standard here.";
	private static final String MESSAGE_EDIT = "Edit the selected standard.";
	private static final String MESSAGE_REMOVE = "Do you want to delete the selected standard(s)?";
	private static final String MESSAGE_REMOVE_ALL = "Do you want to delete all standard(s)?";
	private static final String MESSAGE_EXPORT_SUCCESSFUL = "Standards have been exported successfully.";
	private static final String MESSAGE_EXPORT_FAILED = "Failed to export the standards.";
	//
	private static final String FILTER_EXTENSION = "*.txt";
	private static final String FILTER_NAME = "Standards Referencer (*.txt)";
	private static final String FILE_NAME = "StandardsReferencer.txt";

	public StandardsReferencerFieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {

		getLabelControl(parent);
		//
		composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(NUMBER_COLUMNS, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gridData);
		//
		createLabelSection(composite);
		createSearchSection(composite);
		createTableSection(composite);
		createButtonGroup(composite);
	}

	private void createSearchSection(Composite parent) {

		SearchSupportUI searchSupportUI = new SearchSupportUI(parent, SWT.NONE);
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
		listUI = new StandardsReferencerListUI(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
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

	private Button createButtonAdd(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(ADD);
		button.setToolTipText(ADD_TOOLTIP);
		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				InputDialog dialog = new InputDialog(button.getShell(), DIALOG_TITLE, MESSAGE_ADD, StandardsReferencerListUtil.EXAMPLE_SINGLE, new StandardsReferencerInputValidator(settings.keySet()));
				if(IDialogConstants.OK_ID == dialog.open()) {
					String item = dialog.getValue();
					AssignerReference setting = settings.extractSettingInstance(item);
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

	private Button createButtonImport(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(IMPORT);
		button.setToolTipText(IMPORT_TITLE);
		button.addSelectionListener(new SelectionAdapter() {

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

	private Button createButtonEdit(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(EDIT);
		button.setToolTipText(EDIT_TOOLTIP);
		button.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection structuredSelection = (IStructuredSelection)listUI.getSelection();
				Object object = structuredSelection.getFirstElement();
				if(object instanceof AssignerReference) {
					AssignerReference setting = (AssignerReference)object;
					InputDialog dialog = new InputDialog(button.getShell(), DIALOG_TITLE, MESSAGE_EDIT, settings.extractSettingString(setting), new StandardsReferencerInputValidator(settings.keySet()));
					if(IDialogConstants.OK_ID == dialog.open()) {
						String item = dialog.getValue();
						AssignerReference settingNew = settings.extractSettingInstance(item);
						setting.copyFrom(settingNew);
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

			public void widgetSelected(SelectionEvent e) {

				if(MessageDialog.openQuestion(button.getShell(), DIALOG_TITLE, MESSAGE_REMOVE)) {
					IStructuredSelection structuredSelection = (IStructuredSelection)listUI.getSelection();
					for(Object object : structuredSelection.toArray()) {
						if(object instanceof AssignerReference) {
							settings.remove(((AssignerReference)object).getName());
						}
					}
					setTableViewerInput();
				}
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

	private void setTableViewerInput() {

		listUI.setInput(settings.values());
	}

	@Override
	protected void doLoad() {

		String entries = getPreferenceStore().getString(getPreferenceName());
		settings.load(entries);
		setTableViewerInput();
	}

	@Override
	protected void doLoadDefault() {

		String entries = getPreferenceStore().getDefaultString(getPreferenceName());
		settings.loadDefault(entries);
		setTableViewerInput();
	}

	@Override
	protected void doStore() {

		getPreferenceStore().setValue(getPreferenceName(), settings.save());
	}

	@Override
	public int getNumberOfControls() {

		return 1;
	}

	@Override
	protected void adjustForNumColumns(int numColumns) {

		if(numColumns >= 2) {
			GridData gridData = (GridData)composite.getLayoutData();
			gridData.horizontalSpan = numColumns - 1;
			gridData.grabExcessHorizontalSpace = true;
		}
	}
}
