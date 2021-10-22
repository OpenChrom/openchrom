/*******************************************************************************
 * Copyright (c) 2020, 2021 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.ui.internal.provider;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReportSettings;
import net.openchrom.xxd.process.supplier.templates.util.ReportValidator;

public class ReportInputValidator implements IInputValidator {

	private ReportValidator validator = new ReportValidator();
	private ReportSettings reportSettings;

	public ReportInputValidator(ReportSettings reportSettings) {

		this.reportSettings = reportSettings;
	}

	@Override
	public String isValid(String target) {

		IStatus status = validator.validate(target);
		if(status.isOK()) {
			ReportSetting setting = validator.getSetting();
			if(reportSettings.contains(setting)) {
				return "The element already exists: name, start- and stop retention time.";
			}
		} else {
			return status.getMessage();
		}
		return null;
	}
}
