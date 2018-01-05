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
package net.openchrom.xxd.processor.supplier.tracecompare.model;

public interface ITrackModel {

	int getSampleTrack();

	void setSampleTrack(int sampleTrack);

	int getReferenceTrack();

	void setReferenceTrack(int referenceTrack);

	int getScanVelocity();

	void setScanVelocity(int scanVelocity);

	double getStartRetentionTime();

	void setStartRetentionTime(double startRetentionTime);

	double getStopRetentionTime();

	void setStopRetentionTime(double stopRetentionTime);

	double getStartIntensity();

	void setStartIntensity(double startIntensity);

	double getStopIntensity();

	void setStopIntensity(double stopIntensity);

	boolean isSkipped();

	void setSkipped(boolean isSkipped);

	boolean isEvaluated();

	void setEvaluated(boolean isEvaluated);

	boolean isMatched();

	void setMatched(boolean isMatched);

	String getNotes();

	void setNotes(String notes);

	String getPathSnapshot();

	void setPathSnapshot(String pathSnapshot);
}