/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jan Holy - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.settings;

import java.io.Serializable;

import org.eclipse.chemclipse.support.settings.SystemSettings;
import org.eclipse.chemclipse.support.settings.SystemSettingsStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.openchrom.nmr.processing.supplier.base.settings.support.DigitalFilterTreatmentOptions;

@SystemSettings(SystemSettingsStrategy.NEW_INSTANCE)
public class DigitalFilterRemovalSettings implements Serializable {

	private static final long serialVersionUID = 7271244127560614155L;
	@JsonProperty("Group delay of digital filter")
	private int leftRotationFid = 0;
	@JsonProperty("Weighting factor for first fid point")
	private double dcOffsetMultiplicationFactor = Double.NaN;
	@JsonProperty("Filter treatment options")
	private DigitalFilterTreatmentOptions treatmentOptions = DigitalFilterTreatmentOptions.SUBSTITUTE_WITH_NOISE;

	public int getLeftRotationFid() {

		return leftRotationFid;
	}

	public void setLeftRotationFid(int leftRotationFid) {

		this.leftRotationFid = leftRotationFid;
	}

	public double getDcOffsetMultiplicationFactor() {

		return dcOffsetMultiplicationFactor;
	}

	public void setDcOffsetMultiplicationFactor(double dcOffsetMultiplicationFactor) {

		this.dcOffsetMultiplicationFactor = dcOffsetMultiplicationFactor;
	}

	public DigitalFilterTreatmentOptions getTreatmentOptions() {

		return treatmentOptions;
	}

	public void setTreatmentOptions(DigitalFilterTreatmentOptions treatmentOptions) {

		this.treatmentOptions = treatmentOptions;
	}
}
