/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.settings;

import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.model.settings.AbstractProcessSettings;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class PeakTransferSettings extends AbstractProcessSettings implements IPeakDetectorSettingsMSD, IPeakDetectorSettingsCSD {

	public static final String DESCRIPTION = "Template Peak Transfer";
	//
	@JsonProperty(value = "Transfer Best Target Only", defaultValue = "false")
	@JsonPropertyDescription(value = "If this value is true, only the best target will be transfered.")
	private boolean useBestTargetOnly = false;
	//
	@JsonProperty(value = "Delta Retention Time Left (Minutes)", defaultValue = "0.0")
	@JsonPropertyDescription(value = "This is the left delta retention time.")
	private double deltaRetentionTimeLeft = 0.0;
	@JsonProperty(value = "Delta Retention Time Right (Minutes)", defaultValue = "0.0")
	@JsonPropertyDescription(value = "This is the right delta retention time.")
	private double deltaRetentionTimeRight = 0.0;
	//
	@JsonProperty(value = "Number Traces", defaultValue = "15")
	@JsonPropertyDescription(value = "If a MSD peak contains <= number traces, then SIM detection will be forced.")
	private int numberTraces = 15;

	public boolean isUseBestTargetOnly() {

		return useBestTargetOnly;
	}

	public void setUseBestTargetOnly(boolean useBestTargetOnly) {

		this.useBestTargetOnly = useBestTargetOnly;
	}

	public double getDeltaRetentionTimeLeft() {

		return deltaRetentionTimeLeft;
	}

	public void setDeltaRetentionTimeLeft(double deltaRetentionTimeLeft) {

		this.deltaRetentionTimeLeft = deltaRetentionTimeLeft;
	}

	public double getDeltaRetentionTimeRight() {

		return deltaRetentionTimeRight;
	}

	public void setDeltaRetentionTimeRight(double deltaRetentionTimeRight) {

		this.deltaRetentionTimeRight = deltaRetentionTimeRight;
	}

	public int getNumberTraces() {

		return numberTraces;
	}

	public void setNumberTraces(int numberTraces) {

		this.numberTraces = numberTraces;
	}
}
