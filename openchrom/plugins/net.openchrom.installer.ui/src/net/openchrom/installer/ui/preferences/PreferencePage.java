/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - title
 *******************************************************************************/
package net.openchrom.installer.ui.preferences;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.installer.preferences.PreferenceSupplier;
import net.openchrom.installer.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private final String[][] options = { //
			{MessageDialogWithToggle.ALWAYS, MessageDialogWithToggle.ALWAYS}, //
			{MessageDialogWithToggle.NEVER, MessageDialogWithToggle.NEVER} //
	};

	public PreferencePage() {

		super(GRID);
		setTitle("Plugin Installer");
		setDescription("Plugin installer user interface options");
	}

	@Override
	public void init(IWorkbench workbench) {

		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void createFieldEditors() {

		addField(new ComboFieldEditor(PreferenceSupplier.P_PROPRIETARY_CONVERTERS, "Ask for proprietary converter installation.", options, getFieldEditorParent()));
	}
}