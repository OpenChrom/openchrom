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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.IntegerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PagePeakReview extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PagePeakReview() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Review Template");
		setDescription("");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new IntegerFieldEditor(PreferenceSupplier.P_REVIEW_UI_DELTA_LEFT_MILLISECONDS, "Delta Left [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_REVIEW_UI_DELTA_RIGHT_MILLISECONDS, "Delta Right [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_UI_REPLACE_PEAK, "Replace Peak", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SET_TARGET_NAME, "Set Review Target Name", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_AUTO_SELECT_BEST_PEAK_MATCH, "Auto Select Best Match", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_AUTO_LABEL_DETECTED_PEAK, "Auto Label Detected Peak", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_CHROMATOGRAM_TIC, "Show Chromatogram (TIC)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_CHROMATOGRAM_XIC, "Show Chromatogram (XIC)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_BASELINE, "Show Baseline", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_SHOW_DETAILS, "Show Details", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_REVIEW_FETCH_LIBRARY_SPECTRUM, "Fetch Library Spectrum", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
