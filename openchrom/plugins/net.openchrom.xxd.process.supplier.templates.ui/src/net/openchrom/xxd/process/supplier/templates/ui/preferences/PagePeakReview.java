/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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

public class PagePeakReview extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PagePeakReview() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Review UI");
		setDescription("");
	}

	@Override
	public void createFieldEditors() {

		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_REVIEW_DELTA_LEFT_MILLISECONDS, "Delta Left [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_REVIEW_DELTA_RIGHT_MILLISECONDS, "Delta Right [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_REVIEW_DYNAMIC_OFFSET_MILLISECONDS, "Dynamic Offset [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_REPLACE_NEAREST_PEAK, "Replace Nearest Peak (Manual Peak Detection)", getFieldEditorParent()));
		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_REVIEW_REPLACE_PEAK_DELTA_MILLISECONDS, "Replace Max Delta [ms]", PreferenceSupplier.MIN_DELTA_REPLACE_PEAK_MILLISECONDS, PreferenceSupplier.MAX_DELTA_REPLACE_PEAK_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SET_TARGET_DETECTED_PEAK, "Set Target (Manual Peak Detection)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_AUTO_SELECT_BEST_MATCH, "Autoselect Best Match", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_HIDE_EXISTING_PEAKS, "Hide Existing Peaks", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SET_TARGET_VERIFICATION, "Set Target (Peak Verification)", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_REVIEW_VISIBILITY, "Visibility", PreferenceSupplier.VISIBILITY_OPTIONS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_FOCUS_XIC, "Focus XIC", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_BASELINE, "Show Baseline", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_DETAILS, "Show Details", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_UPDATE_SEARCH_TARGET, "Update Search (Target)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_FETCH_LIBRARY_SPECTRUM, "Fetch Library Spectrum", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_ONLY_RELEVANT_PEAKS, "Show Only Relevant Peaks", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_IGNORE_NULL_CAS_NUMBER, "Ignore CAS# 0-00-0", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_REVIEW_PEAK_TYPE, "Peak Type (Default)", PreferenceSupplier.PEAK_TYPE_OPTIONS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SORT_BY_POSITION, "Sort By Position", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}