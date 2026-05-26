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
 * Aleksandar Kurtakov - initial API and implementation
 *******************************************************************************/
package net.openchrom.installer.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrentVersion {

	private static final String URL_STR = "https://marketplace.lablicate.com/api/download/1/current_element_version";

	private final String version;
	private final String createdAt;

	public CurrentVersion(JsonNode data) {

		version = data.get("version").asText();
		createdAt = data.get("created_at").asText();
	}

	public String getVersion() {

		return version;
	}

	public String getCreatedAt() {

		return createdAt;
	}

	public static CurrentVersion getLatestVersion() {

		try {
			HttpClient client = HttpClient.newHttpClient(); //
			HttpRequest request = HttpRequest.newBuilder() //
					.uri(URI.create(URL_STR)) //
					.GET() //
					.build();//

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode data = objectMapper.readTree(response.body()).get("data");

			return objectMapper.treeToValue(data, CurrentVersion.class);
		} catch(IOException | InterruptedException e) {
			// can't determine latest version
			Thread.currentThread().interrupt(); // only if interrupted
		}
		return null;
	}
}