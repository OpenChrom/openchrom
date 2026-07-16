/*******************************************************************************
 * Copyright (c) 2018, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;
import net.openchrom.xxd.process.supplier.templates.ui.fieldeditors.StandardsReferencerFieldEditor;

public class PageStandardsReferencer extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PageStandardsReferencer() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Referencer (ISTD).");
		setDescription("");
	}

	@Override
	public void createFieldEditors() {

		addField(new StandardsReferencerFieldEditor(PreferenceSupplier.P_STANDARDS_REFERENCER_LIST, "Standards Referencer (ISTD)", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}