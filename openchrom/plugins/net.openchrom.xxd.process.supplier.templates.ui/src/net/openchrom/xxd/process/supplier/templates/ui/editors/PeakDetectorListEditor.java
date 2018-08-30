/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.editors;

import java.io.File;
import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorListUtil;

public class PeakDetectorListEditor extends ListEditor {

	private static final String DESCRIPTION = PeakDetectorSettings.DETECTOR_DESCRIPTION;
	private static final String MESSAGE = "You can create a new peak detector item here.";
	private static final String INITIAL_VALUE = PeakDetectorListUtil.EXAMPLE_SINGLE;
	//
	private static final String DELETE_ITEMS = "Delete Items";
	private static final String DELETE_ITEMS_TOOLTIP = "Deletes the items from the list.";
	private static final String DELETE_ITEMS_MESSAGE = "Would you like to delete the items from the list?";
	private static final String IMPORT_ITEMS = "Import Items";
	private static final String IMPORT_ITEMS_TOOLTIP = "Import a pre-defined list of items.";
	private static final String EXPORT_ITEMS = "Export Items";
	private static final String EXPORT_ITEMS_TOOLTIP = "Export the items of the list.";
	//
	private static final String FILE_NAME = "PeakDetectorItems.txt";
	private static final String FILTER_EXTENSION = "*.txt";
	private static final String FILTER_NAME = "Items (*.txt)";
	//
	private PeakDetectorListUtil util = new PeakDetectorListUtil();

	public PeakDetectorListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		initialize(parent);
	}

	@Override
	protected String createList(String[] items) {

		return util.createList(items);
	}

	@Override
	protected String getNewInputObject() {

		List list = getList();
		InputDialog dialog = new InputDialog(getShell(), DESCRIPTION, MESSAGE, INITIAL_VALUE, new PeakDetectorInputValidator(list));
		dialog.create();
		if(dialog.open() == Dialog.OK) {
			String item = dialog.getValue();
			return addItem(item, list);
		}
		return null;
	}

	private void initialize(Composite parent) {

		Composite composite = getButtonBoxControl(parent);
		//
		addClearButton(composite);
		addImportButton(composite);
		addExportButton(composite);
	}

	private void addClearButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(DELETE_ITEMS);
		button.setToolTipText(DELETE_ITEMS_TOOLTIP);
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				List list = getList();
				if(list != null) {
					MessageBox messageBox = new MessageBox(e.widget.getDisplay().getActiveShell(), SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setText(DELETE_ITEMS);
					messageBox.setMessage(DELETE_ITEMS_MESSAGE);
					if(messageBox.open() == SWT.YES) {
						list.removeAll();
					}
				}
			}
		});
	}

	private void addImportButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(IMPORT_ITEMS);
		button.setToolTipText(IMPORT_ITEMS_TOOLTIP);
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				List list = getList();
				if(list != null) {
					FileDialog fileDialog = new FileDialog(e.widget.getDisplay().getActiveShell(), SWT.READ_ONLY);
					fileDialog.setText(IMPORT_ITEMS);
					fileDialog.setFilterExtensions(new String[]{FILTER_EXTENSION});
					fileDialog.setFilterNames(new String[]{FILTER_NAME});
					fileDialog.setFilterPath(PreferenceSupplier.getPeakDetectorListPathImport());
					String path = fileDialog.open();
					if(path != null) {
						PreferenceSupplier.setPeakDetectorListPathImport(fileDialog.getFilterPath());
						File file = new File(path);
						java.util.List<String> items = util.importItems(file);
						for(String item : items) {
							list.add(item);
						}
					}
				}
			}
		});
	}

	private void addExportButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText(EXPORT_ITEMS);
		button.setToolTipText(EXPORT_ITEMS_TOOLTIP);
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				List list = getList();
				if(list != null) {
					FileDialog fileDialog = new FileDialog(e.widget.getDisplay().getActiveShell(), SWT.SAVE);
					fileDialog.setOverwrite(true);
					fileDialog.setText(EXPORT_ITEMS);
					fileDialog.setFilterExtensions(new String[]{FILTER_EXTENSION});
					fileDialog.setFilterNames(new String[]{FILTER_NAME});
					fileDialog.setFileName(FILE_NAME);
					fileDialog.setFilterPath(PreferenceSupplier.getPeakDetectorListPathExport());
					String path = fileDialog.open();
					if(path != null) {
						PreferenceSupplier.setPeakDetectorListPathExport(fileDialog.getFilterPath());
						File file = new File(path);
						String[] items = list.getItems();
						util.exportItems(file, items);
					}
				}
			}
		});
	}

	private String addItem(String item, List list) {

		String[] items = list.getItems();
		if(!itemExistsInList(item, items)) {
			return item;
		}
		return null;
	}

	private boolean itemExistsInList(String itemX, String[] list) {

		for(String item : list) {
			if(item.equals(itemX)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String[] parseString(String stringList) {

		String[] items = util.parseString(stringList);
		Arrays.sort(items);
		return items;
	}
}
