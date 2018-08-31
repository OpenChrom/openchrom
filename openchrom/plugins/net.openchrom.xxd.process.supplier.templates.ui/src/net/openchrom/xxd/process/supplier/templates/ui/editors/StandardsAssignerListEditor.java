/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.editors;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import net.openchrom.xxd.process.supplier.templates.settings.StandardsAssignerSettings;
import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerListUtil;

public class StandardsAssignerListEditor extends AbstractListEditor {

	private static final String DESCRIPTION = StandardsAssignerSettings.DESCRIPTION;
	private static final String MESSAGE = "You can create a new standards assigner item here.";
	private static final String INITIAL_VALUE = StandardsAssignerListUtil.EXAMPLE_SINGLE;

	public StandardsAssignerListEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent, new StandardsAssignerListUtil());
	}

	@Override
	protected String getNewInputObject() {

		List list = getList();
		InputDialog dialog = new InputDialog(getShell(), DESCRIPTION, MESSAGE, INITIAL_VALUE, new StandardsAssignerInputValidator(list));
		dialog.create();
		if(dialog.open() == Dialog.OK) {
			return addItem(dialog.getValue(), list);
		}
		return null;
	}
}
