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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * Machine-readable description of changes made.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiffType", propOrder = {"oldValue", "newValue"})
public class DiffType {

	@XmlElement(name = "OldValue", required = true)
	protected String oldValue;
	@XmlElement(name = "NewValue", required = true)
	protected String newValue;
	@XmlAttribute(name = "scope", required = true)
	protected ScopeType scope;
	@XmlAttribute(name = "changedItem", required = true)
	@XmlIDREF
	@XmlSchemaType(name = "IDREF")
	protected Object changedItem;

	public String getOldValue() {

		return oldValue;
	}

	public void setOldValue(String value) {

		this.oldValue = value;
	}

	public String getNewValue() {

		return newValue;
	}

	public void setNewValue(String value) {

		this.newValue = value;
	}

	public ScopeType getScope() {

		return scope;
	}

	public void setScope(ScopeType value) {

		this.scope = value;
	}

	public Object getChangedItem() {

		return changedItem;
	}

	public void setChangedItem(Object value) {

		this.changedItem = value;
	}
}
