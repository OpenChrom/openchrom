/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.processor.supplier.tracecompare.model.v1000;

import java.util.HashMap;
import java.util.Map;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ISampleModel;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

public class SampleModel_v1000 implements ISampleModel {

	@XmlElement(name = "SampleGroup")
	private String sampleGroup = "";
	@XmlElement(name = "SamplePath")
	private String samplePath = "";
	@XmlElement(name = "TrackModels", type = TrackModel_v1000.class)
	private Map<Integer, TrackModel_v1000> trackModels = new HashMap<>();

	@Override
	@XmlTransient
	public String getSampleGroup() {

		return sampleGroup;
	}

	@Override
	public void setSampleGroup(String sampleGroup) {

		this.sampleGroup = sampleGroup;
	}

	@Override
	@XmlTransient
	public String getSamplePath() {

		return samplePath;
	}

	@Override
	public void setSamplePath(String samplePath) {

		this.samplePath = samplePath;
	}

	@Override
	@XmlTransient
	public Map<Integer, TrackModel_v1000> getTrackModels() {

		return trackModels;
	}

	@Override
	public void setTrackModels(Map<Integer, TrackModel_v1000> trackModels) {

		this.trackModels = trackModels;
	}
}
