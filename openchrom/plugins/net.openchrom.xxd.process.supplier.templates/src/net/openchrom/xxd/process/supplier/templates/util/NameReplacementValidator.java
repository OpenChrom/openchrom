/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.util;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

import net.openchrom.xxd.process.supplier.templates.model.NameReplacement;

public class NameReplacementValidator extends AbstractTemplateValidator {

	private static final String ERROR = "Please enter a valid replacement.";

	private String name = "";
	private String synonym = "";

	@Override
	public IStatus validate(Object value) {

		String message = null;
		if(value == null) {
			message = ERROR;
		} else {
			if(value instanceof String text) {
				text = text.trim();
				if(text.contains(AbstractTemplateListUtil.SEPARATOR_TOKEN)) {
					message = AbstractTemplateListUtil.ERROR_TOKEN;
				} else if("".equals(text.trim())) {
					message = ERROR;
				} else {
					/*
					 * Extract retention time, ...
					 */
					String[] values = text.trim().split("\\" + AbstractTemplateListUtil.SEPARATOR_ENTRY); // The pipe needs to be escaped.
					if(values.length >= 2) {
						/*
						 * Evaluation
						 */
						name = parseString(values, 0, "");
						synonym = parseString(values, 1, "");
						if(name.isBlank() || synonym.isBlank()) {
							message = "Name and synonym must not be blank.";
						}
					} else {
						message = ERROR;
					}
				}
			} else {
				message = ERROR;
			}
		}

		if(message != null) {
			return ValidationStatus.error(message);
		} else {
			return ValidationStatus.ok();
		}
	}

	public NameReplacement getSetting() {

		NameReplacement setting = new NameReplacement();
		setting.setName(name);
		setting.setSynonym(synonym);

		return setting;
	}
}