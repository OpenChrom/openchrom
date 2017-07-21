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
package net.openchrom.xxd.processor.supplier.tracecompare.model.v1000;

import java.text.DecimalFormat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ITrackModel;

public class TrackModel_v1000 implements ITrackModel {

	@XmlElement(name = "SampleTrack")
	private int sampleTrack = 0;
	@XmlElement(name = "ReferenceTrack")
	private int referenceTrack = 0;
	@XmlElement(name = "ScanVelocity")
	private int scanVelocity = 0;
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

	@Override
	@XmlTransient
	public int getSampleTrack() {

		return sampleTrack;
	}

	@Override
	public void setSampleTrack(int sampleTrack) {

		this.sampleTrack = sampleTrack;
	}

	@Override
	@XmlTransient
	public int getReferenceTrack() {

		return referenceTrack;
	}

	@Override
	public void setReferenceTrack(int referenceTrack) {

		this.referenceTrack = referenceTrack;
	}

	@Override
	@XmlTransient
	public int getScanVelocity() {

		return scanVelocity;
	}

	@Override
	public void setScanVelocity(int scanVelocity) {

		this.scanVelocity = scanVelocity;
	}

	@Override
	@XmlTransient
	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	@Override
	public void setStartRetentionTime(double startRetentionTime) {

		this.startRetentionTime = startRetentionTime;
	}

	@Override
	@XmlTransient
	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	@Override
	public void setStopRetentionTime(double stopRetentionTime) {

		this.stopRetentionTime = stopRetentionTime;
	}

	@Override
	@XmlTransient
	public double getStartIntensity() {

		return startIntensity;
	}

	@Override
	public void setStartIntensity(double startIntensity) {

		this.startIntensity = startIntensity;
	}

	@Override
	@XmlTransient
	public double getStopIntensity() {

		return stopIntensity;
	}

	@Override
	public void setStopIntensity(double stopIntensity) {

		this.stopIntensity = stopIntensity;
	}

	@Override
	@XmlTransient
	public boolean isSkipped() {

		return isSkipped;
	}

	@Override
	public void setSkipped(boolean isSkipped) {

		this.isSkipped = isSkipped;
	}

	@Override
	@XmlTransient
	public boolean isEvaluated() {

		return isEvaluated;
	}

	@Override
	public void setEvaluated(boolean isEvaluated) {

		this.isEvaluated = isEvaluated;
	}

	@Override
	@XmlTransient
	public boolean isMatched() {

		return isMatched;
	}

	@Override
	public void setMatched(boolean isMatched) {

		this.isMatched = isMatched;
	}

	@Override
	@XmlTransient
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {

		this.notes = notes;
	}

	@Override
	@XmlTransient
	public String getPathSnapshotSample() {

		return pathSnapshotSample;
	}

	@Override
	public void setPathSnapshotSample(String pathSnapshotSample) {

		this.pathSnapshotSample = pathSnapshotSample;
	}

	@Override
	@XmlTransient
	public String getPathSnapshotReference() {

		return pathSnapshotReference;
	}

	@Override
	public void setPathSnapshotReference(String pathSnapshotReference) {

		this.pathSnapshotReference = pathSnapshotReference;
	}

	@Override
	public String toString() {

		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.00");
		double factor = scanVelocity / 1000.0d;
		//
		StringBuilder builder = new StringBuilder();
		builder.append("Sample Track: " + sampleTrack);
		builder.append("\n");
		builder.append("\tReference Track: " + referenceTrack);
		builder.append("\n");
		builder.append("\t\tScan Velocity [mm/s]: " + scanVelocity);
		builder.append("\n");
		builder.append("\t\tStart Retention Time [ms]: " + (int)startRetentionTime);
		builder.append("\n");
		builder.append("\t\tStop Retention Time [ms]: " + (int)stopRetentionTime);
		builder.append("\n");
		builder.append("\t\tStart Intensity: " + decimalFormat.format(startIntensity));
		builder.append("\n");
		builder.append("\t\tStop Intensity: " + decimalFormat.format(stopIntensity));
		builder.append("\n");
		builder.append("\t\tDistance Start [mm]: " + decimalFormat.format(startRetentionTime * factor));
		builder.append("\n");
		builder.append("\t\tDistance Stop [mm]: " + decimalFormat.format(stopRetentionTime * factor));
		builder.append("\n");
		builder.append("\t\tSkipped: " + isSkipped);
		builder.append("\n");
		builder.append("\t\tEvaluated: " + isEvaluated);
		builder.append("\n");
		builder.append("\t\tMatched: " + isMatched);
		builder.append("\n");
		builder.append("\t\tSnapshot Sample: " + pathSnapshotSample);
		builder.append("\n");
		builder.append("\t\tSnapshot Reference: " + pathSnapshotReference);
		builder.append("\n");
		builder.append("\t\tNotes: " + notes);
		return builder.toString();
	}
}
