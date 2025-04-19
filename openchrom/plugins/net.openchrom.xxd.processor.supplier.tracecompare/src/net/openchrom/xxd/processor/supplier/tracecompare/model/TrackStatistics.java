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

public class TrackStatistics {

	private String sampleGroup;
	private String referenceGroup;
	private int tracks;
	private int evaluated;
	private int skipped;
	private int matched;

	public String getSampleGroup() {

		return sampleGroup;
	}

	public void setSampleGroup(String sampleGroup) {

		this.sampleGroup = sampleGroup;
	}

	public String getReferenceGroup() {

		return referenceGroup;
	}

	public void setReferenceGroup(String referenceGroup) {

		this.referenceGroup = referenceGroup;
	}

	public double getMatchProbability() {

		double matchProbability = 0.0d;
		if(tracks > 0) {
			matchProbability = 100.0d / tracks * matched;
		}
		return matchProbability;
	}

	public void addTrackModel(ITrackModel trackModel) {

		if(trackModel != null) {
			tracks++;
			if(trackModel.isSkipped()) {
				skipped++;
			} else if(trackModel.isEvaluated()) {
				evaluated++;
				if(trackModel.isMatched()) {
					matched++;
				}
			}
		}
	}

	public int getTracks() {

		return tracks;
	}

	public int getEvaluated() {

		return evaluated;
	}

	public int getSkipped() {

		return skipped;
	}

	public int getMatched() {

		return matched;
	}
}
