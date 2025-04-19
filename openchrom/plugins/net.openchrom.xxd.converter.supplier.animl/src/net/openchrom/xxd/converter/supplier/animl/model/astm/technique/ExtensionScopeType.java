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
 * For Extensions only. Specifies which Technique Definitions or Extensions can be extended using this Extension.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensionScopeType", propOrder = {"extendedTechnique", "extendedExtension"})
public class ExtensionScopeType {

	@XmlElement(name = "ExtendedTechnique")
	protected List<ExtendedTechniqueType> extendedTechnique;
	@XmlElement(name = "ExtendedExtension")
	protected List<ExtendedExtensionType> extendedExtension;

	public List<ExtendedTechniqueType> getExtendedTechnique() {

		if(extendedTechnique == null) {
			extendedTechnique = new ArrayList<>();
		}
		return this.extendedTechnique;
	}

	public List<ExtendedExtensionType> getExtendedExtension() {

		if(extendedExtension == null) {
			extendedExtension = new ArrayList<>();
		}
		return this.extendedExtension;
	}
}
