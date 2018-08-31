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

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.widgets.List;

import net.openchrom.xxd.process.supplier.templates.util.StandardsAssignerValidator;

public class StandardsAssignerInputValidator extends AbstractInputValidator implements IInputValidator {

	public StandardsAssignerInputValidator(List list) {
		super(new StandardsAssignerValidator(), list);
	}
}
