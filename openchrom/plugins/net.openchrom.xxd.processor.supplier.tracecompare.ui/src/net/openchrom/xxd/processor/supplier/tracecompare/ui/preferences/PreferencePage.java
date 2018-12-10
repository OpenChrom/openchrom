/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swtchart.extensions.preferences.PreferenceSupport;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;
import net.openchrom.xxd.processor.supplier.tracecompare.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Trace Compare");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		IntegerFieldEditor scanVelocityFieldEditor = new IntegerFieldEditor(PreferenceSupplier.P_SCAN_VELOCITY, "Scan velocity [mm/s]:", getFieldEditorParent());
		scanVelocityFieldEditor.setValidRange(PreferenceSupplier.MIN_SCAN_VELOCITY, PreferenceSupplier.MAX_SCAN_VELOCITY);
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_DETECTOR_TYPE, "Detector Type:", PreferenceSupplier.getDetectorTypes(), getFieldEditorParent()));
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceSupplier.P_SAMPLE_DIRECTORY, "Sample Directory:", getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceSupplier.P_REFERNCE_DIRECTORY, "Reference Directory:", getFieldEditorParent()));
		addField(scanVelocityFieldEditor);
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_SEARCH_CASE_SENSITIVE, "Search case sensitive", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_190, "Color 190 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_200, "Color 200 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_220, "Color 220 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_240, "Color 240 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_260, "Color 260 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_280, "Color 280 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_300, "Color 300 nm:", getFieldEditorParent()));
		addField(new ColorFieldEditor(PreferenceSupplier.P_COLOR_DATA_DEFAULT, "Color Data Default:", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_MIRROR_REFERENCE_DATA, "Mirror reference data (editor reload needed)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_USE_DATA_VALIDATION, "Use data validation (editor reload needed)", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_LINE_STYLE_SAMPLE, "Line Style Sample:", PreferenceSupport.LINE_STYLES, getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_LINE_WIDTH_SAMPLE, "Line Width Sample:", getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_LINE_STYLE_REFERENCE, "Line Style Reference:", PreferenceSupport.LINE_STYLES, getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_LINE_WIDTH_REFERENCE, "Line Width Reference:", getFieldEditorParent()));
		addField(new IntegerFieldEditor(PreferenceSupplier.P_LINE_WIDTH_HIGHLIGHT, "Line Width Highlight:", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}
