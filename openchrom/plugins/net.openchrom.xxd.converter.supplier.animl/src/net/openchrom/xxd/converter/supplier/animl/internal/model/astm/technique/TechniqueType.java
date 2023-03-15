/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
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
package net.openchrom.xxd.converter.supplier.animl.internal.model.astm.technique;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The root element of an AnIML Technique Definition document. Techniques are typically categorized as either sample alteration, detection, or data post-processing. Each document defines and constrains how ExperimentSteps and Sample definitions are to be filled for its respective technique in an AnIML document.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TechniqueType", propOrder = {"documentation", "extensionScope", "sampleRoleBlueprint", "experimentDataRoleBlueprint", "methodBlueprint", "resultBlueprint", "bibliography"})
public class TechniqueType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "ExtensionScope")
	protected ExtensionScopeType extensionScope;
	@XmlElement(name = "SampleRoleBlueprint")
	protected List<SampleRoleBlueprintType> sampleRoleBlueprint;
	@XmlElement(name = "ExperimentDataRoleBlueprint")
	protected List<ExperimentDataRoleBlueprintType> experimentDataRoleBlueprint;
	@XmlElement(name = "MethodBlueprint")
	protected MethodBlueprintType methodBlueprint;
	@XmlElement(name = "ResultBlueprint")
	protected List<ResultBlueprintType> resultBlueprint;
	@XmlElement(name = "Bibliography")
	protected BibliographyType bibliography;
	@XmlAttribute(name = "version", required = true)
	protected String version;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "extension")
	protected Boolean extension;
	@XmlAttribute(name = "abstract")
	protected Boolean _abstract;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public ExtensionScopeType getExtensionScope() {

		return extensionScope;
	}

	public void setExtensionScope(ExtensionScopeType value) {

		this.extensionScope = value;
	}

	/**
	 * Gets the value of the sampleRoleBlueprint property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the sampleRoleBlueprint property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getSampleRoleBlueprint().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link SampleRoleBlueprintType }
	 *
	 *
	 */
	public List<SampleRoleBlueprintType> getSampleRoleBlueprint() {

		if(sampleRoleBlueprint == null) {
			sampleRoleBlueprint = new ArrayList<SampleRoleBlueprintType>();
		}
		return this.sampleRoleBlueprint;
	}

	/**
	 * Gets the value of the experimentDataRoleBlueprint property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the experimentDataRoleBlueprint property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getExperimentDataRoleBlueprint().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link ExperimentDataRoleBlueprintType }
	 *
	 *
	 */
	public List<ExperimentDataRoleBlueprintType> getExperimentDataRoleBlueprint() {

		if(experimentDataRoleBlueprint == null) {
			experimentDataRoleBlueprint = new ArrayList<ExperimentDataRoleBlueprintType>();
		}
		return this.experimentDataRoleBlueprint;
	}

	public MethodBlueprintType getMethodBlueprint() {

		return methodBlueprint;
	}

	public void setMethodBlueprint(MethodBlueprintType value) {

		this.methodBlueprint = value;
	}

	/**
	 * Gets the value of the resultBlueprint property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the resultBlueprint property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getResultBlueprint().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following types are allowed in the list
	 * {@link ResultBlueprintType }
	 *
	 *
	 */
	public List<ResultBlueprintType> getResultBlueprint() {

		if(resultBlueprint == null) {
			resultBlueprint = new ArrayList<ResultBlueprintType>();
		}
		return this.resultBlueprint;
	}

	public BibliographyType getBibliography() {

		return bibliography;
	}

	public void setBibliography(BibliographyType value) {

		this.bibliography = value;
	}

	public String getVersion() {

		if(version == null) {
			return "0.90";
		} else {
			return version;
		}
	}

	public void setVersion(String value) {

		this.version = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public boolean isExtension() {

		if(extension == null) {
			return false;
		} else {
			return extension;
		}
	}

	public void setExtension(Boolean value) {

		this.extension = value;
	}

	public boolean isAbstract() {

		if(_abstract == null) {
			return false;
		} else {
			return _abstract;
		}
	}

	public void setAbstract(Boolean value) {

		this._abstract = value;
	}
}