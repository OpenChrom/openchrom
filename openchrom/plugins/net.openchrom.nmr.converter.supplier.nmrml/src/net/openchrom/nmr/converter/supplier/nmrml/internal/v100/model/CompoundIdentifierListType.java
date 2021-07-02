/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.converter.supplier.nmrml.internal.v100.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundIdentifierListType", propOrder = {"identifier", "databaseIdentifier"})
public class CompoundIdentifierListType {

	protected List<CVTermType> identifier;
	protected List<CompoundDatabaseIdentifierType> databaseIdentifier;

	public List<CVTermType> getIdentifier() {

		if(identifier == null) {
			identifier = new ArrayList<CVTermType>();
		}
		return this.identifier;
	}

	public List<CompoundDatabaseIdentifierType> getDatabaseIdentifier() {

		if(databaseIdentifier == null) {
			databaseIdentifier = new ArrayList<CompoundDatabaseIdentifierType>();
		}
		return this.databaseIdentifier;
	}
}
