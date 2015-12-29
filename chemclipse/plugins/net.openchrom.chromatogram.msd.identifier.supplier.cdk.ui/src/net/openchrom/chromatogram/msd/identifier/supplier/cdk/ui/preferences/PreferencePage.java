/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("CDK settings.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new RadioGroupFieldEditor(PreferenceSupplier.P_ISOTOPE_SET, "Select an isotope set.", 1, PreferenceSupplier.getIsotopePreferenceOptions(), getFieldEditorParent()));
		IntegerFieldEditor iterationDepthFieldEditor = new IntegerFieldEditor(PreferenceSupplier.P_ISOTOPE_ITERATION_DEPTH, "Set the isotope iteration depth.", getFieldEditorParent());
		iterationDepthFieldEditor.setValidRange(0, 50);
		addField(iterationDepthFieldEditor);
		addField(new StringFieldEditor(PreferenceSupplier.P_USER_DEFINED_ISOTOPES, "User defined isotopes", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA, "Delete identifications without formula", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
