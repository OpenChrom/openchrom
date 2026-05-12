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
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import java.util.Objects;

public class NameReplacement {

	private String name = "";
	private String synonym = "";

	public void copyFrom(NameReplacement setting) {

		if(setting != null) {
			setName(setting.getName());
			setSynonym(setting.getSynonym());
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getSynonym() {

		return synonym;
	}

	public void setSynonym(String synonym) {

		this.synonym = synonym;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		NameReplacement other = (NameReplacement)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "NameReplacement [name=" + name + ", synonym=" + synonym + "]";
	}
}