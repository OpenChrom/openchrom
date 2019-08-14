/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

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
	@XmlElement(name = "PathSnapshot")
	private String pathSnapshot = "";

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
	public String getPathSnapshot() {

		return pathSnapshot;
	}

	@Override
	public void setPathSnapshot(String pathSnapshot) {

		this.pathSnapshot = pathSnapshot;
	}

	@Override
	public String toString() {

		return "TrackModel_v1000 [sampleTrack=" + sampleTrack + ", referenceTrack=" + referenceTrack + "]";
	}
}
