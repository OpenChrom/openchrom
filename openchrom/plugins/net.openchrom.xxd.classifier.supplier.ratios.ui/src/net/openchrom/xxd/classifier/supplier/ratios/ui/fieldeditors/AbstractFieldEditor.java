/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.ui.fieldeditors;

import org.eclipse.jface.preference.FieldEditor;

public abstract class AbstractFieldEditor extends FieldEditor {

	public static final String ADD = "Add";
	public static final String ADD_TOOLTIP = "Add a new template";
	public static final String EDIT = "Edit";
	public static final String EDIT_TOOLTIP = "Edit the selected template";
	public static final String REMOVE = "Remove";
	public static final String REMOVE_TOOLTIP = "Remove Selected Templates";
	public static final String REMOVE_ALL = "Remove All";
	public static final String REMOVE_ALL_TOOLTIP = "Remove All Templates";
	public static final String IMPORT = "Import";
	public static final String EXPORT = "Export";
	//
	public static final String IMPORT_TITLE = "Import Templates";
	public static final String EXPORT_TITLE = "Export Templates";
	public static final String DIALOG_TITLE = "Templates";
	public static final String MESSAGE_ADD = "You can create a new template here.";
	public static final String MESSAGE_EDIT = "Edit the selected template.";
	public static final String MESSAGE_REMOVE = "Do you want to delete the selected templates?";
	public static final String MESSAGE_REMOVE_ALL = "Do you want to delete all templates?";
	public static final String MESSAGE_EXPORT_SUCCESSFUL = "Templates have been exported successfully.";
	public static final String MESSAGE_EXPORT_FAILED = "Failed to export the templates.";
}