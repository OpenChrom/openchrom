/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mz5.internal.model;

public class ControlledVocabulary {

	private String uri;
	private String fullname;
	private String id;
	private String version;

	public String getUri() {

		return uri;
	}

	public void setUri(String uri) {

		this.uri = uri;
	}

	public String getFullname() {

		return fullname;
	}

	public void setFullname(String fullname) {

		this.fullname = fullname;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}
}
