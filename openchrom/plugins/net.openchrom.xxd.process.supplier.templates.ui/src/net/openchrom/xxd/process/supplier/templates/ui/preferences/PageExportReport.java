/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PageExportReport extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PageExportReport() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Report (Export)");
		setDescription("");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_LEFT_MINUTES_REPORT, "Delta Minutes Left", PreferenceSupplier.MIN_DELTA_MINUTES, PreferenceSupplier.MAX_DELTA_MINUTES, getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_RIGHT_MINUTES_REPORT, "Delta Minutes Right", PreferenceSupplier.MIN_DELTA_MINUTES, PreferenceSupplier.MAX_DELTA_MINUTES, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
