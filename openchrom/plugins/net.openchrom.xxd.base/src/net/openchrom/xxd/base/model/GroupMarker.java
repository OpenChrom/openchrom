/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.model;

import java.util.Objects;

public class GroupMarker {

	private String name = "";

	public GroupMarker(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
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
		GroupMarker other = (GroupMarker)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "GroupMarker [name=" + name + "]";
	}
}