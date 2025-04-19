/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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