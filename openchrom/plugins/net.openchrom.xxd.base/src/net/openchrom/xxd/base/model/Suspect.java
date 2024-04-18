/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.base.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogramOverview;

public class Suspect {

	/**
	 * Dynamically add new entries.
	 */
	private static final String DEFAULT_NAME = " ";
	//
	private String name = "";
	private String casNumber = "";
	private double retentionTimeMinutesTarget = 0;
	private double retentionTimeMinutesDelta = 0;
	private float retentionIndexTarget = 0.0f;
	private float retentionIndexDelta = 0.0f;
	private Set<GroupMarker> groupMarkers = new HashSet<>();

	public Suspect() {

		this(DEFAULT_NAME);
	}

	public Suspect(String name) {

		this.name = name;
	}

	public boolean isDefault() {

		return DEFAULT_NAME.equals(name);
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCasNumber() {

		return casNumber;
	}

	public void setCasNumber(String casNumber) {

		this.casNumber = casNumber;
	}

	public double getRetentionTimeMinutesTarget() {

		return retentionTimeMinutesTarget;
	}

	public void setRetentionTimeMinutesTarget(double retentionTimeMinutesTarget) {

		this.retentionTimeMinutesTarget = retentionTimeMinutesTarget;
	}

	public double getRetentionTimeMinutesDelta() {

		return retentionTimeMinutesDelta;
	}

	public void setRetentionTimeMinutesDelta(double retentionTimeMinutesDelta) {

		this.retentionTimeMinutesDelta = retentionTimeMinutesDelta;
	}

	public float getRetentionIndexTarget() {

		return retentionIndexTarget;
	}

	public void setRetentionIndexTarget(float retentionIndexTarget) {

		this.retentionIndexTarget = retentionIndexTarget;
	}

	public float getRetentionIndexDelta() {

		return retentionIndexDelta;
	}

	public void setRetentionIndexDelta(float retentionIndexDelta) {

		this.retentionIndexDelta = retentionIndexDelta;
	}

	public Set<GroupMarker> getGroupMarkers() {

		return groupMarkers;
	}

	public int getRetentionTimeTarget() {

		return (int)(retentionTimeMinutesTarget * IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
	}

	public int getRetentionTimeDelta() {

		return (int)(retentionTimeMinutesDelta * IChromatogramOverview.MINUTE_CORRELATION_FACTOR);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Suspect other = (Suspect)obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {

		return "Suspect [name=" + name + ", casNumber=" + casNumber + ", retentionTimeMinutesTarget=" + retentionTimeMinutesTarget + ", retentionTimeMinutesDelta=" + retentionTimeMinutesDelta + ", retentionIndexTarget=" + retentionIndexTarget + ", retentionIndexDelta=" + retentionIndexDelta + ", groupMarkers=" + groupMarkers + "]";
	}
}