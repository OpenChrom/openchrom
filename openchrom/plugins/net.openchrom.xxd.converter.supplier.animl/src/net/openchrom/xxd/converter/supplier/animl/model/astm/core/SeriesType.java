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
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Container for multiple Values.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SeriesType", propOrder = {"individualValueSet", "encodedValueSet", "autoIncrementedValueSet", "unit"})
public class SeriesType {

	@XmlElement(name = "IndividualValueSet")
	protected List<IndividualValueSetType> individualValueSet;
	@XmlElement(name = "EncodedValueSet")
	protected List<EncodedValueSetType> encodedValueSet;
	@XmlElement(name = "AutoIncrementedValueSet")
	protected List<AutoIncrementedValueSetType> autoIncrementedValueSet;
	@XmlElement(name = "Unit")
	protected UnitType unit;
	@XmlAttribute(name = "dependency", required = true)
	protected DependencyType dependency;
	@XmlAttribute(name = "seriesID", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String seriesID;
	@XmlAttribute(name = "visible")
	protected Boolean visible;
	@XmlAttribute(name = "plotScale")
	protected PlotScaleType plotScale;
	@XmlAttribute(name = "seriesType", required = true)
	protected ParameterTypeType seriesType;
	@XmlAttribute(name = "name", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String name;
	@XmlAttribute(name = "id")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;

	public List<IndividualValueSetType> getIndividualValueSet() {

		if(individualValueSet == null) {
			individualValueSet = new ArrayList<>();
		}
		return this.individualValueSet;
	}

	public List<EncodedValueSetType> getEncodedValueSet() {

		if(encodedValueSet == null) {
			encodedValueSet = new ArrayList<>();
		}
		return this.encodedValueSet;
	}

	public List<AutoIncrementedValueSetType> getAutoIncrementedValueSet() {

		if(autoIncrementedValueSet == null) {
			autoIncrementedValueSet = new ArrayList<>();
		}
		return this.autoIncrementedValueSet;
	}

	public UnitType getUnit() {

		return unit;
	}

	public void setUnit(UnitType value) {

		this.unit = value;
	}

	public DependencyType getDependency() {

		return dependency;
	}

	public void setDependency(DependencyType value) {

		this.dependency = value;
	}

	public String getSeriesID() {

		return seriesID;
	}

	public void setSeriesID(String value) {

		this.seriesID = value;
	}

	public boolean isVisible() {

		if(visible == null) {
			return true;
		} else {
			return visible;
		}
	}

	public void setVisible(Boolean value) {

		this.visible = value;
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

	public ParameterTypeType getSeriesType() {

		return seriesType;
	}

	public void setSeriesType(ParameterTypeType value) {

		this.seriesType = value;
	}

	public String getName() {

		return name;
	}

	public void setName(String value) {

		this.name = value;
	}

	public String getId() {

		return id;
	}

	public void setId(String value) {

		this.id = value;
	}
}
