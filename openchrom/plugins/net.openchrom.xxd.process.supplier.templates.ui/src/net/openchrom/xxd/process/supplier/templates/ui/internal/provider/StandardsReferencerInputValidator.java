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
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerValidator;

public class StandardsReferencerInputValidator implements IInputValidator {

	private StandardsReferencerValidator validator = new StandardsReferencerValidator();
	private List<AssignerReference> settings = new ArrayList<>();

	public StandardsReferencerInputValidator(List<AssignerReference> settings) {
		if(settings != null) {
			this.settings = settings;
		}
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			AssignerReference setting = validator.getSetting();
			if(settings.contains(setting)) {
				return "The element already exists.";
			}
		} else {
			return status.getMessage();
		}
		return null;
	}
}
