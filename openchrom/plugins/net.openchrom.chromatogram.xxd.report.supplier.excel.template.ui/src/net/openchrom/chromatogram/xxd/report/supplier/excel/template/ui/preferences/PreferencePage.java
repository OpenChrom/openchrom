/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - modular placeholder support
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.ui.preferences;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.rcp.ui.icons.core.ApplicationImageFactory;
import org.eclipse.chemclipse.rcp.ui.icons.core.IApplicationImage;
import org.eclipse.chemclipse.support.ui.editors.SystemEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.io.ExcelTemplateReportWriter;
import net.openchrom.chromatogram.xxd.report.supplier.excel.template.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.xxd.report.supplier.excel.template.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static final Logger logger = Logger.getLogger(PreferencePage.class);
	private static final String TITLE = "Excel Template";
	//
	private FileFieldEditor fileFieldEditor;

	public PreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Excel Template Chromatogram Reports");
		setDescription("Allows user customizable report based on *.xlst Excel Templates.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		addField(fileFieldEditor = new FileFieldEditor(PreferenceSupplier.P_TEMPLATE, "Excel Template", getFieldEditorParent()));
		createPlaceholderExportButton(getFieldEditorParent());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {

	}

	private void createPlaceholderExportButton(Composite parent) {

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Create Excel Template");
		button.setToolTipText("Exports a template with all available placeholders.");
		button.setImage(ApplicationImageFactory.getInstance().getImage(IApplicationImage.IMAGE_EXCEL, IApplicationImage.SIZE_16x16));
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog fileDialog = new FileDialog(e.widget.getDisplay().getActiveShell(), SWT.SAVE);
				fileDialog.setOverwrite(true);
				fileDialog.setText(TITLE);
				fileDialog.setFilterExtensions(new String[]{ExcelTemplateReportWriter.FILTER_EXTENSION});
				fileDialog.setFilterNames(new String[]{ExcelTemplateReportWriter.FILTER_NAME});
				fileDialog.setFileName(ExcelTemplateReportWriter.FILE_NAME);
				fileDialog.setFilterPath(PreferenceSupplier.getListPathExport());
				String path = fileDialog.open();
				if(path != null) {
					try {
						PreferenceSupplier.setListPathExport(fileDialog.getFilterPath());
						File file = new File(path);
						ExcelTemplateReportWriter excelTemplateReportWriter = new ExcelTemplateReportWriter();
						excelTemplateReportWriter.generateTemplate(file);
						if(MessageDialog.openQuestion(e.display.getActiveShell(), TITLE, "Would you like to use this as the default template?")) {
							PreferenceSupplier.setTemplate(file);
							fileFieldEditor.load();
						}
						SystemEditor.open(file);
					} catch(IOException e1) {
						logger.warn(e1);
						MessageDialog.openWarning(e.display.getActiveShell(), TITLE, "Something went wrong to export the template.");
					}
				}
			}
		});
	}
}