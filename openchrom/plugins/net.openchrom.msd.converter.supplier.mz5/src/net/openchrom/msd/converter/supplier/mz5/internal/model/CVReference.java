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

public class CVReference {

	private String name;
	private String prefix;
	private int accession; // actually unsigned

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getPrefix() {

		return prefix;
	}

	public void setPrefix(String prefix) {

		this.prefix = prefix;
	}

	public int getAccession() {

		return accession;
	}

	public void setAccession(int accession) {

		this.accession = accession;
	}
}
