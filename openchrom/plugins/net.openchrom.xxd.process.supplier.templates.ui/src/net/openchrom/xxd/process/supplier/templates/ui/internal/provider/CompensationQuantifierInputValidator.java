/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;

import net.openchrom.xxd.process.supplier.templates.model.CompensationSetting;
import net.openchrom.xxd.process.supplier.templates.util.CompensationQuantValidator;

public class CompensationQuantifierInputValidator implements IInputValidator {

	private CompensationQuantValidator validator = new CompensationQuantValidator();
	private Set<String> names = new HashSet<>();

	public CompensationQuantifierInputValidator(Set<String> names) {

		if(names != null) {
			this.names = names;
		}
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			CompensationSetting setting = validator.getSetting();
			String name = setting.getName();
			if(names.contains(name)) {
				return "The element already exists.";
			}
		} else {
			return status.getMessage();
		}
		return null;
	}
}