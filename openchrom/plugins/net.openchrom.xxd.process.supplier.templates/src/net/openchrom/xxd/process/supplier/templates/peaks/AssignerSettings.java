/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

public class AssignerSettings {

	private double startRetentionTime;
	private double stopRetentionTime;
	private String name = "";
	private double concentration = 0.0d;
	private String concentrationUnit = "";
	private double responseFactor = 1.0d;

	public AssignerSettings(double startRetentionTime, double stopRetentionTime, String name) {
		this.startRetentionTime = startRetentionTime;
		this.stopRetentionTime = stopRetentionTime;
		this.name = name;
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public String getName() {

		return name;
	}

	public double getConcentration() {

		return concentration;
	}

	public void setConcentration(double concentration) {

		this.concentration = concentration;
	}

	public String getConcentrationUnit() {

		return concentrationUnit;
	}

	public void setConcentrationUnit(String concentrationUnit) {

		this.concentrationUnit = concentrationUnit;
	}

	public double getResponseFactor() {

		return responseFactor;
	}

	public void setResponseFactor(double responseFactor) {

		this.responseFactor = responseFactor;
	}
}
