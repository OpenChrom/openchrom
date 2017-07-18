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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class SampleLaneModel {

	@XmlElement(name = "SampleLane")
	private int sampleLane = 0;
	@XmlElement(name = "ReferenceSampleLane")
	private int referenceSampleLane = 0;
	@XmlElement(name = "Start Retention Time")
	private double startRetentionTime;
	@XmlElement(name = "Stop Retention Time")
	private double stopRetentionTime;
	@XmlElement(name = "Start Intensity")
	private double startIntensity;
	@XmlElement(name = "Stop Intensity")
	private double stopIntensity;
	@XmlElement(name = "Evaluated")
	private boolean isEvaluated = false;
	@XmlElement(name = "Matched")
	private boolean isMatched = false;
	@XmlElement(name = "Notes")
	private String notes = "";
	@XmlElement(name = "Path Snapshot Sample")
	private String pathSnapshotSample = "";
	@XmlElement(name = "Path Snapshot Reference")
	private String pathSnapshotReference = "";

	@XmlTransient
	public int getSampleLane() {

		return sampleLane;
	}

	public void setSampleLane(int sampleLane) {

		this.sampleLane = sampleLane;
	}

	@XmlTransient
	public int getReferenceSampleLane() {

		return referenceSampleLane;
	}

	public void setReferenceSampleLane(int referenceSampleLane) {

		this.referenceSampleLane = referenceSampleLane;
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
}
