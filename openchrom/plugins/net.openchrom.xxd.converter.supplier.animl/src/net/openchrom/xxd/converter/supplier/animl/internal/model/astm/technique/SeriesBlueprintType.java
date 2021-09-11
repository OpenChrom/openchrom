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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Definition of Series that needs to be attached to this SeriesSet.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeriesBlueprintType", propOrder = {"documentation", "quantity", "allowedValue"})
public class SeriesBlueprintType {

	@XmlElement(name = "Documentation")
	protected DocumentationType documentation;
	@XmlElement(name = "Quantity")
	protected List<Quantity> quantity;
	@XmlElement(name = "AllowedValue")
	protected List<AllowedValueType> allowedValue;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "seriesType", required = true)
	protected AllTypeNameList seriesType;
	@XmlAttribute(name = "modality")
	protected ModalityType modality;
	@XmlAttribute(name = "plotScale")
	protected PlotScaleType plotScale;
	@XmlAttribute(name = "dependency", required = true)
	protected DependencyType dependency;
	@XmlAttribute(name = "maxOccurs")
	protected String maxOccurs;

	public DocumentationType getDocumentation() {

		return documentation;
	}

	public void setDocumentation(DocumentationType value) {

		this.documentation = value;
	}

	public List<Quantity> getQuantity() {

		if(quantity == null) {
			quantity = new ArrayList<Quantity>();
		}
		return this.quantity;
	}

	public List<AllowedValueType> getAllowedValue() {

		if(allowedValue == null) {
			allowedValue = new ArrayList<AllowedValueType>();
		}
		return this.allowedValue;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public AllTypeNameList getSeriesType() {

		return seriesType;
	}

	public void setSeriesType(AllTypeNameList value) {

		this.seriesType = value;
	}

	public ModalityType getModality() {

		if(modality == null) {
			return ModalityType.REQUIRED;
		} else {
			return modality;
		}
	}

	public void setModality(ModalityType value) {

		this.modality = value;
	}

	public PlotScaleType getPlotScale() {

		if(plotScale == null) {
			return PlotScaleType.LINEAR;
		} else {
			return plotScale;
		}
	}

	public void setPlotScale(PlotScaleType value) {

		this.plotScale = value;
	}

	public DependencyType getDependency() {

		return dependency;
	}

	public void setDependency(DependencyType value) {

		this.dependency = value;
	}

	public String getMaxOccurs() {

		if(maxOccurs == null) {
			return "1";
		} else {
			return maxOccurs;
		}
	}

	public void setMaxOccurs(String value) {

		this.maxOccurs = value;
	}
}
