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
package net.openchrom.installer.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SetupDefinition {

	public static final String P2_FEATURE_GROUP_SUFFIX = ".feature.group";

	public static final String DESCRIPTION = "OpenChrom Setup Definition";
	public static final String FILE_EXTENSION = ".osd";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";

	private static final String IDENTIFIER_FEATURES = "install_features";

	public List<String> getFeatures(File file) throws IOException {

		List<String> features = new ArrayList<>();

		if(file != null && file.exists()) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(file);
			JsonNode jsonArray = root.get(IDENTIFIER_FEATURES);

			if(jsonArray != null && jsonArray.isArray() && jsonArray.size() > 0) {
				for(JsonNode f : jsonArray) {
					features.add(f.asText() + P2_FEATURE_GROUP_SUFFIX);
				}
			}
		}

		return features;
	}
}