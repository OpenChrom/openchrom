/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.generator;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class MethodSettings {

	Map<String, Object> settings = new LinkedHashMap<>();

	@JsonAnySetter
	void setSettings(String name, Object value) {

		settings.put(name, value);
	}

	public Map<String, Object> getSettings() {

		return settings;
	}
}
