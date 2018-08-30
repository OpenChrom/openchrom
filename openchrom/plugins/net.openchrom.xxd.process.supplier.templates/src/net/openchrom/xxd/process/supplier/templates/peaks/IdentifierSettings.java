/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

public class IdentifierSettings {

	private double startRetentionTime;
	private double stopRetentionTime;
	private String name = "";
	private String casNumber = "";
	private String comments = "";
	private String contributor = "";
	private String referenceId = "";

	public IdentifierSettings(double startRetentionTime, double stopRetentionTime, String name) {
		this.startRetentionTime = startRetentionTime;
		this.stopRetentionTime = stopRetentionTime;
		this.name = name;
	}

	public double getStartRetentionTime() {

		return startRetentionTime;
	}

	public double getStopRetentionTime() {

		return stopRetentionTime;
	}

	public String getName() {

		return name;
	}

	public String getCasNumber() {

		return casNumber;
	}

	public void setCasNumber(String casNumber) {

		this.casNumber = casNumber;
	}

	public String getComments() {

		return comments;
	}

	public void setComments(String comments) {

		this.comments = comments;
	}

	public String getContributor() {

		return contributor;
	}

	public void setContributor(String contributor) {

		this.contributor = contributor;
	}

	public String getReferenceId() {

		return referenceId;
	}

	public void setReferenceId(String referenceId) {

		this.referenceId = referenceId;
	}
}
