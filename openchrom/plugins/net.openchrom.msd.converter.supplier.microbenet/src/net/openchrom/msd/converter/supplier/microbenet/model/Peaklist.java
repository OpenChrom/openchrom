/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.microbenet.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"peaks"})
public class Peaklist {

	@XmlElement(name = "Peaks", required = true)
	protected Peaks peaks;
	@XmlAttribute(name = "intensityScale")
	protected Double intensityScale;
	@XmlAttribute(name = "userCrafted")
	protected Boolean userCrafted;
	@XmlAttribute(name = "uuid", required = true)
	protected String uuid;

	public Peaks getPeaks() {

		return peaks;
	}

	public void setPeaks(Peaks value) {

		this.peaks = value;
	}

	public Double getIntensityScale() {

		return intensityScale;
	}

	public void setIntensityScale(Double value) {

		this.intensityScale = value;
	}

	public Boolean isUserCrafted() {

		return userCrafted;
	}

	public void setUserCrafted(Boolean value) {

		this.userCrafted = value;
	}

	public String getUuid() {

		return uuid;
	}

	public void setUuid(String value) {

		this.uuid = value;
	}
}