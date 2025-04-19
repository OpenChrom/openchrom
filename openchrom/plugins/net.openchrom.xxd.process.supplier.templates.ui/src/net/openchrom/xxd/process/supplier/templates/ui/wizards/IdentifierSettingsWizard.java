/*******************************************************************************
 * Copyright (c) 2024, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import java.util.Set;

import org.eclipse.jface.wizard.Wizard;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class IdentifierSettingsWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 550;
	public static final int DEFAULT_HEIGHT = 450;

	/**
	 * If invalidNames is null or empty, no name check is performed.
	 * 
	 * @param identifierSetting
	 * @param invalidNames
	 */
	public IdentifierSettingsWizard(IdentifierSetting identifierSetting, Set<String> invalidNames) {

		addPage(new IdentifierSettingsPage(identifierSetting, invalidNames));
	}

	@Override
	public boolean performFinish() {

		return true;
	}
}