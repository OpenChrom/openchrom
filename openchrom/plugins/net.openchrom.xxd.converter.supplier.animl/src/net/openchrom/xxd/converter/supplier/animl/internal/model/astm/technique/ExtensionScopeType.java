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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * For Extensions only. Specifies which Technique Definitions or Extensions can be extended using this Extension.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensionScopeType", propOrder = {"extendedTechnique", "extendedExtension"})
public class ExtensionScopeType {

	@XmlElement(name = "ExtendedTechnique")
	protected List<ExtendedTechniqueType> extendedTechnique;
	@XmlElement(name = "ExtendedExtension")
	protected List<ExtendedExtensionType> extendedExtension;

	/**
	 * Gets the value of the extendedTechnique property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the extendedTechnique property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getExtendedTechnique().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ExtendedTechniqueType }
	 *
	 *
	 */
	public List<ExtendedTechniqueType> getExtendedTechnique() {

		if(extendedTechnique == null) {
			extendedTechnique = new ArrayList<ExtendedTechniqueType>();
		}
		return this.extendedTechnique;
	}

	/**
	 * Gets the value of the extendedExtension property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the extendedExtension property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getExtendedExtension().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ExtendedExtensionType }
	 *
	 *
	 */
	public List<ExtendedExtensionType> getExtendedExtension() {

		if(extendedExtension == null) {
			extendedExtension = new ArrayList<ExtendedExtensionType>();
		}
		return this.extendedExtension;
	}
}
