/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.model;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.xxd.processor.supplier.tracecompare.preferences.PreferenceSupplier;

public class SampleLaneModel {

	@XmlElement(name = "SampleLane")
	private int sampleLane = 0;
	@XmlElement(name = "ReferenceLane")
	private int referenceLane = 0;
	@XmlElement(name = "StartRetentionTime")
	private double startRetentionTime;
	@XmlElement(name = "StopRetentionTime")
	private double stopRetentionTime;
	@XmlElement(name = "StartIntensity")
	private double startIntensity;
	@XmlElement(name = "StopIntensity")
	private double stopIntensity;
	@XmlElement(name = "Skipped")
	private boolean isSkipped = false;
	@XmlElement(name = "Evaluated")
	private boolean isEvaluated = false;
	@XmlElement(name = "Matched")
	private boolean isMatched = false;
	@XmlElement(name = "Notes")
	private String notes = "";
	@XmlElement(name = "PathSnapshotSample")
	private String pathSnapshotSample = "";
	@XmlElement(name = "PathSnapshotReference")
	private String pathSnapshotReference = "";

	@XmlTransient
	public int getSampleLane() {

		return sampleLane;
	}

	public void setSampleLane(int sampleLane) {

		this.sampleLane = sampleLane;
	}

	@XmlTransient
	public int getReferenceLane() {

		return referenceLane;
	}

	public void setReferenceLane(int referenceLane) {

		this.referenceLane = referenceLane;
	}

	@XmlTransient
	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public void setStartRetentionTime(double startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	@XmlTransient
	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public void setStopRetentionTime(double stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	@XmlTransient
	public double getStartIntensity() {

		return startIntensity;
	}

	public void setStartIntensity(double startIntensity) {

		this.startIntensity = startIntensity;
	}

	@XmlTransient
	public double getStopIntensity() {

		return stopIntensity;
	}

	public void setStopIntensity(double stopIntensity) {

		this.stopIntensity = stopIntensity;
	}

	@XmlTransient
	public boolean isSkipped() {

		return isSkipped;
	}

	public void setSkipped(boolean isSkipped) {

		this.isSkipped = isSkipped;
	}

	@XmlTransient
	public boolean isEvaluated() {

		return isEvaluated;
	}

	public void setEvaluated(boolean isEvaluated) {

		this.isEvaluated = isEvaluated;
	}

	@XmlTransient
	public boolean isMatched() {

		return isMatched;
	}

	public void setMatched(boolean isMatched) {

		this.isMatched = isMatched;
	}

	@XmlTransient
	public String getNotes() {

		return notes;
	}

	public void setNotes(String notes) {

		this.notes = notes;
	}

	@XmlTransient
	public String getPathSnapshotSample() {

		return pathSnapshotSample;
	}

	public void setPathSnapshotSample(String pathSnapshotSample) {

		this.pathSnapshotSample = pathSnapshotSample;
	}

	@XmlTransient
	public String getPathSnapshotReference() {

		return pathSnapshotReference;
	}

	public void setPathSnapshotReference(String pathSnapshotReference) {

		this.pathSnapshotReference = pathSnapshotReference;
	}

	@Override
	public String toString() {

		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.00");
		double factor = PreferenceSupplier.getScanVelocity() / 1000.0d;
		StringBuilder builder = new StringBuilder();
		builder.append("Lane Results");
		builder.append("\n");
		builder.append("\tSample Lane: " + sampleLane);
		builder.append("\n");
		builder.append("\tReference Lane: " + referenceLane);
		builder.append("\n");
		builder.append("\tDistance Start [mm]: " + decimalFormat.format(startRetentionTime * factor));
		builder.append("\n");
		builder.append("\tDistance Stop [mm]: " + decimalFormat.format(stopRetentionTime * factor));
		builder.append("\n");
		builder.append("\tStart Intensity: " + decimalFormat.format(startIntensity));
		builder.append("\n");
		builder.append("\tStop Intensity: " + decimalFormat.format(stopIntensity));
		builder.append("\n");
		builder.append("\tSkipped: " + isSkipped);
		builder.append("\n");
		builder.append("\tEvaluated: " + isEvaluated);
		builder.append("\n");
		builder.append("\tMatched: " + isMatched);
		builder.append("\n");
		builder.append("\tSnapshot Sample: " + pathSnapshotSample);
		builder.append("\n");
		builder.append("\tSnapshot Reference: " + pathSnapshotReference);
		builder.append("\n");
		builder.append("\tNotes: " + notes);
		return builder.toString();
	}
}
