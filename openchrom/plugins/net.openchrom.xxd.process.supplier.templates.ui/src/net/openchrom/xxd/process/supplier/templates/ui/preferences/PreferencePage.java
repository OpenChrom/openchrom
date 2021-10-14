/*******************************************************************************
 * Copyright (c) 2018, 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Template Processor");
		setDescription("");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new DirectoryFieldEditor(PreferenceSupplier.P_LIST_PATH_IMPORT, "List Path Import", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceSupplier.P_LIST_PATH_EXPORT, "List Path Export", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_OFFSET_MIN_Y, "Offset Min Y [%]", PreferenceSupplier.MIN_OFFSET_Y, PreferenceSupplier.MAX_OFFSET_Y, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_OFFSET_MAX_Y, "Offset Max Y [%]", PreferenceSupplier.MIN_OFFSET_Y, PreferenceSupplier.MAX_OFFSET_Y, getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SORT_IMPORT_TEMPLATE, "Sort Import Template", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SORT_EXPORT_TEMPLATE, "Sort Export Template", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_SETTINGS_SORT, "Sort Detector Settings", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SETTINGS_SORT, "Sort Review Settings", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REPORT_SETTINGS_SORT, "Sort Report Settings", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}