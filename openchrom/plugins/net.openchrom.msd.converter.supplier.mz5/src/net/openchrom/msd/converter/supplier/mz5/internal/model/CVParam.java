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

public class CVParam {

	private String value;
	private int cvRefID; // actually unsigned
	private int uRefID; // actually unsigned

	public String getValue() {

		return value;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public int getControlledVocabularyReferenceID() {

		return cvRefID;
	}

	public void setControlledVocabularyReferenceID(int cvRefID) {

		this.cvRefID = cvRefID;
	}

	public int getUnitReferenceID() {

		return uRefID;
	}

	public void setUnitReferenceID(int uRefID) {

		this.uRefID = uRefID;
	}
}
