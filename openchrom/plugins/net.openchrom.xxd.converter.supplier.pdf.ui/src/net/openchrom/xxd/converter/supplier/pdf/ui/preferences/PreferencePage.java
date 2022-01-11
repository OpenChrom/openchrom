/*******************************************************************************
 * Copyright (c) 2012, 2020 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.pdf.ui.preferences;

import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.LabelFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpacerFieldEditor;
import org.eclipse.chemclipse.support.ui.preferences.fieldeditors.SpinnerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import net.openchrom.xxd.converter.supplier.pdf.preferences.PreferenceSupplier;
import net.openchrom.xxd.converter.supplier.pdf.ui.Activator;

public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("PDF Converter");
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void createFieldEditors() {

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new FileFieldEditor(PreferenceSupplier.P_REPORT_BANNER, "Report Banner (*.jpg) [1200x164 px, 600 dpi]", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_REPORT_SLOGAN, "Report Slogan", getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("Generic Report", getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_NUMBER_IMAGE_PAGES, "Number Images Pages", PreferenceSupplier.MIN_NUMBER_IMAGE_PAGES, PreferenceSupplier.MAX_NUMBER_IMAGE_PAGES, getFieldEditorParent()));
		//
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("Profile Report", getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceSupplier.P_REPORT_METHOD, "Report Method", getFieldEditorParent()));
		addField(new SpinnerFieldEditor(PreferenceSupplier.P_NUMBER_LARGEST_PEAKS, "Number Largest Peaks", PreferenceSupplier.MIN_NUMBER_LARGEST_PEAKS, PreferenceSupplier.MAX_NUMBER_LARGEST_PEAKS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceSupplier.P_PRINT_ALL_TARGETS, "Print all targets", getFieldEditorParent()));
	}
}
