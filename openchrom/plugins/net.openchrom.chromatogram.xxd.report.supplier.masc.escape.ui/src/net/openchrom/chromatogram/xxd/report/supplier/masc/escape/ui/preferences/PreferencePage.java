/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.masc.escape.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("MaSC ESCAPE Chromatogram Reports");
		setDescription("Allows user customizable report based on *.xltx spreadsheet templates.");
	}

	@Override
	public void createFieldEditors() {

		addField(new FileFieldEditor(PreferenceSupplier.P_TEMPLATE, "ESCAPE Template", getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceSupplier.P_REPORT, "Export Folder", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}