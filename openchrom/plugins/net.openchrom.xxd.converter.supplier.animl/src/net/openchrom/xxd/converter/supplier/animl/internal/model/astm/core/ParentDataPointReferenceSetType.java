/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Contains references to the parent Result.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParentDataPointReferenceSetType", propOrder = {"parentDataPointReference"})
public class ParentDataPointReferenceSetType {

	@XmlElement(name = "ParentDataPointReference", required = true)
	protected List<ParentDataPointReferenceType> parentDataPointReference;

	public List<ParentDataPointReferenceType> getParentDataPointReference() {

		if(parentDataPointReference == null) {
			parentDataPointReference = new ArrayList<ParentDataPointReferenceType>();
		}
		return this.parentDataPointReference;
	}
}
