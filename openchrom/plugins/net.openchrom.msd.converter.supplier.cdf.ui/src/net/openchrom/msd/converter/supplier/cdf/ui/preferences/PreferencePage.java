/*******************************************************************************
 * Copyright (c) 2013, 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpinnerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.msd.converter.supplier.cdf.preferences.PreferenceSupplier;
import net.openchrom.msd.converter.supplier.cdf.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set the NetCDF converter settings.");
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void createFieldEditors() {

		addField(new SpinnerFieldEditor(PreferenceSupplier.P_PRECISION, "Precision Ions", PreferenceSupplier.MIN_PRECISION, PreferenceSupplier.MAX_PRECISION, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_FORCE_PARSE_NOMINAL, "Force Parse Nominal", getFieldEditorParent()));
	}
}
