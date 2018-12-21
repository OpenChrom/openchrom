/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.fieldeditors;

import org.eclipse.jface.preference.FieldEditor;

public abstract class AbstractFieldEditor extends FieldEditor {

	protected static final String ADD = "Add";
	protected static final String ADD_TOOLTIP = "Add a new template";
	protected static final String EDIT = "Edit";
	protected static final String EDIT_TOOLTIP = "Edit the selected template";
	protected static final String REMOVE = "Remove";
	protected static final String REMOVE_TOOLTIP = "Remove Selected Template(s)";
	protected static final String REMOVE_ALL = "Remove All";
	protected static final String REMOVE_ALL_TOOLTIP = "Remove All Templates";
	protected static final String IMPORT = "Import";
	protected static final String EXPORT = "Export";
	//
	protected static final String IMPORT_TITLE = "Import Templates";
	protected static final String EXPORT_TITLE = "Export Templates";
	protected static final String DIALOG_TITLE = "Template(s)";
	protected static final String MESSAGE_ADD = "You can create a new template here.";
	protected static final String MESSAGE_EDIT = "Edit the selected template.";
	protected static final String MESSAGE_REMOVE = "Do you want to delete the selected template(s)?";
	protected static final String MESSAGE_REMOVE_ALL = "Do you want to delete all template(s)?";
	protected static final String MESSAGE_EXPORT_SUCCESSFUL = "Templates have been exported successfully.";
	protected static final String MESSAGE_EXPORT_FAILED = "Failed to export the templates.";
}
