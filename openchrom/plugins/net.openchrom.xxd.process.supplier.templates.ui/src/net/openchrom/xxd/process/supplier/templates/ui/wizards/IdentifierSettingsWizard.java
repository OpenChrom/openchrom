/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import net.openchrom.xxd.process.supplier.templates.model.IdentifierSetting;

public class IdentifierSettingsWizard extends Wizard {

	public static final int DEFAULT_WIDTH = 550;
	public static final int DEFAULT_HEIGHT = 450;

	public IdentifierSettingsWizard(IdentifierSetting identifierSetting) {

		addPage(new IdentifierSettingsPage(identifierSetting));
	}

	@Override
	public boolean performFinish() {

		return true;
	}
}