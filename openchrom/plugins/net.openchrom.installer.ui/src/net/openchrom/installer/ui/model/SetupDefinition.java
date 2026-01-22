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
 * Alexander Kurtakov - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.ui.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SetupDefinition {

	public static final String DESCRIPTION = "OpenChrom Setup Definition";
	public static final String FILE_EXTENSION = ".osd";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";

	private static final String IDENTIFIER_FEATURES = "install_features";

	public List<String> getFeatures(File file, String p2FeatureGroupSuffix) throws IOException {

		List<String> features = new ArrayList<>();
		if(file != null && file.exists()) {
			try (FileReader fileReader = new FileReader(file)) {
				JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
				JsonArray jsonArray = jsonObject.getAsJsonArray(IDENTIFIER_FEATURES);
				if(jsonArray != null && !jsonArray.isEmpty()) {
					jsonArray.forEach(f -> features.add(f.getAsString() + p2FeatureGroupSuffix));
				}
			}
		}

		return features;
	}
}