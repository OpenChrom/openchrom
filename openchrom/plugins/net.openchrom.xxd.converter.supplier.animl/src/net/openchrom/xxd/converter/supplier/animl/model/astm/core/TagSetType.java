/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
 * Set of Tag elements.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TagSetType", propOrder = {"tag"})
public class TagSetType {

	@XmlElement(name = "Tag")
	protected List<TagType> tag;

	public List<TagType> getTag() {

		if(tag == null) {
			tag = new ArrayList<>();
		}
		return this.tag;
	}
}
