/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.FloatFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpinnerFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Ratio Classifier");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	@Override
	public void createFieldEditors() {

		addField(new FloatFieldEditor(PreferenceSupplier.P_ALLOWED_DEVIATION, "Allowed Deviation (%)", PreferenceSupplier.MIN_DEVIATION, PreferenceSupplier.MAX_DEVIATION, getFieldEditorParent()));
		addField(new FloatFieldEditor(PreferenceSupplier.P_ALLOWED_DEVIATION_WARN, "Allowed Deviation Warn (%)", PreferenceSupplier.MIN_DEVIATION, PreferenceSupplier.MAX_DEVIATION, getFieldEditorParent()));
		//
		addField(new DirectoryFieldEditor(PreferenceSupplier.P_LIST_PATH_IMPORT, "List Path Import", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceSupplier.P_LIST_PATH_EXPORT, "List Path Export", getFieldEditorParent()));
		//
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_EXPORT_NUMBER_TRACES, "Export Number Traces", PreferenceSupplier.MIN_NUMBER_TRACES, PreferenceSupplier.MAX_NUMBER_TRACES, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {

	}
}