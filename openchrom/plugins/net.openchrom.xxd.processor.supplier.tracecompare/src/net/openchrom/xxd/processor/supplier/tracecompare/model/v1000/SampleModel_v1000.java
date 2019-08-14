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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import net.openchrom.xxd.processor.supplier.tracecompare.model.ISampleModel;

public class SampleModel_v1000 implements ISampleModel {

	@XmlElement(name = "SampleGroup")
	private String sampleGroup = "";
	@XmlElement(name = "SamplePath")
	private String samplePath = "";
	@XmlElement(name = "TrackModels", type = TrackModel_v1000.class)
	private Map<Integer, TrackModel_v1000> trackModels = new HashMap<Integer, TrackModel_v1000>();

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
