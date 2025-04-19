/*******************************************************************************
 * Copyright (c) 2018, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;

import net.openchrom.xxd.process.supplier.templates.model.AssignerReference;
import net.openchrom.xxd.process.supplier.templates.util.StandardsReferencerValidator;

public class StandardsReferencerInputValidator implements IInputValidator {

	private StandardsReferencerValidator validator = new StandardsReferencerValidator();
	private Set<String> identifiers = new HashSet<>();

	public StandardsReferencerInputValidator(Set<String> identifiers) {

		if(identifiers != null) {
			this.identifiers = identifiers;
		}
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			AssignerReference setting = validator.getSetting();
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
