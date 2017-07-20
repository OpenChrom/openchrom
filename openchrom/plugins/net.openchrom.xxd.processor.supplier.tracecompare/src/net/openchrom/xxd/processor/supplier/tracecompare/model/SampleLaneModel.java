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
	@XmlElement(name = "ReferenceLane")
	private int referenceLane = 0;
	@XmlElement(name = "ValuesSampleX")
	private double[] valuesSampleX = new double[0];
	@XmlElement(name = "ValuesSampleY")
	private double[] valuesSampleY = new double[0];
	@XmlElement(name = "ValuesReferenceX")
	private double[] valuesReferenceX = new double[0];
	@XmlElement(name = "ValuesReferenceY")
	private double[] valuesReferenceY = new double[0];
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
	public double[] getValuesSampleX() {

		return valuesSampleX;
	}

	public void setValuesSampleX(double[] valuesSampleX) {

		this.valuesSampleX = valuesSampleX;
	}

	@XmlTransient
	public double[] getValuesSampleY() {

		return valuesSampleY;
	}

	public void setValuesSampleY(double[] valuesSampleY) {

		this.valuesSampleY = valuesSampleY;
	}

	@XmlTransient
	public double[] getValuesReferenceX() {

		return valuesReferenceX;
	}

	public void setValuesReferenceX(double[] valuesReferenceX) {

		this.valuesReferenceX = valuesReferenceX;
	}

	@XmlTransient
	public double[] getValuesReferenceY() {

		return valuesReferenceY;
	}

	public void setValuesReferenceY(double[] valuesReferenceY) {

		this.valuesReferenceY = valuesReferenceY;
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
}
