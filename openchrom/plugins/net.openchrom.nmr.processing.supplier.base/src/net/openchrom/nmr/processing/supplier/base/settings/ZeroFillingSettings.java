/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

import org.eclipse.chemclipse.support.settings.EnumSelectionSettingProperty;
import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class ZeroFillingSettings {

	@JsonProperty(value = "Zero Filling", defaultValue = "AUTO")
	@EnumSelectionSettingProperty
	private ZeroFillingFactor zeroFillingFactor = ZeroFillingFactor.AUTO;

	public ZeroFillingSettings() {
	}

	public ZeroFillingFactor getZeroFillingFactor() {

		return zeroFillingFactor;
	}

	public void setZeroFillingFactor(ZeroFillingFactor zeroFillingFactor) {

		this.zeroFillingFactor = zeroFillingFactor;
	}
}
