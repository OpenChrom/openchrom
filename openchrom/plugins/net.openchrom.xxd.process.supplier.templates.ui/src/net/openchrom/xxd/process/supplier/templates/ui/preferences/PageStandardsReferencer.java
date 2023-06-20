/*******************************************************************************
 * Copyright (c) 2018, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
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

	public void createFieldEditors() {

		addField(new StandardsReferencerFieldEditor(PreferenceSupplier.P_STANDARDS_REFERENCER_LIST, "Standards Referencer (ISTD)", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}
}