/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.FloatFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.LabelFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
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

	@Override
	public void createFieldEditors() {

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_OFFSET_MIN_Y, "Offset Min Y [%]", PreferenceSupplier.MIN_OFFSET_Y, PreferenceSupplier.MAX_OFFSET_Y, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_OFFSET_MAX_Y, "Offset Max Y [%]", PreferenceSupplier.MIN_OFFSET_Y, PreferenceSupplier.MAX_OFFSET_Y, getFieldEditorParent()));

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_PEAK_EXPORT_NUMBER_TRACES, "Peak Export Number Traces", PreferenceSupplier.MIN_TRACES, PreferenceSupplier.MAX_TRACES, getFieldEditorParent()));

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("Identifier", getFieldEditorParent()));
		addField(new FloatFieldEditor(PreferenceSupplier.P_LIMIT_MATCH_FACTOR_IDENTIFIER, "Limit Match Factor", PreferenceSupplier.MIN_FACTOR, PreferenceSupplier.MAX_FACTOR, getFieldEditorParent()));
		addField(new FloatFieldEditor(PreferenceSupplier.P_MATCH_QUALITY_IDENTIFIER, "Match Quality", PreferenceSupplier.MIN_FACTOR, PreferenceSupplier.MAX_FACTOR, getFieldEditorParent()));

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("Detector", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_AUTO_ADJUST_SCAN_RANGE, "Auto Adjust Scan Range (Experimental)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_AUTO_ADJUST_DETECTOR_RANGE, "Auto Adjust Detector Range (Experimental)", getFieldEditorParent()));

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SORT_IMPORT_TEMPLATE, "Sort Import Template", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SORT_EXPORT_TEMPLATE, "Sort Export Template", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_SETTINGS_SORT, "Sort Detector Settings", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SETTINGS_SORT, "Sort Review Settings", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REPORT_SETTINGS_SORT, "Sort Report Settings", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}