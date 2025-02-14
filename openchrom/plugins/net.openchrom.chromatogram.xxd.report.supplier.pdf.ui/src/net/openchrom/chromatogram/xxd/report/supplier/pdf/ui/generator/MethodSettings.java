/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
