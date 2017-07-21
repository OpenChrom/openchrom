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
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.chemclipse.support.text.ValueFormat;

import net.openchrom.xxd.processor.supplier.tracecompare.core.DataProcessor;

public class ReferenceModel {

	@XmlElement(name = "ReferenceGroup")
	private String referenceGroup = "";
	@XmlElement(name = "ReferencePath")
	private String referencePath = "";
	@XmlElement(name = "TrackModels", type = TrackModel.class)
	private Map<Integer, TrackModel> trackModels = new HashMap<Integer, TrackModel>();

	@XmlTransient
	public String getReferenceGroup() {

		return referenceGroup;
	}

	public void setReferenceGroup(String referenceGroup) {

		this.referenceGroup = referenceGroup;
	}

	@XmlTransient
	public String getReferencePath() {

		return referencePath;
	}

	public void setReferencePath(String referencePath) {

		this.referencePath = referencePath;
	}

	@XmlTransient
	public Map<Integer, TrackModel> getTrackModels() {

		return trackModels;
	}

	public void setTrackModels(Map<Integer, TrackModel> trackModels) {

		this.trackModels = trackModels;
	}

	@Override
	public String toString() {

		TrackStatistics trackStatistics = DataProcessor.getTrackStatistics(this);
		DecimalFormat decimalFormat = ValueFormat.getDecimalFormatEnglish("0.000");
		//
		StringBuilder builder = new StringBuilder();
		builder.append("Reference Group: " + referenceGroup);
		builder.append("\n");
		builder.append("\tReference Path: " + referencePath);
		builder.append("\n");
		builder.append("\tMatch Probability [%]: " + decimalFormat.format(trackStatistics.getMatchProbability()));
		builder.append("\n");
		builder.append("\tTracks: " + trackStatistics.getTracks());
		builder.append("\n");
		builder.append("\tEvaluated Tracks: " + trackStatistics.getEvaluated());
		builder.append("\n");
		builder.append("\tSkipped Tracks: " + trackStatistics.getSkipped());
		builder.append("\n");
		builder.append("\tMatched Tracks: " + trackStatistics.getMatched());
		return builder.toString();
	}
}
