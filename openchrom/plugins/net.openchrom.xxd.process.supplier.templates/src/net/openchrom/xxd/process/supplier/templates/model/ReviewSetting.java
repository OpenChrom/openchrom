/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.PeakType;

public class ReviewSetting extends AbstractSetting {

	public static final String CLASSIFIER_REVIEW_OK = "Review (OK)";
	//
	private String name = "";
	private String casNumber = "";
	private String traces = "";
	private PeakType peakType = PeakType.VV; // VV => include background: true
	private boolean optimizeRange = false;

	public void copyFrom(ReviewSetting setting) {

		if(setting != null) {
			setPositionStart(setting.getPositionStart());
			setPositionStop(setting.getPositionStop());
			setPositionDirective(setting.getPositionDirective());
			setName(setting.getName());
			setCasNumber(setting.getCasNumber());
			setTraces(setting.getTraces());
			setPeakType(setting.getPeakType());
			setOptimizeRange(setting.isOptimizeRange());
		}
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getCasNumber() {

		return casNumber;
	}

	public void setCasNumber(String casNumber) {

		this.casNumber = casNumber;
	}

	public String getTraces() {

		return traces;
	}

	public void setTraces(String traces) {

		this.traces = traces;
	}

	public PeakType getPeakType() {

		return peakType;
	}

	public void setPeakType(PeakType peakType) {

		this.peakType = peakType;
	}

	public boolean isOptimizeRange() {

		return optimizeRange;
	}

	public void setOptimizeRange(boolean optimizeRange) {

		this.optimizeRange = optimizeRange;
	}
}