/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.extensions.cdk.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.DoubleFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.msd.extensions.cdk.preferences.PreferenceSupplier;
import net.openchrom.msd.extensions.cdk.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {

		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setTitle("CDK");
		setDescription("Mass Spectometry using the Chemistry Development Kit");
	}

	@Override
	public void createFieldEditors() {

		addField(new DoubleFieldEditor(PreferenceSupplier.P_MIN_INTENSITY, "Minimum Isotope Intensity", getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench workbench) {

	}
}