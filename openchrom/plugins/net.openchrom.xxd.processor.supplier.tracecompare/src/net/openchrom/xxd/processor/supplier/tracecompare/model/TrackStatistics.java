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

public class TrackStatistics {

	private String referenceGroup;
	private int tracks;
	private int evaluated;
	private int skipped;
	private int matched;

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
