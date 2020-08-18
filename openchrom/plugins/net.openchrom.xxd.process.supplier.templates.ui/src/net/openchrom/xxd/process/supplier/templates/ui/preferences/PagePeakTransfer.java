/*******************************************************************************
 * Copyright (c) 2019, 2020 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.IntegerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpinnerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PagePeakTransfer extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PagePeakTransfer() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Transfer");
		setDescription("");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_USE_IDENTIFIED_PEAKS_ONLY, "Transfer Identified Peaks Only", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_USE_BEST_TARGET_ONLY, "Use Best Target Only", getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_TRANSFER_RETENTION_TIME_MILLISECONDS_LEFT, "Delta Retention Time Left [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_TRANSFER_RETENTION_TIME_MILLISECONDS_RIGHT, "Delta Retention Time Right [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_TRANSFER_OFFSET_RETENTION_TIME_MILLISECONDS_PEAK_MAXIMUM, "Offset Retention Time (Peak Maximum) [ms]", PreferenceSupplier.MIN_DELTA_MILLISECONDS, PreferenceSupplier.MAX_DELTA_MILLISECONDS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_ADJUST_PEAK_HEIGHT, "Adjust Peak Height", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_CREATE_MODEL_PEAK, "Create Model Peak (CSD)", getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_TRANSFER_PEAK_OVERLAP_COVERAGE, "Peak Overlap Coverage [%]", PreferenceSupplier.MIN_PEAK_OVERLAP_COVERAGE, PreferenceSupplier.MAX_PEAK_OVERLAP_COVERAGE, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_OPTIMIZE_RANGE, "Optimize Range (VV)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_TRANSFER_CHECK_PURITY, "Check Purity (MSD)", getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_TRANSFER_NUMBER_TRACES, "Number Traces (MSD)", PreferenceSupplier.MIN_NUMBER_TRACES, PreferenceSupplier.MAX_NUMBER_TRACES, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
