/*******************************************************************************
 * Copyright (c) 2020, 2022 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PageExportDetector extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PageExportDetector() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Detector (Export)");
		setDescription("");
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common
	 * GUI blocks needed to manipulate various types of preferences. Each field
	 * editor knows how to save and restore itself.
	 */
	public void createFieldEditors() {

		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_EXPORT_NUMBER_TRACES_DETECTOR, "Number Traces (0 = TIC)", PreferenceSupplier.MIN_NUMBER_TRACES, PreferenceSupplier.MAX_NUMBER_TRACES, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_EXPORT_OPTIMIZE_RANGE_DETECTOR, "Export Optimize Range", getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_LEFT_POSITION_DETECTOR, "Delta Left", PreferenceSupplier.MIN_DELTA_POSITION, PreferenceSupplier.MAX_DELTA_POSITION, getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_RIGHT_POSITION_DETECTOR, "Delta Right", PreferenceSupplier.MIN_DELTA_POSITION, PreferenceSupplier.MAX_DELTA_POSITION, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_EXPORT_POSITION_DIRECTIVE_DETECTOR, "Position Directive", PositionDirective.getOptions(), getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {

	}
}