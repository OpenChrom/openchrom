/*******************************************************************************
 * Copyright (c) 2013, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;
import net.openchrom.xxd.identifier.supplier.cdk.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("CDK (Chemistry Development Kit)");
		setDescription("Use an offline library to generate molecular formulas.");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new RadioGroupFieldEditor(PreferenceSupplier.P_ISOTOPE_SET, "Select an isotope set.", 1, PreferenceSupplier.getIsotopePreferenceOptions(), getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_ISOTOPE_ITERATION_DEPTH, "Set the isotope iteration depth.", 0, 50, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_USER_DEFINED_ISOTOPES, "User defined isotopes", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SMILES_STRICT, "SMILES Strict", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_ALLOW_RADICALS, "Allow Radicals", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS, "Output Radicals As WildCard Atoms", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETAILED_FAILURE_ANALYSIS, "Detailed Failure Analysis", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID, "Interpret Acids Without The Word Acid", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_WARN_RATHER_THAN_FAIL, "Warn Rather Than Fail On Uninterpretable Stereochemistry", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DELETE_SCAN_TARGETS, "Delete Scan Targets - No SMILES", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DELETE_PEAK_TARGETS, "Delete Peak Targets - No SMILES", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SHOW_ATOMS_H, "Show H Atoms", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}