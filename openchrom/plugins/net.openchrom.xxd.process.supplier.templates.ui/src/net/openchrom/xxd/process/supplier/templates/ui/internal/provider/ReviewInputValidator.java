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

import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.util.ReviewValidator;

public class ReviewInputValidator implements IInputValidator {

	private ReviewValidator validator = new ReviewValidator();
	private Set<String> names = new HashSet<>();

	public ReviewInputValidator(Set<String> names) {

		if(names != null) {
			this.names = names;
		}
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			ReviewSetting setting = validator.getSetting();
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