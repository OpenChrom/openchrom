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

import net.openchrom.xxd.process.supplier.templates.model.IntegratorSetting;
import net.openchrom.xxd.process.supplier.templates.util.PeakIntegratorValidator;

public class PeakIntegratorInputValidator implements IInputValidator {

	private PeakIntegratorValidator validator = new PeakIntegratorValidator();
	private Set<String> identifiers = new HashSet<>();

	public PeakIntegratorInputValidator(Set<String> identifiers) {

		if(identifiers != null) {
			this.identifiers = identifiers;
		}
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			IntegratorSetting setting = validator.getSetting();
			String identifier = setting.getIdentifier();
			if(identifiers.contains(identifier)) {
				return "The element already exists.";
			}
		} else {
			return status.getMessage();
		}
		return null;
	}
}
