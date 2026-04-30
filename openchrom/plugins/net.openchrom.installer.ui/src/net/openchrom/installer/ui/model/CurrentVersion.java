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
package net.openchrom.installer.ui.model;

import com.google.gson.JsonObject;

public class CurrentVersion {

	private String version;
	private String createdAt;

	public CurrentVersion(JsonObject data) {

		version = data.get("version").getAsString();
		createdAt = data.get("created_at").getAsString();
	}

	public String getVersion() {

		return version;
	}

	public String getCreatedAt() {

		return createdAt;
	}
}
