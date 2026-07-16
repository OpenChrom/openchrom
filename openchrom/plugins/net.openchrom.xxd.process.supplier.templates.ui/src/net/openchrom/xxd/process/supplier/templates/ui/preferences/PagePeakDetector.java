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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PagePeakDetector extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PagePeakDetector() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Detector UI [Template]");
		setDescription("");
	}

	@Override
	public void createFieldEditors() {

		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_DETECTOR_DELTA_LEFT_MILLISECONDS, "Delta Left [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_DETECTOR_DELTA_RIGHT_MILLISECONDS, "Delta Right [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_DETECTOR_DYNAMIC_OFFSET_MILLISECONDS, "Dynamic Offset [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_REPLACE_NEAREST_PEAK, "Replace Peak", getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_DETECTOR_REPLACE_PEAK_DELTA_MILLISECONDS, "Replace Max Delta [ms]", PreferenceSupplier.MIN_DELTA_REPLACE_PEAK_MILLISECONDS, PreferenceSupplier.MAX_DELTA_REPLACE_PEAK_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_HIDE_EXISTING_PEAKS, "Hide Existing Peaks", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_DETECTOR_VISIBILITY, "Visibility", PreferenceSupplier.VISIBILITY_OPTIONS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_FOCUS_XIC, "Focus XIC", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_SHOW_BASELINE, "Show Baseline", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_SHOW_ONLY_RELEVANT_PEAKS, "Show Only Relevant Peaks", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_DETECTOR_SHOW_TARGET_NAME_DIALOG, "Show Target Name Dialog", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}