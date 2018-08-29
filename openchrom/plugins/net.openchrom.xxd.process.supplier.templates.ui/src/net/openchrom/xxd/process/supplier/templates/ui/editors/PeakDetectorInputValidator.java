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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.widgets.List;

import net.openchrom.xxd.process.supplier.templates.util.PeakDetectorValidator;

public class PeakDetectorInputValidator implements IInputValidator {

	private String[] items = new String[]{};
	private PeakDetectorValidator validator = new PeakDetectorValidator();

	public PeakDetectorInputValidator(List list) {
		if(list != null) {
			items = list.getItems();
		} else {
			items = new String[]{};
		}
	}

	@Override
	public String isValid(String range) {

		IStatus status = validator.validate(range);
		if(status.isOK()) {
			for(String item : items) {
				if(item.equals(range)) {
					return "The range already exists.";
				}
			}
		} else {
			return status.getMessage();
		}
		return null;
	}
}
