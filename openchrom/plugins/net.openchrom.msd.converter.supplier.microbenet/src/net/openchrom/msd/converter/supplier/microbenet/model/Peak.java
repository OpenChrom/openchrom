/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
public class Peak {

	@XmlAttribute(name = "intensity")
	protected Double intensity;
	@XmlAttribute(name = "mass")
	protected Double mass;
	@XmlAttribute(name = "profile")
	protected Double profile;
	@XmlAttribute(name = "sigma")
	protected Double sigma;

	public Double getIntensity() {

		return intensity;
	}

	public void setIntensity(Double value) {

		this.intensity = value;
	}

	public Double getMass() {

		return mass;
	}

	public void setMass(Double value) {

		this.mass = value;
	}

	public Double getProfile() {

		return profile;
	}

	public void setProfile(Double value) {

		this.profile = value;
	}

	public Double getSigma() {

		return sigma;
	}

	public void setSigma(Double value) {

		this.sigma = value;
	}
}