/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.List;

import net.openchrom.xxd.base.model.io.SuspectMatricesIO;

public class SuspectMatrix {

	private String name = "";
	private String version = SuspectMatricesIO.VERSION_LATEST;
	private List<Suspect> suspects = new ArrayList<>();

	public SuspectMatrix(String name, boolean addDefault) {

		setName(name);
		if(addDefault) {
			addDefault();
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {

		this.version = version;
	}

	public void add(Suspect compound) {

		if(compound.isDefault()) {
			if(!hasDefault()) {
				suspects.add(compound);
			}
		} else {
			suspects.add(compound);
		}
	}

	public List<Suspect> getSuspects() {

		return suspects;
	}

	public void validate() {

		if(!hasDefault()) {
			addDefault();
		}
	}

	private boolean hasDefault() {

		boolean hasDefault = false;
		if(!suspects.isEmpty()) {
			exitloop:
			for(Suspect compound : suspects) {
				if(compound.isDefault()) {
					hasDefault = true;
					break exitloop;
				}
			}
		}
		//
		return hasDefault;
	}

	private void addDefault() {

		suspects.add(new Suspect());
	}
}