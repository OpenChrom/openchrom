/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.converter.supplier.animl.model.astm.core;

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
			parentDataPointReference = new ArrayList<>();
		}
		return this.parentDataPointReference;
	}
}
