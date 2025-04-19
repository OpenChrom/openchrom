/*******************************************************************************
 * Copyright (c) 2016, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.settings;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OnsiteSettings extends AbstractProcessSettings implements IOnsiteSettings {

	@JsonIgnore
	private Map<String, String> settings = new HashMap<>();

	@Override
	public String getLine(String line) {

		if(line != null) {
			String[] values = line.split("=");
			if(values.length == 2) {
				String key = values[0];
				if(settings.containsKey(key)) {
					line = key + "=" + settings.get(key);
				}
			}
		}
		return line;
	}

	@Override
	public void setValue(String key, String value) {

		settings.put(key, value);
	}
}
