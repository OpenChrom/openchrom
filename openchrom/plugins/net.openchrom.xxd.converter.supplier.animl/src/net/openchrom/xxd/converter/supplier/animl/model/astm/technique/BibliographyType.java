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
 * Philip Wenig - refactorings
 *******************************************************************************/
package net.openchrom.xxd.converter.supplier.animl.model.astm.technique;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Set of literature references used in the documentation of this technique definition.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BibliographyType", propOrder = {"literatureReference"})
public class BibliographyType {

	@XmlElement(name = "LiteratureReference", required = true)
	protected List<LiteratureReferenceType> literatureReference;

	/**
	 * Gets the value of the literatureReference property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the literatureReference property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getLiteratureReference().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link LiteratureReferenceType }
	 * 
	 * 
	 */
	public List<LiteratureReferenceType> getLiteratureReference() {

		if(literatureReference == null) {
			literatureReference = new ArrayList<>();
		}
		return this.literatureReference;
	}
}
