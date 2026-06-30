/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.ExtendedIntegerFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.ui.Activator;

public class PageExportIdentifier extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PageExportIdentifier() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("Peak Identifier (Export)");
		setDescription("");
	}

	public void createFieldEditors() {

		addField(new ExtendedIntegerFieldEditor(PreferenceSupplier.P_EXPORT_NUMBER_TRACES_IDENTIFIER, "Number Traces (0 = TIC)", PreferenceSupplier.MIN_NUMBER_TRACES, PreferenceSupplier.MAX_NUMBER_TRACES, getFieldEditorParent()));
		addField(new ComboFieldEditor(PreferenceSupplier.P_EXPORT_POSITION_DIRECTIVE_IDENTIFIER, "Position Directive", PositionDirective.getOptions(), getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_LEFT_COORDINATE_IDENTIFIER, "Delta Left [Coordinate]", PreferenceSupplier.MIN_DELTA_COORDINATE, PreferenceSupplier.MAX_DELTA_COORDINATE, getFieldEditorParent()));
		addField(new DoubleFieldEditor(PreferenceSupplier.P_EXPORT_DELTA_RIGHT_COORDINATE_IDENTIFIER, "Delta Right [Coordinate]", PreferenceSupplier.MIN_DELTA_COORDINATE, PreferenceSupplier.MAX_DELTA_COORDINATE, getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}
}